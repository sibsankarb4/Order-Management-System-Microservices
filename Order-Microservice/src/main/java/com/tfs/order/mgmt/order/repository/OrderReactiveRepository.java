package com.tfs.order.mgmt.order.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.tfs.order.mgmt.order.model.entity.Order;

@Repository
public interface OrderReactiveRepository extends ReactiveMongoRepository<Order, String> {
	
}
