package com.sb.customer.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationException extends RuntimeException {

	private String message;
	
	public ApplicationException(String message) {
		this.message = message;
	}
}
