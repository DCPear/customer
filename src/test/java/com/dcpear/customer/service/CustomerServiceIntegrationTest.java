package com.dcpear.customer.service;

import com.dcpear.customer.domain.Level;
import com.dcpear.customer.service.CustomerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void getCustomerCount(){
    assertEquals(true, customerService.getCustomerCount() == 2.0);
    }

    @Test
    public void getLevelCount(){
        assertEquals(true, customerService.getLevelCount(Level.Gold) == 1.0);
    }
}
