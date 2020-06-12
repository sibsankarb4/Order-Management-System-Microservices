package com.tfs.order.management.api.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import brave.sampler.Sampler;

/**
 * This class is responsible to start the API Gateway service and enable the
 * following functionality :
 * <ol>
 * 		<li><b>@EnableAutoConfiguration:</b> Enable Spring Boot’s auto-configuration mechanism</li>
 * 		<li><b>@ComponentScan:</b> Enable @Component scan on the package where the application is located</li> 
 * 		<li><b>@Configuration:</b> Allow to register extra beans in the context or import additional configuration classes</li>
 * 		<li><b>@EnableZuulProxy:</b> Enabled Zuul Reverse Proxy</li>
 * 		<li><b>@EnableDiscoveryClient:</b> Activates the Netflix Eureka DiscoveryClient implementation</li>
 * <ol/>
 * 
 * @author AmlanSaha
 *
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication
public class ApiGatewayServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayServiceApplication.class, args);
	}

	/**
	 * This method is responsible for sampling. Sampling is an up-front decision,
	 * meaning that the decision to report data is made at the first operation in a
	 * trace and that decision is propagated downstream.
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Sampler
	 */
	@Bean
	public Sampler defaultSampler(){
		return Sampler.ALWAYS_SAMPLE;
	}
}
