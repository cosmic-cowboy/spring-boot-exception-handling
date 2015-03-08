package com.slgerkamp.introductory.spring.boot.exceptions.api;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.slgerkamp.introductory.spring.boot.exceptions.exception.AddErrorInformationException;
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

	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* このController独自のエラーハンドリング
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 *  IllegalArgumentExceptionがスローされたら、409を返却する
	 */
	@ResponseStatus(value=HttpStatus.CONFLICT, reason = "IDが重複しています")
	@ExceptionHandler(IllegalArgumentException.class)
	public void conflict(){
		logger.error("Request raised a IllegalArgumentException");
	}
	
	/**
	 * SQLExceptionについては例外を通知しない
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ SQLException.class })
	public String databaseError(Exception exception) {
		logger.error("Request raised " + exception.getClass().getSimpleName());
		return "databaseError";
	}
	
	/**
	 * エラーを更新
	 * @param e
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler({ AddErrorInformationException.class })
	public void handleAddErrorInformationException(AddErrorInformationException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "追加できません");
	}
	
}	