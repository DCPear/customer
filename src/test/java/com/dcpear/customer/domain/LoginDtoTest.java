package com.dcpear.customer.domain;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test LoginDto
 * @Author Deepa Perera
 */

public class LoginDtoTest {

    @Test
    public void testAll(){
        LoginDto loginDto = new LoginDto("Shady", "password");
        assertEquals("Shady",loginDto.getUsername());
        assertEquals("password",loginDto.getPassword());
    }

}