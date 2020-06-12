package com.tfs.order.mgmt.customer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.tfs.order.mgmt.customer.domain.entity.Customer;

/**
 * This class extends the ReactiveMongoRepository interface, which inherits from ReactiveCrudRepository 
 * and adds reactive query methods for the Customer Entity.
 * 
 * @author AmlanSaha
 *
 */
@Repository
public interface CustomerReactiveRepository extends ReactiveMongoRepository<Customer, String> {

//	Flux<Customer> findByFirstName(final String firstName);
//	
//	Mono<Customer> findOneByFirstName(final String firstName);
}
