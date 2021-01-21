package com.dcpear.customer.security;

import com.dcpear.customer.repo.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Centre point for setting up project security needs.
 * override web security configuration adapter's configure method in order to change the default behavior of the HTTP
 * security object, so when we invoke the HTTP security object's authorize request methods, it will tell Spring
 * security how to handle different APIs.
 * authentication manager requires an object that implements the user details service interface. It then invokes
 * the object's load by username method, which we have overridden here
 *
 * @Author Deepa Perera
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfiguration  extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImp userDetailsServiceImp;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Allowed entry points
        http.authorizeRequests()
                .antMatchers("/api/public/**").permitAll()
                .antMatchers("/users/signin").permitAll()
                // Disallow everything else..
                .anyRequest().authenticated();

        // Disable CSRF (cross site request forgery)
        http.csrf().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(new JwtTokenFilter(userDetailsServiceImp), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
