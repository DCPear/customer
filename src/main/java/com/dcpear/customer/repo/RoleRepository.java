package com.dcpear.customer.repo;

import com.dcpear.customer.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Role repository
 * Author Deepa Perera
 */
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(String name);
}
