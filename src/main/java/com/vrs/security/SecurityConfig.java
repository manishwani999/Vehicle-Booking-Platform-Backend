package com.vrs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        	.cors().and()
            .csrf(csrf -> csrf.disable())
				/*
				 * .sessionManagement(sm ->
				 * sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS) )
				 */
            .authorizeHttpRequests(auth -> auth

            	    // ---------- AUTH ----------
            	    .requestMatchers(
            	        "/api/users/login",
            	        "/api/users/register",
            	        "/api/users/register-admin"
            	    ).permitAll()

            	    // ---------- BOOKINGS ----------
            	    .requestMatchers(
            	        "/api/bookings/**"
            	    ).hasAnyRole("USER", "ADMIN")

            	    // ---------- VEHICLES ----------
            	    .requestMatchers("/api/vehicles/admin/**").hasRole("ADMIN")
            	    .requestMatchers("/api/vehicles/**").permitAll()
            	    .requestMatchers("/api/dashboard/**").hasRole("ADMIN")

            	    // ---------- EVERYTHING ELSE ----------
            	    .anyRequest().authenticated()
            	);


        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
