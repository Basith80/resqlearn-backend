package com.sih.disasterplatform.service.auth;

import com.sih.disasterplatform.entity.User;
import com.sih.disasterplatform.repository.UserRepository;
import com.sih.disasterplatform.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String login(String email, String password) throws Exception {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new Exception("User not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new Exception("Incorrect password");
        }

        return jwtUtil.generateToken(
                user.getEmail(),
                user.getRole().name()
        );
    }

    public User register(User user) throws Exception {

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already in use");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(true);

        return userRepository.save(user);
    }
}
