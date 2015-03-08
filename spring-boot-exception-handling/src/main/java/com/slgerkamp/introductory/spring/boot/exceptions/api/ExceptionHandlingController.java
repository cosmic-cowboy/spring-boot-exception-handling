package com.slgerkamp.introductory.spring.boot.exceptions.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slgerkamp.introductory.spring.boot.exceptions.exception.OrderNotFoundException;

@RestController
@RequestMapping("/local")
public class ExceptionHandlingController {

	protected Logger logger;

	public ExceptionHandlingController() {
		logger = LoggerFactory.getLogger(getClass());
	}

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* リクエストハンドラ
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 * 例外が発生した場合、
	 * デフォルトでは、ステータスコード500が返却される
	 * @return 
	 */
	@RequestMapping("/order")
	String order() {
		logger.info("Throw RuntimeException");
		throw new RuntimeException();
	}
	
	/**
	 * ステータスコード404が返却される
	 * @return 
	 */
	@RequestMapping("/orderNotFound")
	String throwOrderNotFoundException() {
		logger.info("Throw OrderNotFoundException");
		throw new OrderNotFoundException();
	}

}	