package com.dcpear.customer.domain;

import lombok.Data;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;

/**
 * Entity of a Security Role
 * Author Deepa  Perera
 */

@Entity
@Data
@Table(name="security_role")
public class Role  implements GrantedAuthority {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role_name")
    private String roleName;

    @Column(name="description")
    private String description;

    @Override
    public String getAuthority() {
        return roleName;
    }
    public Long getId() {
        return id;
    }

}
