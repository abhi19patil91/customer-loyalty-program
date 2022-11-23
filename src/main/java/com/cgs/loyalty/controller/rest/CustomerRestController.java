package com.cgs.loyalty.controller.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;
import com.cgs.loyalty.exception.BusinessException;
import com.cgs.loyalty.exception.ControllerException;
import com.cgs.loyalty.service.customer.LoyaltyCustomerService;

@RestController
public class CustomerRestController {

	@Autowired
	private LoyaltyCustomerService loyaltyCustomerService;

	// Register a customer into Loyalty program
	
	@PostMapping("/register")
	public ResponseEntity<?> registerLoyaltyCustomer(@RequestBody LoyaltyCustomerDetails customer) {
		try {
			LoyaltyCustomerDetails savedCustomer = loyaltyCustomerService.save(customer);
			return new ResponseEntity<LoyaltyCustomerDetails>(savedCustomer, HttpStatus.CREATED);
		} 
		catch (BusinessException e) {
			ControllerException ce = new ControllerException(e.getErrorCode(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} 
		catch (Exception e) {
			ControllerException ce = new ControllerException("611", "something went wrong in controller");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Get Customers
	
	@GetMapping("/customers")
	public ResponseEntity<List<LoyaltyCustomerDetails>> getAllCustomer(){
			List<LoyaltyCustomerDetails> customer = loyaltyCustomerService.getAllCustomer();
			return new ResponseEntity<List<LoyaltyCustomerDetails>>(customer,HttpStatus.FOUND);		
	}
	
	// Get Customer
	
	@GetMapping("/customer/{customerId}")
	public ResponseEntity<LoyaltyCustomerDetails> getCustomer(@PathVariable ("customerId") String customerId){
		LoyaltyCustomerDetails customer = loyaltyCustomerService.getCustomer(customerId);
		return new ResponseEntity<LoyaltyCustomerDetails>(customer,HttpStatus.FOUND);
		
	}

	// Update Customer
	
	@PutMapping("/update")
	public ResponseEntity<LoyaltyCustomerDetails> updateCustomer(@RequestBody LoyaltyCustomerDetails customer){
		LoyaltyCustomerDetails savedCustomer =loyaltyCustomerService.addCustomer(customer);
		return new ResponseEntity<LoyaltyCustomerDetails>(savedCustomer,HttpStatus.CREATED);
	}

	// Delete Customer
	
	@DeleteMapping("/delete/{customerId}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable ("customerId") String customerId){
		loyaltyCustomerService.deleteById(customerId);
		return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
	}

}
