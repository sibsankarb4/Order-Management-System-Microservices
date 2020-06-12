               package com.tfs.order.mgmt.customer.service.impl;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.tfs.order.mgmt.customer.constant.Constants;
import com.tfs.order.mgmt.customer.domain.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.customer.domain.entity.Customer;
import com.tfs.order.mgmt.customer.enums.ActionType;
import com.tfs.order.mgmt.customer.repository.CustomerReactiveRepository;
import com.tfs.order.mgmt.customer.service.CustomerService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This Service Implementation class is responsible to for the following functionality of Customer entity :
 * <ol>
 * 		<li>API to responsible to create a new Customer.</li>
 * 		<li>API to retrieve a customer details by Customer Id.</li>
 * 		<li>API to retrieve the list of all available Customers.</li>
 * 		<li>API to responsible to update the details of an existing Customer.</li>
 * 		<li>API to responsible to delete the details of an existing Customer.</li>
 * </ol> 
 * 
 * @author AmlanSaha
 */
@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerReactiveRepository customerReactiveRepository;  
	
	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;
	
	private static final Logger logger = LogManager.getLogger(CustomerServiceImpl.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mono<Customer> saveCustomer(Customer customer) {
		return customerReactiveRepository.insert(customer); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mono<Customer> findCustomerById(String customerId) {
		return customerReactiveRepository.findById(customerId); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Flux<Customer> findAllCustomer() {
		return customerReactiveRepository.findAll(); 
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mono<Customer> updateCustomer(String customerId, Customer customer) {
		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where(Constants.CUSTOMER_ID_KEY).is(customerId));
		return reactiveMongoTemplate.findAndReplace(updateQuery, customer, FindAndReplaceOptions.options().returnNew());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Mono<Customer> deleteCustomer(String customerId) {
		Query deleteQuery = new Query();
		deleteQuery.addCriteria(Criteria.where(Constants.CUSTOMER_ID_KEY).is(customerId));
		return reactiveMongoTemplate.findAndRemove(deleteQuery, Customer.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void updateCustomerCreditLimit(CustomerCreditLimitEventDTO customerCreditLimitEvent) {
		reactiveMongoTemplate.findById(customerCreditLimitEvent.getCustomerId(), Customer.class)
				.subscribe((customer) -> {
					Double currentCreditLimit = customer.getCreditLimit();

					if (StringUtils.equals(ActionType.CREDIT_INCREMENT.name(),
							customerCreditLimitEvent.getActionType().name())) {
						customer.setCreditLimit(currentCreditLimit + customerCreditLimitEvent.getCreditLimitAmount());
					} else {
						customer.setCreditLimit(currentCreditLimit - customerCreditLimitEvent.getCreditLimitAmount());
					}

					Query updateQuery = new Query();
					updateQuery.addCriteria(Criteria.where(Constants.CUSTOMER_ID_KEY).is(customer.getId()));
					reactiveMongoTemplate
							.findAndReplace(updateQuery, customer, FindAndReplaceOptions.options().returnNew()).block();
				});
	}	
}