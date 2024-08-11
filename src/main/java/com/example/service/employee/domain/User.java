package com.example.service.employee.domain;

import com.example.service.employee.model.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "logins")
    private String login;
    @Column(name = "passwords")
    private String passwords;
    @Column(name = "roles")
    @Enumerated(EnumType.STRING)
    @ColumnDefault("USER")
    private UserRole role;
    @Column(name = "actives",nullable = false,columnDefinition = "boolean default true")
    @ColumnDefault("true")
    private Boolean active;

    public User(String login, String encoder, UserRole userRole) {
        this.login =login;
        this.passwords = encoder;
        this.role = userRole;
        this.active = true;
    }

    public User(String login, String encoder) {
        this.login =login;
        this.passwords = encoder;
        this.active = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.role == UserRole.ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"),new SimpleGrantedAuthority("ROLE_SUB_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        }else if(this.role == UserRole.SUB_ADMIN){
            return List.of(new SimpleGrantedAuthority("ROLE_SUB_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        }else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"),new SimpleGrantedAuthority("ROLE_SUB"));
        }
    }

    @Override
    public String getPassword() {
        return this.passwords;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
       return true;
    }

    public void delete() {
        System.out.println("deleteUser");
    }
}
