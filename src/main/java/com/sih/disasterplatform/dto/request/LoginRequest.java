package com.sih.disasterplatform.dto.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    private String email;
    private String password;
}
