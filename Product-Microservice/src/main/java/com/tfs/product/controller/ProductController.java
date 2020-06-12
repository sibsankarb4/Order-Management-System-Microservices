package com.tfs.product.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tfs.product.model.Product;
import com.tfs.product.service.ProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author AvkashKumar
 *
 */
@RestController
@RequestMapping("/product")

public class ProductController {
	
	@Autowired
	private ProductService productService;

	/**
	 * 
	 * @param product
	 * @return
	 */
	@PostMapping(value = "/addProduct")
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Product> addProduct(@RequestBody Product product) {
		return productService.addproduct(product);

	}

	/**
	 * 
	 * @return
	 */
	@GetMapping(value = "/getAllProduct")
	public Flux<Product> getAllProduct() {
		return productService.getAllProduct();
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping(value = "/getProduct/{id}")
	public Mono<ResponseEntity<Product>> getProductById(@PathVariable("id") final String id) {
System.out.println("*****************************");
		return productService.getProductById(id).map((product) -> new ResponseEntity<>(product, HttpStatus.OK))
				.defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	/**
	 * 
	 * @param id
	 * @param product
	 * @return
	 */
	@PutMapping(value = "/updateProduct/{id}")
	public Mono<Product> updateProduct(@PathVariable("id") final String id, @RequestBody Product product) {

		return productService.updateProduct(id, product);
	}

	/**
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteProduct/{id}")
	public Mono<Product> deleteProduct(@PathVariable final String id) {

		return productService.deleteProduct(id);
	}

	/*
	 * @GetMapping("/publish/{event}") public String publishEvent(@PathVariable
	 * String event) { System.out.println("inside publish event" + event); return
	 * exchangeeventproducer.ExchangeEvent(event);
	 * 
	 * }
	 */
}