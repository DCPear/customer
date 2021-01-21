package com.dcpear.customer.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class CustomerTest {

    @Test
    public void testConstructorAndGetters() {

        Customer customer = new Customer(1, "Jane", "Doe", LocalDate.parse("1975-01-14"),
                "me@me.com", "123456", "1, Tasman Street", Level.Gold);
        assertEquals(customer.getFirstName(), "Jane");
    }

    @Test
    void testGetDisplayName() {
        Customer customer = new Customer(1, "Jane", "Doe", LocalDate.parse("1975-01-14"),
                "me@me.com", "123456", "1, Tasman Street", Level.Gold);
        assertEquals("Jane Doe", customer.getDisplayName());
    }

    @Test
    void testGetAge() {
        LocalDate dateOfBirth = LocalDate.parse("2013-01-02");
        Customer customer = new Customer(1, "Jane", "Doe", dateOfBirth,
                "me@me.com", "123456", "1, Tasman Street", Level.Gold);
        long age = customer.getAge();
        assertEquals(8, age);
    }
}

