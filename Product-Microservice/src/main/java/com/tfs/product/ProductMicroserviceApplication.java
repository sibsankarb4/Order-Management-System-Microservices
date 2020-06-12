package com.tfs.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

/**
 * @author AvkashKumar
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ProductMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductMicroserviceApplication.class, args);
	}
	
	@Bean 
	public Sampler defaultSampler() { 
		return Sampler.ALWAYS_SAMPLE; 
	}
}
