package com.slgerkamp.introductory.spring.boot.exceptions.api;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slgerkamp.introductory.spring.boot.exceptions.exception.AddErrorInformationException;
import com.slgerkamp.introductory.spring.boot.exceptions.exception.OrderNotFoundException;

@RestController
@RequestMapping("/global")
public class WithoutExceptionHandlingController {

	protected Logger logger;

	public WithoutExceptionHandlingController() {
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
	
	/**
	 * ステータスコード409が返却される
	 * @return
	 */
	@RequestMapping("/order/{id}")
	String throwIllegalArgumentException(){
		throw new IllegalArgumentException();
	}

	/**
	 * SQLExceptionについては例外を通知せず、
	 * [databaseError]という文字列を
	 * 正常にリクエストが終了したものとする
	 * @return
	 * @throws SQLException
	 */
	@RequestMapping("/databaseError")
	String throwDatabaseException1() throws SQLException {
		logger.info("Throw SQLException");
		throw new SQLException();
	}
	
	@RequestMapping("/add")
	String throwAddErrorInformationException() throws SQLException {
		logger.info("Throw AddErrorInformationException");
		throw new AddErrorInformationException();
	}
}	