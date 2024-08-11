package com.example.service.employee.service;


import com.example.service.employee.config.token.TokenService;
import com.example.service.employee.domain.User;
import com.example.service.employee.model.DataLoginDTO;
import com.example.service.employee.model.DataToken;
import com.example.service.employee.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService service;
    public DataToken login(DataLoginDTO dto) {
        var user = new UsernamePasswordAuthenticationToken(dto.login(),dto.password());
        var userAuth = manager.authenticate(user);
        return new DataToken(service.generatesToken((User)userAuth.getPrincipal()));
    }

    public DataLoginDTO register(DataLoginDTO dto) {
        var user = repository.save(new User(dto.login(),this.encoder(dto.password())));
        return new DataLoginDTO(user.getLogin(), user.getPassword());


    }

    private String encoder(String password) {
        return new BCryptPasswordEncoder().encode(password);
    }
}
