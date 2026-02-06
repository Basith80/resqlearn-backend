package com.sih.disasterplatform.admin.controller;

import com.sih.disasterplatform.user.User;
import com.sih.disasterplatform.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserRepository userRepository;

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }
}
