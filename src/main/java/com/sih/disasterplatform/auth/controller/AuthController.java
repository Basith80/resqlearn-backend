package com.sih.disasterplatform.auth.controller;

import com.sih.disasterplatform.auth.dto.LoginRequest;
import com.sih.disasterplatform.auth.dto.LoginResponse;
import com.sih.disasterplatform.user.User;
import com.sih.disasterplatform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return new LoginResponse("Login successful");
    }
}
