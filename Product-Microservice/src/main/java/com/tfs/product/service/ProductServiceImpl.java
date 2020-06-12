package com.tfs.product.service;

import java.util.Objects;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndReplaceOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.tfs.product.contstant.ProductConstants;
import com.tfs.product.enums.ActionType;
import com.tfs.product.model.Product;
import com.tfs.product.model.ProductCountEventDTO;
import com.tfs.product.repository.ProductRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author AvkashKumar
 *
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;
	

    /**
     *  method used to add product.
     */
	@Override
	public Mono<Product> addproduct(Product product) {
		return productRepository.save(product);
	}

  /**
   * 
   */
	@Override
	public Mono<Product> getProductById(String Id) {
		System.out.println("*******************"+Id);
		return productRepository.findById(Id);
	}

    /**
     * method used to get all product.
     */
	@Override
	public Flux<Product> getAllProduct() {
		return productRepository.findAll().switchIfEmpty(Flux.empty());
	}

    /**
     * method used to update product
     */
	@Override
	public Mono<Product> updateProduct(String productId ,Product product) {
		    Query updateQuery = new Query();
			updateQuery.addCriteria(Criteria.where(ProductConstants.PRODUCT_ID_KEY).is(productId));
	  
			return reactiveMongoTemplate.findAndReplace(updateQuery, product, FindAndReplaceOptions.options().returnNew());
		 }

    /**
     * method used to delete Product
     */
	@Override
	public Mono<Product> deleteProduct(String productId) { 
		final Mono<Product> product = getProductById(productId);
		  if (Objects.isNull(product)) {
		   return Mono.empty();
		  }
		  return getProductById(productId).switchIfEmpty(Mono.empty()).filter(Objects::nonNull).flatMap(productToBeDeleted -> productRepository
		    .delete(productToBeDeleted).then(Mono.just(productToBeDeleted)));
		 }
	
	
	public Product updateProductCount(ProductCountEventDTO productCountEventDTO) {
		Product product = reactiveMongoTemplate.findById(productCountEventDTO
				.getProductId(), Product.class)
				.block();
		
		Integer currentProductQuantity = product.getQtyInStock();
		
		if(StringUtils.equals(ActionType.COUNT_INCREMENT.name(), productCountEventDTO.getActionType().name())) {
			product.setQtyInStock(currentProductQuantity+productCountEventDTO.getProductCount());
		} else {
			product.setQtyInStock(currentProductQuantity-productCountEventDTO.getProductCount());
		}
		
		Query updateQuery = new Query();
		updateQuery.addCriteria(Criteria.where(ProductConstants.PRODUCT_ID_KEY).is(product.getProducId()));
		return reactiveMongoTemplate.findAndReplace(updateQuery, product, FindAndReplaceOptions.options().returnNew()).block();
		
	}	
}
;

	


