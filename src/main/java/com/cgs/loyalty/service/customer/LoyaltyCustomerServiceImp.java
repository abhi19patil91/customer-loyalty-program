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
		String regaxId = "^[\\d]{4}$";
		String regaxEmail = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
		String regaxDob = "^([0-2][0-9]|(3)[0-1])(\\/)(((0)[0-9])|((1)[0-2]))(\\/)\\d{4}$";
		boolean id = customer.getCustomerId().matches(regaxId);
		boolean email = customer.getEmail().matches(regaxEmail);
		boolean dob = customer.getDob().matches(regaxDob);

		if (id == false) {
			throw new BusinessException("301", "Please provide currect id like it contains only 4 digit numbers !!");
		} else if (email == false) {
			throw new BusinessException("302", "Please provide currect email formate like xxxx@gmail.com !!");
		} else if (dob == false) {
			throw new BusinessException("303", "Please provide currect dob formate like dd/mm/yyyy !!");
		}else {
			try {
				LoyaltyCustomerDetails savedCustomer = customerLoyaltyRepository.save(customer);
				return savedCustomer;
			} catch (IllegalArgumentException e) {
				throw new BusinessException("304 ", "given Customer is null");
			} catch (Exception e) {
				throw new BusinessException("305", "something went wrong in service layer while saving the Customer ");
			}
		}
		}

//		if (customer.getCustomerId().matches(regaxId)) {
//			try {
//
//				LoyaltyCustomerDetails savedCustomer = customerLoyaltyRepository.save(customer);
//				return savedCustomer;
//			} catch (IllegalArgumentException e) {
//				throw new BusinessException("302 ", "given Customer is null");
//			} catch (Exception e) {
//				throw new BusinessException("303", "something went wrong in service layer while saving the Customer ");
//			}
//		} else {
//			throw new BusinessException("301", "Please provide currect id like it contains only 4 digit numbers !!");
//		}
//	}

	@Override
	public List<LoyaltyCustomerDetails> getAllCustomer() {
		try {
			List<LoyaltyCustomerDetails> customerList = customerLoyaltyRepository.findAll();
			if (customerList.isEmpty())
				throw new BusinessException("304",
						"hai list is completely empty , we don,t have anything to return !!");
			return customerList;
		} catch (NoSuchElementException e) {
			throw new BusinessException("306", "given customer id is not present in db..");
		} catch (Exception e) {
			throw new BusinessException("305",
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
				throw new BusinessException("307", "given customer id is not present in db..");
			} catch (Exception e) {
				throw new BusinessException("308", "Something went wrong while getting customer");
			}
		} else {
			throw new BusinessException("309", "only 4 digit numbers allowed");
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
			throw new BusinessException("310", "given customer id is null or its not presnt in db or invalid formate");
		} catch (Exception e) {
			throw new BusinessException("311", "Something went wrong in service layer while updation of customer");
		}
	}

	@Override
	public void deleteById(String customerId) {

		LoyaltyCustomerDetails savedCust = customerLoyaltyRepository.findById(customerId).orElse(null);
		try {
			customerLoyaltyRepository.deleteById(savedCust.getCustomerId());
		} catch (NullPointerException e) {
			throw new BusinessException("312", "given customer id is null or its not presnt in db or invalid formate");
		} catch (Exception e) {
			throw new BusinessException("313", "Something went wrong while deletin customer");
		}
	}
}
