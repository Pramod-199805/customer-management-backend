package com.sb.customer.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
	
	private String customerId;	
	private String firstName;	
	private String lastName;
	private String street;
	private String email;
	private String city;
	private String state;
	private String phoneNumber;

}
