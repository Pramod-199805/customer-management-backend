package com.sb.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.service.annotation.GetExchange;

import com.sb.customer.dto.AuthenticationResponse;
import com.sb.customer.dto.UserDTO;
import com.sb.customer.service.UserService;
import com.sb.customer.utility.ResponseObject;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "user/v1/")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(value = "register",consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseObject> userSignUp(@Valid @RequestBody UserDTO userDTO) {
		String signUp = userService.signUp(userDTO);
		ResponseObject response = new ResponseObject(signUp);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PostMapping(value = "signin" ,consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseObject> getUserToken(@Valid @RequestBody UserDTO userDTO) {
		System.out.println(userDTO + "UD");
		AuthenticationResponse userToken = userService.signIn(userDTO);
		ResponseObject response = new ResponseObject(userToken);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "validate-token")
	public ResponseEntity<ResponseObject> getUserToken(@Nullable @RequestHeader("Authorization") String authorizationHeader) {
		boolean userToken = userService.validateToken(authorizationHeader);
		if(userToken) {
			return new ResponseEntity<>(new ResponseObject("Valid Token"), HttpStatus.OK);
		}
		return new ResponseEntity<>(new ResponseObject("InValid Token"), HttpStatus.UNAUTHORIZED);
	}

}
