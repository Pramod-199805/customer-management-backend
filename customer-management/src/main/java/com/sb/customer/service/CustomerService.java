package com.sb.customer.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.sb.customer.dto.CustomerDTO;
import com.sb.customer.dto.CustomerResponse;
import com.sb.customer.entity.Customer;
import com.sb.customer.exception.ApplicationException;
import com.sb.customer.repository.CustomerRepository;
import com.sb.customer.utility.PageResponse;

import jakarta.transaction.Transactional;

import com.sb.customer.constant.ApplicationConstants;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	public String createCustomer(CustomerDTO customerDTO) {
		Customer customer = new ModelMapper().map(customerDTO, Customer.class);
		Optional<Customer> findByEmail = customerRepository.findByEmail(customerDTO.getEmail());
		if (!findByEmail.isPresent()) {
			customerRepository.save(customer);
			return "Customer has been created Successfully";
		} else {
			throw new ApplicationException(
					String.format("Customer with Email %s already present", customerDTO.getEmail()));
		}
	}

	public String updateCustomer(String email, CustomerDTO customerDTO) {
		Customer customer = new ModelMapper().map(customerDTO, Customer.class);
		Optional<Customer> existingCustomerWithEmail = customerRepository.findByEmail(email);
		if (existingCustomerWithEmail.isPresent()) {
			if (customerDTO.getEmail().equals(email) || !customerRepository.existsByEmail(customerDTO.getEmail())) {
				customer.setCustomerId(existingCustomerWithEmail.get().getCustomerId());
				customer.setCreatedDate(existingCustomerWithEmail.get().getCreatedDate());
				customerRepository.save(customer);
				return "Customer has been updated successfully";
			} else {
				throw new ApplicationException(
						String.format("Cannot update to %s as it already exists", customerDTO.getEmail()));
			}
		} else {
			throw new ApplicationException(String.format("Customer with email %s does not exist", email));
		}
	}

	public CustomerResponse getCustomerById(String email) {
		Optional<Customer> findByEmail = customerRepository.findByEmail(email);
		if (StringUtils.isNotBlank(email) && findByEmail.isPresent()) {
			Customer customer = customerRepository.findByEmail(email).get();
			return new ModelMapper().map(customer, CustomerResponse.class);
		} else {
			throw new ApplicationException(String.format("Customer with Email %s not present", email));
		}
	}

	public PageResponse<CustomerResponse> getAllCustomers(int pageNumber, int pageSize, String sortDirection,
			String sortBy) {
		pageNumber = pageNumber - 1;
		Pageable page = PageRequest.of(pageNumber, pageSize, Direction.valueOf(sortDirection.toUpperCase()), sortBy);
		Page<Customer> customers = customerRepository.findAll(page);
		List<CustomerResponse> customersList = customers.getContent().stream()
				.map(cust -> new ModelMapper().map(cust, CustomerResponse.class)).collect(Collectors.toList());
		PageResponse<CustomerResponse> response = new PageResponse<>();
		if (CollectionUtils.isEmpty(customersList)) {
			response.setMessage("No records found");
		}
		response.setDataList(customersList);
		response.setTotalNuberOfRecors(customers.getTotalElements());
		response.setTotalPageSize(customers.getNumberOfElements());
		response.setMessage(String.format(ApplicationConstants.PAGINATION_RECORDS, customers.getNumberOfElements(),
				customers.getTotalElements()));
		return response;
	}

	@Transactional
	public String deleteCustomerById(String email) {
		Optional<Customer> findByEmail = customerRepository.findByEmail(email);
		if (StringUtils.isNotBlank(email) && findByEmail.isPresent()) {
			customerRepository.deleteByEmail(email);
			return "Customer deleted succesfully";
		} else {
			throw new ApplicationException(String.format("Customer with Email %s not present", email));
		}
	}

	public PageResponse<CustomerResponse> getAllCustomersByCity(String searchByCity) {
		List<Customer> customers = customerRepository.findByCity(searchByCity);
		List<CustomerResponse> customersList = customers.stream()
				.map(cust -> new ModelMapper().map(cust, CustomerResponse.class)).collect(Collectors.toList());
		PageResponse<CustomerResponse> response = new PageResponse<>();
		if (CollectionUtils.isEmpty(customersList)) {
			response.setMessage("No records found");
		}
		response.setDataList(customersList);
		response.setTotalNuberOfRecors(customerRepository.count());
		response.setTotalPageSize(customersList.size());
		response.setMessage(String.format(ApplicationConstants.PAGINATION_RECORDS, customersList.size(),
				customerRepository.count()));
		return response;
	}

	public PageResponse<CustomerResponse> getAllCustomersByPhoneNumber(String searchByPhoneNumber) {
		List<Customer> customers = customerRepository.findByPhoneNumber(searchByPhoneNumber);
		List<CustomerResponse> customersList = customers.stream()
				.map(cust -> new ModelMapper().map(cust, CustomerResponse.class)).collect(Collectors.toList());
		PageResponse<CustomerResponse> response = new PageResponse<>();
		if (CollectionUtils.isEmpty(customersList)) {
			response.setMessage("Norecords found");
		}
		response.setDataList(customersList);
		response.setTotalNuberOfRecors(customerRepository.count());
		response.setTotalPageSize(customersList.size());
		response.setMessage(String.format(ApplicationConstants.PAGINATION_RECORDS, customersList.size(),
				customerRepository.count()));
		return response;
	}

	public PageResponse<CustomerResponse> getAllCustomersLikeFirstName(String searchByPhoneNumber) {
		List<Customer> customers = customerRepository.findByFirstNameLike(searchByPhoneNumber);
		List<CustomerResponse> customersList = customers.stream()
				.map(cust -> new ModelMapper().map(cust, CustomerResponse.class)).collect(Collectors.toList());
		PageResponse<CustomerResponse> response = new PageResponse<>();
		if (CollectionUtils.isEmpty(customersList)) {
			response.setMessage("Norecords found");
		}
		response.setDataList(customersList);
		response.setTotalNuberOfRecors(customerRepository.count());
		response.setTotalPageSize(customersList.size());
		response.setMessage(String.format(ApplicationConstants.PAGINATION_RECORDS, customersList.size(),
				customerRepository.count()));
		return response;
	}

	public PageResponse<CustomerResponse> getAllCustomers(String searchBy) {
		List<Customer> customers = customerRepository.findByAnyColumnContaining(searchBy);
		List<CustomerResponse> customersList = customers.stream()
				.map(cust -> new ModelMapper().map(cust, CustomerResponse.class)).collect(Collectors.toList());
		PageResponse<CustomerResponse> response = new PageResponse<>();
		if (CollectionUtils.isEmpty(customersList)) {
			response.setMessage("Norecords found");
		}
		response.setDataList(customersList);
		response.setTotalNuberOfRecors(customerRepository.count());
		response.setTotalPageSize(customersList.size());
		response.setMessage(String.format(ApplicationConstants.PAGINATION_RECORDS, customersList.size(),
				customerRepository.count()));
		return response;
	}
}
