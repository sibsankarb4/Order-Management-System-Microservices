package com.tfs.order.mgmt.order.config;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.fasterxml.classmate.TypeResolver;
import com.tfs.order.mgmt.order.model.dto.Customer;
import com.tfs.order.mgmt.order.model.entity.Order;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {
	
	@Bean
	public Docket api() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(Flux.class, Publisher.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Mono.class, 
						typeResolver.resolve(Mono.class, Order.class)), Order.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Mono.class, 
						typeResolver.resolve(ResponseEntity.class, Order.class)), Order.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Mono.class, 
						Order.class), Order.class))
				.apiInfo(new ApiInfoBuilder()
						.title("Order Microservice")
						.description("This Microservice holds the following responsibilty for <b>Order Microservice</b> : <br /> "
								+ "<ol>\n" + 
								"  		<li>API to retrieve the list of all available Orders.</li>\n" + 
								"  		<li>API to retrieve a Order details by Order Id.</li>\n" + 
								"  		<li>API to responsible to create a new Order.</li>\n" + 
								"  		<li>API to responsible to update the details of an existing Order.</li>\n" + 
								" 		<li>API to responsible to delete the details of an existing Order.</li>\n" + 
								"  </ol>")
						.version("1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

}
