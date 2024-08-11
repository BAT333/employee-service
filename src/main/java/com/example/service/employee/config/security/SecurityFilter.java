package com.example.service.employee.config.security;

import com.example.service.employee.config.token.TokenService;
import com.example.service.employee.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenService tokenService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getToken(request);
        if(token !=null){
            var userObj = tokenService.getUser(token);
            var user = userRepository.findByLoginAndActiveTrue(userObj);
            var userAuth = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(userAuth);
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if(token != null){
            return token.replace("Bearer ","");
        }
        return null;
    }
}
