package org.hbn.authservice.service;

import org.hbn.authservice.dto.LoginRequestDTO;
import org.hbn.authservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {


    @Autowired
    private UserService userService;

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<User> user = userService.findByEmail(loginRequestDTO.getEmail());
        return user.isPresent() ? Optional.of(user.get().getEmail()) : Optional.empty();
    }
}
