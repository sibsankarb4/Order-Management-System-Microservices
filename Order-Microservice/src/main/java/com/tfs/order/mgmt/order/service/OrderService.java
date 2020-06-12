package com.tfs.order.mgmt.order.service;

import com.tfs.order.mgmt.order.model.entity.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface OrderService {
	
    public Mono<Order> createOrder(Order order);
		
	public Mono<Order> getOrderById(String orderId);
	
	public Flux<Order> getAllOrder();
	
	public Mono<Order> updateOrder(String orderId, Order order);
	
	public Mono<Order> deleteOrder(String orderId);

}
