package com.sb.customer.service;

import java.util.Optional;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sb.customer.dto.AuthenticationResponse;
import com.sb.customer.dto.UserDTO;
import com.sb.customer.entity.User;
import com.sb.customer.exception.ApplicationException;
import com.sb.customer.jwtservice.JWTService;
import com.sb.customer.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	private final PasswordEncoder passwordEncoder;

	private final AuthenticationManager authenticationManager;

	private final JWTService jwtService;

	public String signUp(UserDTO userDTO) {
		Optional<User> findByEmail = userRepository.findByEmail(userDTO.getEmail());
		if (ObjectUtils.isNotEmpty(userDTO)) {
			if (findByEmail.isEmpty()) {
				ModelMapper mapper = new ModelMapper();
				User user = mapper.map(userDTO, User.class);
				user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
				userRepository.save(user);
				return "User registered successfully";
			} else {
				throw new ApplicationException("User already exists!");
			}
		}
		throw new ApplicationException("User details cannot be empty");
	}

	public AuthenticationResponse signIn(UserDTO userDTO) {
		if (ObjectUtils.isNotEmpty(userDTO)) {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
			User user = userRepository.findByEmail(userDTO.getEmail())
					.orElseThrow(() -> new ApplicationException("User not found"));
			String token = jwtService.generateToken(user);
			AuthenticationResponse response = new AuthenticationResponse();
			response.setToken(token);
			response.setUserId(user.getUserId());
			return response;

		}
		throw new ApplicationException("User details cannot be empty");
	}

	public boolean validateToken(String authorizationHeader) {

		if (StringUtils.startsWith(authorizationHeader, "Bearer ")) {
			String token = authorizationHeader.substring(7);
			boolean validToken = jwtService.isTokenValid(token);
			if (validToken) {
				return true;
			} else {
				throw new ApplicationException("Invalid token");
			}

		} else {
			return false;
		}
	}

}
