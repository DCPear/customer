package com.dcpear.customer.domain;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Login DTO that holds login data.
 * @Author Deepa Perera
 */
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LoginDto {
    @NotNull
    private String username;

    @NotNull
    private String password;

    private String firstName;

    private String lastName;

    /**
     * Full constructor
     * @param username
     * @param password
     */
    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
