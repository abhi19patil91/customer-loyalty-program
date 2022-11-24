package com.cgs.loyalty.service.customer;

import java.util.List;

import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;

public interface LoyaltyCustomerService {

	// Create Customer
	public LoyaltyCustomerDetails save(LoyaltyCustomerDetails customer);

	// Get Customers
	public List<LoyaltyCustomerDetails> getAllCustomer();
	
	// Get Customer
	public LoyaltyCustomerDetails getCustomer(String customerId);

	// Update Customer
	public LoyaltyCustomerDetails updateCustomer(LoyaltyCustomerDetails customer);
	
	// Delete Customer
	public void deleteById(String customerId);

	

	

}
