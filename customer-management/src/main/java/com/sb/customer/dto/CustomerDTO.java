package com.sb.customer.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
	
	@NotBlank(message = "Fist Name cannot be null")
	private String firstName;
	
	private String lastName;

	@NotBlank(message = "Email cannot be null")
	@Email(message = "Please enter a valid email address, such as 'example@example.com'")
	private String email;
	
	@NotBlank(message = "Street cannot be null")
	private String street;
	
	@NotBlank(message = "City cannot be null")
	private String city;
	
	@NotBlank(message = "Sate cannot be null")
	private String state;
	
	@NotBlank
	@Size(min = 10,max = 10,message = "Phone number cannot be null")
	private String phoneNumber;

}
