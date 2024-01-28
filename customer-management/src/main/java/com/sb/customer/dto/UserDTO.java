package com.sb.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@NotBlank
	@Email(message = "Email cannot be empty")
	private String email;

	@NotBlank(message = "Password cannot be null")
	private String password;
}
