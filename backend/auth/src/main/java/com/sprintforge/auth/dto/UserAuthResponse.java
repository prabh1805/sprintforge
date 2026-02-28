package com.sprintforge.auth.dto;

import lombok.Data;

@Data
public class UserAuthResponse {
    private Long id;
    private String email;
    private String password;
    private String role;
}
