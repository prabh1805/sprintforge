package com.sprintforge.auth.service;

import com.sprintforge.auth.dto.LoginResponse;
import com.sprintforge.auth.dto.UserAuthResponse;
import com.sprintforge.auth.security.JwtSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    private final JwtSecurity jwtSecurity;

    public LoginResponse login(final String email, final String password) {
        String url = "http://localhost:8081/internal/users/email/" + email;

        //Add internal service header
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Internal-Key", "sprintforge-secret");

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<UserAuthResponse> response =
                restTemplate.exchange(
                        url,
                        HttpMethod.GET,
                        request,
                        UserAuthResponse.class
                        );

        UserAuthResponse user = response.getBody();
        if(user == null) {
            throw new RuntimeException("User not found");
        }
        if(!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtSecurity.generateToken(
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getRole()
        );
    }
}
