package com.cgs.loyalty.service.customer;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;
import com.cgs.loyalty.exception.BusinessException;
import com.cgs.loyalty.repository.customer.LoyaltyCustomerRepository;

@Service
public class LoyaltyCustomerServiceImp implements LoyaltyCustomerService {

	@Autowired
	private LoyaltyCustomerRepository customerLoyaltyRepository;

	@Override
	public LoyaltyCustomerDetails save(LoyaltyCustomerDetails customer) {
		String regax = "^[\\d]{4}$";
		if (customer.getCustomerId().matches(regax)) {
			try {

				LoyaltyCustomerDetails savedCustomer = customerLoyaltyRepository.save(customer);
				return savedCustomer;
			} catch (IllegalArgumentException e) {
				throw new BusinessException("402 ", "given Customer is null");
			} catch (Exception e) {
				throw new BusinessException("403", "something went wrong in service layer while saving the Customer ");
			}
		} else {
			throw new BusinessException("401", "Please provide currect id like it contains only 4 digit numbers !!");
		}
	}

	@Override
	public List<LoyaltyCustomerDetails> getAllCustomer() {
		return null;
	}

	@Override
	public LoyaltyCustomerDetails getCustomer(String customerId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LoyaltyCustomerDetails addCustomer(LoyaltyCustomerDetails customer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(String customerId) {
		// TODO Auto-generated method stub
		
	}

	

	
}
