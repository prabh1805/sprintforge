package com.sprintforge.identity.controller;

import com.sprintforge.identity.dto.UserAuthResponse;
import com.sprintforge.identity.entity.User;
import com.sprintforge.identity.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/users")
public class InternalUserController {
    private final UserService userService;
    @GetMapping("/email/{email}")
    public ResponseEntity<@NonNull UserAuthResponse> getUserByEmail(@PathVariable String email){
        User user = userService.findByEmail(email);

        UserAuthResponse response = new UserAuthResponse(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                user.getRole().name()
        );
        return ResponseEntity.ok(response);
    }
}
