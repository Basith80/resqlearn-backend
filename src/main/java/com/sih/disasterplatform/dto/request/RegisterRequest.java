package com.sih.disasterplatform.dto.request;

import com.sih.disasterplatform.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    // ✅ Used by TEACHER and STUDENT to join a school securely
    // Teachers enter teacherCode, Students enter studentCode
    private String schoolCode;

    // grade and subject REMOVED
}