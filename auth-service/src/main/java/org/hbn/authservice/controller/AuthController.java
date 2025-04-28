package org.hbn.authservice.controller;


import io.swagger.v3.oas.annotations.Operation;
import org.hbn.authservice.dto.LoginRequestDTO;
import org.hbn.authservice.dto.LoginResponseDTO;
import org.hbn.authservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Operation(summary = "import io.swagger.v3.oas.annotations.Operation")
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO){
        Optional<String> tokenOptional = authService.authenticate(loginRequestDTO);

        if(tokenOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        String token = tokenOptional.get();

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

}
