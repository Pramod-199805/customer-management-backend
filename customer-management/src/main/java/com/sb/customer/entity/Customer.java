package com.sb.customer.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "CUSTOMERS")
@EntityListeners(AuditingEntityListener.class)
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "CUSTOMER_ID")
	private String customerId;
	
	@NotBlank(message = "Fist Name cannot be null")
	@Column(name = "FIRST_NAME")
	private String firstName;
	
	@Column(name = "LAST_NAME")
	private String lastName;
	
	@Column(name = "EMAIL",unique = true)
	@NotBlank(message = "Email cannot be null")
	private String email;

	@Column(name = "STREET")
	@NotBlank(message = "Street cannot be null")
	private String street;
	
	@Column(name = "CITY")
	@NotBlank(message = "City cannot be null")
	private String city;
	
	@Column(name = "STATE")
	@NotBlank(message = "Sate cannot be null")
	private String state;
	
	@Column(name = "PHONE_NUMBER")
	@NotBlank(message = "Phone number cannot be null")
	@Size(min = 10,max = 10)
	private String phoneNumber;
	
	
	@Column(name = "CREATED_AT")
	@CreatedDate
	private LocalDateTime createdDate;
	
	@Column(name = "MODIFIED_AT")
	@LastModifiedDate
	private LocalDateTime modifiedDate;

}
