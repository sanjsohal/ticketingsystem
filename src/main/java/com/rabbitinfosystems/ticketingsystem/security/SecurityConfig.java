package com.rabbitinfosystems.ticketingsystem.security;

import com.rabbitinfosystems.ticketingsystem.filter.FirebaseAuthenticationFilter;
import com.rabbitinfosystems.ticketingsystem.filter.OriginCheckFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConfig {

    @Autowired
    private FirebaseAuthenticationFilter firebaseAuthenticationFilter;

    @Autowired
    private OriginCheckFilter originCheckFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers(HttpMethod.OPTIONS, "/api/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                    .requestMatchers("/api/avatars/upload").permitAll()
                .anyRequest().authenticated()
            )
            .addFilterBefore(originCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(firebaseAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return authentication -> authentication;
    }

}
