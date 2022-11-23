package com.cgs.loyalty.repository.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cgs.loyalty.entity.customer.LoyaltyCustomerDetails;

@Repository
public interface LoyaltyCustomerRepository extends JpaRepository<LoyaltyCustomerDetails, String> {

}
