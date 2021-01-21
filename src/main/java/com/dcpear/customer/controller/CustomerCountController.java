package com.dcpear.customer.controller;

import com.dcpear.customer.domain.Level;
import com.dcpear.customer.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("count")
@Tag(name = "customer count", description = "The customer count API")
public class CustomerCountController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCountController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public long getCount() {
        return customerService.getCustomerCount();
    }

    @GetMapping("/getNumberOf/{level}")
    @PreAuthorize("hasAnyRole('ROLE_CSR','ROLE_ADMIN')")
    @Operation(summary = "Get customer count by level")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "OK")})
    public long getNumbersByLevel(@PathVariable("level") Level level){
        LOGGER.info("GET /{}/number", level);
        return customerService.getLevelCount(level) ;
    }
}
