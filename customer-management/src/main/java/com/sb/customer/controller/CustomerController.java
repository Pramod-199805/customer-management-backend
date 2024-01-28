package com.sb.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sb.customer.constant.ApplicationConstants;
import com.sb.customer.dto.CustomerDTO;
import com.sb.customer.dto.CustomerResponse;
import com.sb.customer.service.CustomerService;
import com.sb.customer.utility.PageResponse;
import com.sb.customer.utility.ResponseObject;

import jakarta.validation.Valid;

@RestController
@RequestMapping("customer/v1/")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping(value = "create", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseObject> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
		String createCustomer = customerService.createCustomer(customerDTO);
		ResponseObject responseObject = new ResponseObject(createCustomer);
		return new ResponseEntity<>(responseObject, HttpStatus.CREATED);

	}

	@PutMapping(value = "edit/{email}", consumes = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<ResponseObject> updateCustomer(@PathVariable String email,
			@Valid @RequestBody CustomerDTO customerDTO) {
		String updateCustomer = customerService.updateCustomer(email, customerDTO);
		ResponseObject responseObject = new ResponseObject(updateCustomer);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@GetMapping(value = "get/{email}")
	public ResponseEntity<ResponseObject> getCustomerById(@PathVariable String email) {
		CustomerResponse response = customerService.getCustomerById(email);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@DeleteMapping(value = "delete/{email}")
	public ResponseEntity<ResponseObject> deleteCustomerById(@PathVariable String email) {
		System.out.println("Delete");
		String response = customerService.deleteCustomerById(email);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@GetMapping(value = "")
	public ResponseEntity<ResponseObject> getAllCustomersByPagination(
			@RequestParam(name = "pageNumber", defaultValue = ApplicationConstants.PAGE_NUMBER) int pageNumber,
			@RequestParam(name = "pageSize", defaultValue = ApplicationConstants.PAGE_SIZE) int pageSize,
			@RequestParam(name = "sortDirection", defaultValue = ApplicationConstants.SORT_DIR) String sortDirection,
			@RequestParam(name = "sortBy", defaultValue = ApplicationConstants.SORT_BY) String sortBy) {
		System.out.println("Controller Class");
		PageResponse<CustomerResponse> response = customerService.getAllCustomers(pageNumber, pageSize, sortDirection,
				sortBy);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@GetMapping(value = "by-city")
	public ResponseEntity<ResponseObject> getAllCustomersByCity(@RequestParam String searchByCity) {
		PageResponse<CustomerResponse> response = customerService.getAllCustomersByCity(searchByCity);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@GetMapping(value = "by-phone")
	public ResponseEntity<ResponseObject> getAllCustomersByPhoneNumber(@RequestParam String searchByPhoneNumber) {
		PageResponse<CustomerResponse> response = customerService.getAllCustomersByPhoneNumber(searchByPhoneNumber);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

	@GetMapping(value = "by-name")
	public ResponseEntity<ResponseObject> getAllCustomersByFirstName(@RequestParam String searchByFirstName) {
		PageResponse<CustomerResponse> response = customerService.getAllCustomersLikeFirstName(searchByFirstName);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}
	
	@GetMapping(value = "all-customer")
	public ResponseEntity<ResponseObject> getAllCustomers(@RequestParam String searchBy) {
		PageResponse<CustomerResponse> response = customerService.getAllCustomers(searchBy);
		ResponseObject responseObject = new ResponseObject(response);
		return new ResponseEntity<>(responseObject, HttpStatus.OK);

	}

}
