package com.example.service.employee.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private SecurityFilter filter;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity security){
        try {
            return security.csrf(AbstractHttpConfigurer::disable)
                    .sessionManagement(https-> https.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authorizeHttpRequests(authoriza->
                            authoriza.requestMatchers(HttpMethod.POST,"auth").permitAll().
                                    requestMatchers(HttpMethod.POST,"auth/register").permitAll().
                                    requestMatchers(HttpMethod.POST,"/employee/register").permitAll().
                                    requestMatchers(HttpMethod.GET,"/employee/**").permitAll().
                                    requestMatchers(HttpMethod.GET,"/employee/{id}").permitAll().
                                    requestMatchers(HttpMethod.PUT,"/employee/update/{id}").permitAll().
                                    requestMatchers(HttpMethod.DELETE,"/employee/delete/{id}").permitAll().
                                    requestMatchers("/v3/api-docs/**", "/swagger-ui.html", "/swagger-ui/**").permitAll().
                                    anyRequest().authenticated())
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Bean
    public AuthenticationManager manager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    }
}
