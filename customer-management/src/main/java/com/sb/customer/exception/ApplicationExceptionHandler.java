package com.sb.customer.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.sb.customer.utility.ResponseObject;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;

@RestControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ApplicationException.class)
	public ResponseEntity<ExceptionObject> handlingResourceNotFoundException(ApplicationException exception) {
		ExceptionObject object = ExceptionObject.builder().message(exception.getMessage())
				.timeStamp(LocalDateTime.now()).build();
		return new ResponseEntity<>(object, HttpStatusCode.valueOf(400));
	}

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<List<Map<Path, String>>> handle(ConstraintViolationException constraintViolationException) {
		Map<Path, String> er = new HashMap<>();
		List<Map<Path, String>> errors = new ArrayList<>();
		constraintViolationException.getConstraintViolations()
				.forEach(error -> er.put(error.getPropertyPath(), error.getMessage()));
		errors.add(er);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(MalformedJwtException.class)
	public ResponseEntity<Object> handleMalformedJwtException(MalformedJwtException ex) {
		return new ResponseEntity<>(new ResponseObject("Invalid Token"), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(SignatureException.class)
	public ResponseEntity<Object> handleMalformedJwtException(SignatureException ex) {
		return new ResponseEntity<>(new ResponseObject("Invalid Token"), HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<Object> handleMalformedJwtException(ExpiredJwtException ex) {
		return new ResponseEntity<>(new ResponseObject("Token Expired"), HttpStatus.UNAUTHORIZED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		Map<String, String> er = new HashMap<>();
		List<Map<String, String>> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorDesc = error.getDefaultMessage();
			er.put(fieldName, errorDesc);
		});
		errors.add(er);

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

}
