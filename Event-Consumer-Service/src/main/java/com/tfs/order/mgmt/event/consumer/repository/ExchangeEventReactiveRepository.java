package com.tfs.order.mgmt.event.consumer.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.tfs.order.mgmt.event.consumer.domain.entity.ExchangeEvent;

/**
 * This class extends the ReactiveMongoRepository interface, which inherits from ReactiveCrudRepository 
 * and adds reactive query methods for the ExchangeEvent Entity.
 * 
 * @author AmlanSaha
 */
@Repository
public interface ExchangeEventReactiveRepository extends ReactiveMongoRepository<ExchangeEvent, String> {
}
