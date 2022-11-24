package com.cgs.loyalty.service.customer;

import java.util.List;
import java.util.NoSuchElementException;
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
		try {
			List<LoyaltyCustomerDetails> customerList = customerLoyaltyRepository.findAll();
			if (customerList.isEmpty())
				throw new BusinessException("404", "hai list is completely empty , we don,t have anything to return !!");
			return customerList;
		} catch (NoSuchElementException e) {
			throw new BusinessException("410", "given customer id is not present in db..");
		}
		catch (Exception e) {
			throw new BusinessException("405",
					"something went wrong in service layer while fetching all the Customer ");
		}
	}

	@Override
	public LoyaltyCustomerDetails getCustomer(String customerId) {
		String regax = "^[\\d]{4}$";
		if (customerId.matches(regax)) {
			try {
				return customerLoyaltyRepository.findById(customerId).get();
			} catch (NoSuchElementException e) {
				throw new BusinessException("410", "given customer id is not present in db..");
			} catch (Exception e) {
				throw new BusinessException("411", "Something went wrong while getting customer");
			}
		} else {
			throw new BusinessException("412", "only 4 digit numbers allowed");
		}

	}

	@Override
	public LoyaltyCustomerDetails updateCustomer(LoyaltyCustomerDetails customer) {

		try {
			LoyaltyCustomerDetails existingCustomer = customerLoyaltyRepository.findById(customer.getCustomerId())
					.orElse(null);
			existingCustomer.setName(customer.getName());
			existingCustomer.setMobileNo(customer.getMobileNo());
			existingCustomer.setEmail(customer.getEmail());
			existingCustomer.setDob(customer.getDob());
			existingCustomer.setCustomerType(customer.getCustomerType());
			existingCustomer.setRating(customer.getRating());
			existingCustomer.setChannelOfRegistration(customer.getChannelOfRegistration());
			customerLoyaltyRepository.save(existingCustomer);
			return existingCustomer;
		} catch (NullPointerException e) {
			throw new BusinessException("408", "given customer id is null or its not presnt in db or invalid formate");
		} catch (Exception e) {
			throw new BusinessException("409", "Something went wrong in service layer while updation of customer");
		}
	}

	@Override
	public void deleteById(String customerId) {
		
		LoyaltyCustomerDetails savedCust = customerLoyaltyRepository.findById(customerId).orElse(null);
		try {
			customerLoyaltyRepository.deleteById(savedCust.getCustomerId());
		} catch (NullPointerException e) {
			throw new BusinessException("408", "given customer id is null or its not presnt in db or invalid formate");
		} catch (Exception e) {
			throw new BusinessException("409", "Something went wrong while deletin customer");
		}
		
		
		
		
	}
}
