package com.tfs.product.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.tfs.product.repository.ProductRepository;

/**
 * @author AvkashKumar
 *
 */
@Configuration
@EnableReactiveMongoRepositories(basePackageClasses = ProductRepository.class)
public class MongoConfig extends AbstractReactiveMongoConfiguration {
	
	private MongoProperties mongoProperties;

	@Autowired
	public MongoConfig(MongoProperties mongoProperties) {
		this.mongoProperties = mongoProperties;
	}

	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create();
	}

	@Override
	protected String getDatabaseName() {
		return mongoProperties.getDatabase();
	}

	@Bean
	public ReactiveMongoTemplate reactiveMongoTemplate() {
		return new ReactiveMongoTemplate(mongoClient(), getDatabaseName());
	}

	@Override
	public MongoClient reactiveMongoClient() {
		return MongoClients.create();
	}
}
