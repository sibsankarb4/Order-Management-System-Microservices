package com.tfs.order.mgmt.customer.service;

import com.tfs.order.mgmt.customer.domain.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.customer.domain.entity.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * This Service interface is responsible to expose following contracts of Customer entity :
 * <ol>
 * 		<li>API to responsible to create a new Customer.</li>
 * 		<li>API to retrieve a customer details by Customer Id.</li>
 * 		<li>API to retrieve the list of all available Customers.</li>
 * 		<li>API to responsible to update the details of an existing Customer.</li>
 * 		<li>API to responsible to delete the details of an existing Customer.</li>
 * </ol> 
 * @author AmlanSaha
 *
 */
public interface CustomerService {

	/**
	 * This API is responsible to create a new Customer
	 * 
	 * @param customer
	 * 			Customer Entity
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Mono<Customer>
	 */
	Mono<Customer> saveCustomer(Customer customer);
	
	/**
	 * This API is responsible to retrieve a customer details by Customer Id.
	 * 
	 * @param id
	 * 			Customer Id
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Mono<ResponseEntity<Customer>>
	 */
	Mono<Customer> findCustomerById(String customerId);
	
	/**
	 * This API is responsible to retrieve the list of all available Customers.
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Flux<Customer>
	 */
	Flux<Customer> findAllCustomer();
	
	/**
	 * This API is responsible to update the details of an existing Customer
	 * 
	 * @param id
	 * 			Customer Id
	 * @param customer
	 * 			Customer Entity
	 * 
	 * @return Mono<ResponseEntity<Customer>>
	 */
	Mono<Customer> updateCustomer(String customerId, Customer customer);
	
	/**
	 * This API is responsible to delete an existing Customer
	 * 
	 * @param id
	 * 			Customer Id
	 * 
	 * @return Mono<ResponseEntity<Customer>>
	 */
	Mono<Customer> deleteCustomer(String customerId);

	/**
	 * This API is responsible to update the credit limit of an existing Customer
	 * 
	 * @param customerCreditLimitEvent
	 * 				Customer Credit Limit Event
	 * 
	 * @return Customer
	 */
	void updateCustomerCreditLimit(CustomerCreditLimitEventDTO customerCreditLimitEvent);
}
