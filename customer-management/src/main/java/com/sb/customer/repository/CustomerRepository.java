package com.sb.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sb.customer.entity.Customer;
import java.util.Optional;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, String> {

	public Optional<Customer> findByEmail(String email);

	public void deleteByEmail(String email);

	public List<Customer> findByCity(String city);

	public List<Customer> findByPhoneNumber(String phoneNumber);

	public List<Customer> findByFirstNameLike(String firstName);

	public boolean existsByEmail(String email);
	@Query("SELECT c FROM Customer c WHERE " + "c.firstName LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.lastName LIKE CONCAT('%', :searchTerm, '%') OR "  
			+ "c.email LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.street LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.state LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.city LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.phoneNumber LIKE CONCAT('%', :searchTerm, '%') OR "
			+ "c.email LIKE CONCAT('%', :searchTerm, '%')")
	public List<Customer> findByAnyColumnContaining(String searchTerm);

}
