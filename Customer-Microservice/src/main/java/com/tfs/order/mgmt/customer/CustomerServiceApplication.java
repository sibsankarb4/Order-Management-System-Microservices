package com.tfs.order.mgmt.customer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import brave.sampler.Sampler;

/**
 * This class is responsible to start the Customer Microservice and enable the
 * following functionality :
 * <ol>
 * 		<li><b>@EnableAutoConfiguration:</b> Enable Spring Boot’s auto-configuration mechanism</li>
 * 		<li><b>@ComponentScan:</b> Enable @Component scan on the package where the application is located</li> 
 * 		<li><b>@Configuration:</b> Allow to register extra beans in the context or import additional configuration classes</li>
 * 		<li><b>@EnableDiscoveryClient:</b> Activates the Netflix Eureka DiscoveryClient implementation</li>
 * <ol/>
 * 
 * @author AmlanSaha
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class CustomerServiceApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CustomerServiceApplication.class, args);
	}
	
	@Bean 
	public Sampler defaultSampler() { 
		return Sampler.ALWAYS_SAMPLE; 
	}
}