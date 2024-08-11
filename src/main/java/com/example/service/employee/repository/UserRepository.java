package com.example.service.employee.repository;


import com.example.service.employee.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    UserDetails findByLoginAndActiveTrue(String username);


    boolean existsByLogin(String login);
}
