package com.dcpear.customer.service;

import com.dcpear.customer.domain.Customer;
import com.dcpear.customer.domain.Level;
import com.dcpear.customer.repo.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public long getCustomerCount() {
        return customerRepository.count();
    }

    public long getLevelCount(Level level){
       long levelCount = customerRepository.findAllByLevel(level).stream().count();
        System.out.println("number of "+level + " members = " + levelCount);
        return levelCount;
    }
}
