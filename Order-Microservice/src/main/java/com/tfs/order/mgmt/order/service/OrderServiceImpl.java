package com.tfs.order.mgmt.order.service;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import com.tfs.order.mgmt.order.enums.ActionType;
import com.tfs.order.mgmt.order.model.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.order.model.dto.ProductCountEventDTO;
import com.tfs.order.mgmt.order.model.entity.Order;
import com.tfs.order.mgmt.order.producer.ProductCustomerMsgProducer;
import com.tfs.order.mgmt.order.repository.OrderReactiveRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderReactiveRepository orderReactiveRepository;

	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;

	@Autowired
	ProductCustomerMsgProducer productCustomerMsgProducer;
	
	@Autowired
	ProductCountEventDTO productCountEventDTO;
	
	@Autowired
	CustomerCreditLimitEventDTO customerCreditLimitEventDTO;

	@Override
	public Mono<Order> createOrder(Order order) {
		Mono<Order> insOrder = orderReactiveRepository.insert(order);
		insOrder.subscribe(created_order -> {
			initiate_Kafka_Event_for_Create(created_order);
		});
		return insOrder;
	}

	@Override
	public Mono<Order> getOrderById(String orderId) {
		return orderReactiveRepository.findById(orderId);
	}

	@Override
	public Flux<Order> getAllOrder() {
		return orderReactiveRepository.findAll();
	}

	@Override
	public Mono<Order> updateOrder(String orderId, Order updated_order) {

		Mono<Order> return_updated_order = orderReactiveRepository.save(updated_order);
		Mono<Order> order = getOrderById(orderId);

		order.subscribe(old_order -> {
			initiate_Kafka_Event_for_Update(old_order, updated_order, orderId);
		});

		return return_updated_order;
	}

	@Override
	public Mono<Order> deleteOrder(String orderId) {

		final Mono<Order> order = getOrderById(orderId);
		if (Objects.isNull(order)) {
			return Mono.empty();
		}
		Mono<Order> return_deleted_Order = getOrderById(orderId).switchIfEmpty(Mono.empty()).filter(Objects::nonNull)
				.flatMap(orderToBeDeleted -> orderReactiveRepository.delete(orderToBeDeleted)
						.then(Mono.just(orderToBeDeleted)));

		order.subscribe(deleted_order -> {
			initiate_Kafka_Event_for_Delete(deleted_order);
		});
		return return_deleted_Order;

	}

	/****************** Start :: intiate kafka event for create ******************/
	private void initiate_Kafka_Event_for_Create(Order order) {

		productCountEventDTO.setOrderId(order.getOrderId());
		productCountEventDTO.setProductId(order.getProduct().getProductId());
		productCountEventDTO.setProductCount(order.getProduct().getProductCount());
		productCountEventDTO.setActionType(ActionType.COUNT_DECREMENT);

		customerCreditLimitEventDTO.setOrderId(order.getOrderId());
		customerCreditLimitEventDTO.setCustomerId(order.getCustomerId());
		customerCreditLimitEventDTO.setCreditLimitAmount(order.getOrderAmount());
		customerCreditLimitEventDTO.setActionType(ActionType.CREDIT_DECREMENT);

		try {
			productCustomerMsgProducer.sendToProduct(productCountEventDTO);
			productCustomerMsgProducer.sendToCustomer(customerCreditLimitEventDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	/****************** End :: intiate kafka event for create ******************/
	
	/****************** Start :: intiate kafka event for update order ******************/
	private void initiate_Kafka_Event_for_Update(Order old_order, Order updated_order, String orderId) {

		/****************** Start :: Product Count Calculation ******************/
		if (old_order.getProduct().getProductCount() != updated_order.getProduct().getProductCount()) {
			
			productCountEventDTO.setOrderId(orderId);
			productCountEventDTO.setProductId(updated_order.getProduct().getProductId());

			if (old_order.getProduct().getProductCount() > updated_order.getProduct().getProductCount()) {
				int count_diff = (old_order.getProduct().getProductCount())
						- (updated_order.getProduct().getProductCount());
				productCountEventDTO.setProductCount(count_diff);
				productCountEventDTO.setActionType(ActionType.COUNT_INCREMENT);

			} else {
				int count_diff = (updated_order.getProduct().getProductCount())
						- (old_order.getProduct().getProductCount());
				productCountEventDTO.setProductCount(count_diff);
				productCountEventDTO.setActionType(ActionType.COUNT_DECREMENT);
			}
			try {
				productCustomerMsgProducer.sendToProduct(productCountEventDTO);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		/****************** End :: Product Count Calculation ******************/

		/****************** Start :: Customer Credit Limit Calculation ******************/
		
		if (old_order.getOrderAmount() != updated_order.getOrderAmount()) {
			
			customerCreditLimitEventDTO.setOrderId(orderId);
			customerCreditLimitEventDTO.setCustomerId(updated_order.getCustomerId());

			if (old_order.getOrderAmount() > updated_order.getOrderAmount()) {
				double credit_diff = (old_order.getOrderAmount()) - (updated_order.getOrderAmount());
				customerCreditLimitEventDTO.setCreditLimitAmount(credit_diff);
				customerCreditLimitEventDTO.setActionType(ActionType.CREDIT_INCREMENT);
			} else {
				double credit_diff = (updated_order.getOrderAmount()) - (old_order.getOrderAmount());
				customerCreditLimitEventDTO.setCreditLimitAmount(credit_diff);
				customerCreditLimitEventDTO.setActionType(ActionType.CREDIT_DECREMENT);

			}
			try {

				productCustomerMsgProducer.sendToCustomer(customerCreditLimitEventDTO);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}

		}

		/****************** End :: Credit Limit Calculation ******************/

	}
	/****************** End :: intiate kafka event for update order ******************/
	
	/****************** Start :: intiate kafka event for delete ******************/
	private void initiate_Kafka_Event_for_Delete(Order order) {

		productCountEventDTO.setOrderId(order.getOrderId());
		productCountEventDTO.setProductId(order.getProduct().getProductId());
		productCountEventDTO.setProductCount(order.getProduct().getProductCount());
		productCountEventDTO.setActionType(ActionType.COUNT_INCREMENT);

		customerCreditLimitEventDTO.setOrderId(order.getOrderId());
		customerCreditLimitEventDTO.setCustomerId(order.getCustomerId());
		customerCreditLimitEventDTO.setCreditLimitAmount(order.getOrderAmount());
		customerCreditLimitEventDTO.setActionType(ActionType.CREDIT_INCREMENT);

		try {
			productCustomerMsgProducer.sendToProduct(productCountEventDTO);
			productCustomerMsgProducer.sendToCustomer(customerCreditLimitEventDTO);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
	/****************** End :: intiate kafka event for delete ******************/

}
