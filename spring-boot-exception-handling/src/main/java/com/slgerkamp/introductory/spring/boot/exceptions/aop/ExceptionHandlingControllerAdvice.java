package com.slgerkamp.introductory.spring.boot.exceptions.aop;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.slgerkamp.introductory.spring.boot.exceptions.exception.AddErrorInformationException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

	protected Logger logger;

	public ExceptionHandlingControllerAdvice() {
		logger = LoggerFactory.getLogger(getClass());
	}
	
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */
	/* アプリケーション共通のエラーハンドリング
	/* - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - */

	/**
	 *  IllegalArgumentExceptionがスローされたら、409を返却する
	 */
	@ResponseStatus(value=HttpStatus.CONFLICT, reason = "Global_IDが重複しています")
	@ExceptionHandler(IllegalArgumentException.class)
	public void conflict(){
		logger.error("Request raised a IllegalArgumentException");
	}
	
	/**
	 * SQLExceptionについては例外を通知しない
	 * 例外が返される
	 * @param exception
	 * @return
	 */
	@ExceptionHandler({ SQLException.class })
	public String databaseError(Exception exception) {
		logger.error("Request raised " + exception.getClass().getSimpleName());
		return "Global_databaseError";
	}
	
	/**
	 * エラーを更新
	 * @param e
	 * @param response
	 * @throws IOException
	 */
	@ExceptionHandler({ AddErrorInformationException.class })
	public void handleAddErrorInformationException(AddErrorInformationException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value(), "Global_追加できません");
	}
}
