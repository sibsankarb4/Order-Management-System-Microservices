package com.tfs.order.mgmt.customer.config;

import org.reactivestreams.Publisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import com.fasterxml.classmate.TypeResolver;
import com.tfs.order.mgmt.customer.domain.entity.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebFlux;

/**
 * This class is responsible for enable Swagger, which is a specification and
 * complete framework implementation for describing, producing, consuming, and
 * visualizing RESTful web services. </br>
 * The goal of Swagger is to enable client and
 * documentation systems to update at the same pace as the server.
 * 
 * @author AmlanSaha
 *
 */
@Configuration
@EnableSwagger2WebFlux
public class SwaggerConfig {

	/**
	 * This method is responsible for following operations :
	 * <ol>
	 * 		<li><b>genericModelSubstitutes: </b>Substitutes each generic class with it's direct parameterized type.</li>
	 * 		<li><b>alternateTypeRules: </b>Adds model substitution rules</li>
	 * 		<li><b>apiInfo: </b>Sets the api's meta information as included in the json ResourceListing response.</li>
	 * 		<li><b>select: </b>Initiates a builder for api selection.</li>
	 * </ol>
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Docket
	 */
	@Bean
	public Docket api() {
		
		TypeResolver typeResolver = new TypeResolver();
		
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(Flux.class, Publisher.class)
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Mono.class, 
						typeResolver.resolve(ResponseEntity.class, Customer.class)), Customer.class))
				.alternateTypeRules(AlternateTypeRules.newRule(typeResolver.resolve(Mono.class, 
						Customer.class), Customer.class))
				.apiInfo(new ApiInfoBuilder()
						.title("Customer Microservice")
						.description("This Microservice holds the following responsibilty for <b>Customer Microservice</b> : <br /> "
								+ "<ol>\n" + 
								"  		<li>API to retrieve the list of all available Customers.</li>\n" + 
								"  		<li>API to retrieve a customer details by Customer Id.</li>\n" + 
								"  		<li>API to responsible to create a new Customer.</li>\n" + 
								"  		<li>API to responsible to update the details of an existing Customer.</li>\n" + 
								" 		<li>API to responsible to delete the details of an existing Customer.</li>\n" + 
								"  </ol>")
						.version("1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
}
