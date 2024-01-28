package com.sb.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sb.customer.entity.User;
import java.util.List;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {

	public Optional<User> findByEmail(String email);
}
