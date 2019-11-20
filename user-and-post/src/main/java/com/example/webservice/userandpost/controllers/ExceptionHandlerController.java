package com.example.webservice.userandpost.controllers;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.webservice.userandpost.exceptions.ResourceNotFoundException;
import com.example.webservice.userandpost.valueobject.ExceptionResponse;

@ControllerAdvice
@Controller
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity handleAllExceptions(Exception ex, WebRequest req) {
		ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity(resp,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public final ResponseEntity handleNotFoundException(Exception ex, WebRequest req) {
		ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), req.getDescription(false));
		return new ResponseEntity(resp,HttpStatus.NOT_FOUND);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ExceptionResponse resp = new ExceptionResponse(new Date(), ex.getMessage(), ex.getBindingResult().toString());
		return new ResponseEntity(resp,HttpStatus.BAD_REQUEST);
	}
	
}
