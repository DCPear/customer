package com.dcpear.customer.service;

import com.dcpear.customer.domain.Customer;
import com.dcpear.customer.domain.Level;
import com.dcpear.customer.repo.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {

    private static final Level CUSTOMER_LEVEL = Level.Gold;
    /**
     * create mock object to simulate behaviour of the real object.
     */
    @Mock
    private CustomerRepository customerRepositoryMock;

    @Mock
    private Customer customerMock;
    /**
     * create Object to inject mock dependency, because on this case we simulate about UserService,
     * so we create @InjectMock based on UserService.
     */

    @InjectMocks //Autowire CustomerService(customerRepository);
    private CustomerService customerService;

    /**
     * Mock responses to commonly invoked methods.
     */
    @Before
    public void setupReturnValuesOfMockMethods() {
        when(customerRepositoryMock.count()).thenReturn((11L));
        when(customerRepositoryMock.findAllByLevel(CUSTOMER_LEVEL)).thenReturn(Arrays.asList(customerMock));
    }

    /*******************************************************************************************
     * Verify the service return value
     ******************************************************************************************/
    @Test
    public void getCustomerCount() {
       assertEquals(11L,customerService.getCustomerCount() );
    }

    @Test
    public void getLevelCount() {
       assertEquals(Arrays.asList(customerMock).size(),customerService.getLevelCount(Level.Gold));
    }

}