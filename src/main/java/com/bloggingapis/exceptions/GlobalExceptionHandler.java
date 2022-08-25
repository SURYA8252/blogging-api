package com.bloggingapis.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bloggingapis.payloads.ApisResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApisResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
    	String message = ex.getMessage();
    	ApisResponse apisResponse = new ApisResponse(message, false);
    	return new ResponseEntity<ApisResponse>(apisResponse,HttpStatus.NOT_FOUND);
    }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError) error).getField();
			String messsage = error.getDefaultMessage();
			resp.put(fieldName, messsage);
		});
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApisResponse> handleApiException(ApiException ex) {
		String message = ex.getMessage();
		ApisResponse apisResponse = new ApisResponse(message,true);
		return new ResponseEntity<ApisResponse>(apisResponse,HttpStatus.BAD_REQUEST);
	}
}
