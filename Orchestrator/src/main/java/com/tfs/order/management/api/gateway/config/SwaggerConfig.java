package com.tfs.order.management.api.gateway.config;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Primary
public class SwaggerConfig implements SwaggerResourcesProvider  {
	
	@Autowired
	RouteLocator routeLocator;
	
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.genericModelSubstitutes(Mono.class, Flux.class, Publisher.class)
				.apiInfo(new ApiInfoBuilder()
						.title("Zuul API Gateway")
						.description("This Microservice holds the following responsibilty for <b> Zuul API Gateway Service</b> : <br /> "
								+ "<ol>\n" + 
								"  </ol>")
						.version("1.0")
						.build())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}

	@Override
	public List<SwaggerResource> get() {
		//Dynamic introduction of micro services using routeLocator
		List<SwaggerResource> resources = new ArrayList<>();
		resources.add(swaggerResource("api-gateway-server","/v2/api-docs","1.0"));
		//Recycling Lambda expressions to simplify code
		routeLocator.getRoutes().forEach(route ->{
			//Dynamic acquisition
			resources.add(swaggerResource(route.getId(),route.getFullPath().replace("**", "v2/api-docs"), "1.0"));
		});
		//You can also directly inherit the Consumer interface
		routeLocator.getRoutes().forEach(new Consumer<Route>() {

			@Override
			public void accept(Route t) {
				// TODO Auto-generated method stub
				
			}
		});
		return resources;
	}
	
	private SwaggerResource swaggerResource(String name,String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation(location);
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}
}
