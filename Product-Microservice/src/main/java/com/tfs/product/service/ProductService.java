package com.tfs.product.service;


import com.tfs.product.model.Product;
import com.tfs.product.model.ProductCountEventDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author AvkashKumar
 *
 */
public interface ProductService {
	/**
	 * 
	 * @param product
	 * @return
	 */
	public Mono<Product> addproduct(Product product);
	/**
	 * 
	 * @param Id
	 * @return
	 */
	
	public Mono<Product> getProductById(String Id);
	/**
	 * 
	 * @return
	 */
	public Flux<Product>getAllProduct();
	/**
	 * 
	 * @param productId
	 * @param product
	 * @return
	 */
	public Mono<Product> updateProduct(String productId ,Product product);
	/**
	 * 
	 * @param productId
	 * @return
	 */
	
	public Mono<Product> deleteProduct(String productId);
	
	/**
	 * 
	 * @param productCountEventDTO
	 * @return
	 */
	Product updateProductCount(ProductCountEventDTO productCountEventDTO);

}
