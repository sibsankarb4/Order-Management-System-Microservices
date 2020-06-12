package com.tfs.order.mgmt.customer.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * This class use the annotation @ControllerAdvice, which holds the exception handling methods 
 * and enable those to all controllers
 * 
 * @author AmlanSaha
 */
@ControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

	/**
	 * This method use the annotation @ExceptionHandler on it, so that Spring
	 * configuration will detect this annotation and register the method as
	 * exception handler for Runtime Exception class and its subclasses
	 * 
	 * @param ex
	 * 			RuntimeException
	 * 
	 * @author AmlanSaha
	 * 
	 * @return ResponseEntity<String>
	 */
	
	private static final Logger logger = LogManager.getLogger(ControllerExceptionHandler.class);
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex){
    	logger.error("Exception caught in handleRuntimeException :  {} " , ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
	 * This method use the annotation @ExceptionHandler on it, so that Spring
	 * configuration will detect this annotation and register the method as
	 * exception handler for Exception class and its subclasses
	 * 
	 * @param ex
	 * 			Exception
	 * 
	 * @author AmlanSaha
	 * 
	 * @return ResponseEntity<String>
	 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex){
    	logger.error("Exception caught in handleRuntimeException :  {} " , ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }


}
