package com.marindulja.mgmt_sys_demo_2.controllers;


import com.marindulja.mgmt_sys_demo_2.dto.LoginRequest;
import com.marindulja.mgmt_sys_demo_2.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginDto) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(loginDto));
    }
}
