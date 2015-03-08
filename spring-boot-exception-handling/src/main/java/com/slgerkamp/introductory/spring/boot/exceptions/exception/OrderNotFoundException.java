package com.slgerkamp.introductory.spring.boot.exceptions.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 例外が発生したら、
 * ステータスコード404を返却する
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="取り引きが見つかりません")
public class OrderNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 855578754995511181L;

}
