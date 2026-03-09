package com.sih.disasterplatform.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String role;
    // ✅ Added so frontend can display full name in sidebar
    private String fullName;
}