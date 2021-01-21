package com.dcpear.customer.repo;

import com.dcpear.customer.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author Deepa Perera.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByUsernameTest() {
        Optional<User> user = userRepository.findByUsername("admin");
        assertTrue(user.isPresent());

        user = userRepository.findByUsername("testUser");
        assertFalse(user.isPresent());
    }
}