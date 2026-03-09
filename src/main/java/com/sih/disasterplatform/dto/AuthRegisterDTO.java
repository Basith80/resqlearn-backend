package com.sih.disasterplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Registration request body.
 * - SCHOOL / INDIVIDUAL: only name, email, password, role
 * - TEACHER: name, email, password, role, secretCode (matches school.teacherCode)
 * - STUDENT: name, email, password, role, secretCode (matches school.studentCode)
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRegisterDTO {
    private String name;
    private String email;
    private String password;
    private String role;
    private String secretCode; // used by TEACHER and STUDENT to find their school
}