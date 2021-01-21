package com.dcpear.customer.controller;

import com.dcpear.customer.domain.Customer;
import com.dcpear.customer.domain.Level;
import com.dcpear.customer.repo.CustomerRepository;
import com.dcpear.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.*;


@RestController
@RequestMapping("api")
@Tag(name = "customer", description = "The customer API")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    CustomerRepository customerRepository;


    @PostMapping("public/addCustomer")
    @Operation(summary = "Add a customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Customer> createContact(@Validated @RequestBody Customer customer) throws URISyntaxException {
        LOGGER.info("add a customer");
        Customer result = customerRepository.save(customer);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/getCustomers")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Get All customers")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public Collection<Customer> getCustomers() {
        LOGGER.info("get all customers");
        return (Collection<Customer>) customerRepository.findAll();
    }

    @GetMapping("public/getCustomerById/{id}")
    @Operation(summary = "Get customers by Id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    public Customer getCustomer(@PathVariable("id") Integer id) {
        LOGGER.info("GET /getCustomerById/{}", id);
        return customerRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id Not found"));
    }

    @GetMapping("/getCustomersByName/{lastName}")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Get customers by last name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    public Collection<Customer> getCustomersByLastName(@PathVariable("lastName") String lastName) {
        return customerRepository.findAllByLastName(lastName);
    }

    @GetMapping("/getCustomersByLevel/{level}")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Get customers by level")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    public Collection<Customer> getCustomersByLevel(@PathVariable("level") Level level) {
        return customerRepository.findAllByLevel(level);
    }

    @PatchMapping("/updateCustomerLevel/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Update customer level")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    public ResponseEntity<Customer> updateCustomerLevel(@PathVariable("id") Integer id,
                                                        @Validated @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        LOGGER.debug("PATCH /updateCustomerLevel/{}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
        customer.setLevel(customerDetails.getLevel());
        final Customer result = customerRepository.save(customer);
        return ResponseEntity.ok().body(result);
    }

    @PatchMapping("/updateCustomerName/{id}")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Update customer last name")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "Customer not found")})
    public ResponseEntity<Customer> updateCustomerName(@PathVariable("id") Integer id,
                                                       @Validated @RequestBody Customer customerDetails) throws ResourceNotFoundException {
        LOGGER.debug("PATCH /updateCustomerName/{}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));
        customer.setLastName(customerDetails.getLastName());
        final Customer result = customerRepository.save(customer);
        return ResponseEntity.ok().body(result);
    }


    @DeleteMapping("/deleteCustomer/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete customer")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "200", description = "OK")})
    public ResponseEntity<Customer> deleteCustomer(@PathVariable(value = "id") Integer id)
            throws ResourceNotFoundException {
        LOGGER.debug("DELETE /deleteCustomer/{}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found for this id :: " + id));

        customerRepository.delete(customer);
        return ResponseEntity.noContent().build();
    }

    /**
     * Exception handler if NoSuchElementException is thrown in this Controller
     *
     * @param ex exception
     * @return Error message String.
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public String return400(NoSuchElementException ex) {
        LOGGER.error("Unable to complete transaction", ex);
        return ex.getMessage();
    }
}
