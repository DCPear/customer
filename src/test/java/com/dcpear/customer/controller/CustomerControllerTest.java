package com.dcpear.customer.controller;

import com.dcpear.customer.domain.Customer;
import com.dcpear.customer.domain.Level;
import com.dcpear.customer.service.JwtRequestHelper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import java.time.LocalDate;


import static org.junit.Assert.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CustomerControllerTest {

    private static final String COMMON_URL = "/api";
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JwtRequestHelper jwtRequestHelper;

    @Autowired
    private TestRestTemplate restTemplate;

    /**
     * Initialise MockMvc (so that we don't need to initialize it inside every test.)
     */
    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    /**
     * Integration Test for Post method  add customers
     *
     * @throws Exception
     */
    @Test
    public void addCustomerTest() throws Exception {

        Customer customer = new Customer(
                3,
                "Liza",
                "DoLittle",
                LocalDate.parse("1980-08-16"),
                "me@me.com",
                "534567",
                "80 my road",
                Level.Silver);

        //convert to json String
        String jsonRequest = objectMapper.writeValueAsString(customer);

        MvcResult result = mockMvc.perform(
                post("/api/public/addCustomer")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Assert.assertEquals("application/json",
                result.getResponse().getContentType());
    }

    /**
     * Integration Test for Get method Get Customers
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_CSR"})
    @Test
    public void getCustomersTest1() throws Exception {

        String  accessToken = jwtRequestHelper.tokenWithRole("ROLE_CSR");

        MvcResult result = mockMvc.perform(
                get("/api/getCustomers")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value("1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].firstName").isNotEmpty())
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Get method Get Customers
     *
     * @throws Exception
     */
    @Test
    public void getCustomersTest() throws Exception {

        String CALL_URL = COMMON_URL+"/getCustomers";
        HttpEntity<String> request = new HttpEntity<>(jwtRequestHelper.httpHeaderWithRole("ROLE_CSR"));
        ResponseEntity<String> response = restTemplate.exchange(CALL_URL, HttpMethod.GET ,request,String.class );
        assertEquals((HttpStatus.OK),response.getStatusCode());
    }

    /**
     * Integration Test for Get method Get Customers by id
     *
     * @throws Exception
     */
    @Test
    public void getCustomersByIdTest() throws Exception {

        MvcResult result = mockMvc.perform(
                get("/api/public/getCustomerById/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Get method Get Customers by last name
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_CSR"})
    @Test
    public void getCustomersByNameTest() throws Exception {

        String  accessToken = jwtRequestHelper.tokenWithRole("ROLE_CSR");
        MvcResult result = mockMvc.perform(
                get("/api/getCustomersByName/{lastName}", "Doe")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].lastName").value("Doe"))
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Get method Get Customers by level
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_CSR"})
    @Test
    public void getCustomersByLevelTest() throws Exception {

        String  accessToken = jwtRequestHelper.tokenWithRole("ROLE_CSR");
        MvcResult result = mockMvc.perform(
                get("/api/getCustomersByLevel/{level}", "Gold")
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[*].level").value("Gold"))
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Patch method update Customer Level
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_CSR"})
    @Test
    public void updateCustomerLevelTest() throws Exception {

        Customer customer = new Customer(
                2,
                "Jane",
                "Dory",
                LocalDate.parse("1976-08-16"),
                "me@me.com",
                "5678567",
                "34 my road",
                Level.Gold);

        //convert to json String
        String jsonRequest = objectMapper.writeValueAsString(customer);

        MvcResult result = mockMvc.perform(
                patch("/api/updateCustomerLevel/{id}", 2)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Doe"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value("Gold"))
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Patch method update Customer Last Name
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_CSR"})
    @Test
    public void updateCustomerNameTest() throws Exception {

        Customer customer = new Customer(
                2,
                "Jane",
                "Dory",
                LocalDate.parse("1976-08-16"),
                "me@me.com",
                "5678567",
                "34 my road",
                Level.Gold);

        //convert to json String
        String jsonRequest = objectMapper.writeValueAsString(customer);

        MvcResult result = mockMvc.perform(
                patch("/api/updateCustomerName/{id}", 2)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dory"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.level").value("Silver"))
                .andReturn();

        Assert.assertEquals("application/json", result.getResponse().getContentType());
    }

    /**
     * Integration Test for Delete method Delete Customers
     *
     * @throws Exception
     */
    @WithMockUser(authorities={"ROLE_ADMIN"})
    @Test
    public void deleteCustomerTest() throws Exception {

        mockMvc.perform(
                delete("/api/deleteCustomer/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }

}