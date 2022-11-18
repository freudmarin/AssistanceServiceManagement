package com.marindulja.mgmt_sys_demo_2.services;

import com.marindulja.mgmt_sys_demo_2.dto.LoginRequest;
import com.marindulja.mgmt_sys_demo_2.security.JwtProvider;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthService {
    private final AuthenticationManager authenticationManager;

    final
    UserService userService;
    private final JwtProvider jwtProvider;

    public AuthService(AuthenticationManager authenticationManager, UserService userService, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtProvider = jwtProvider;
    }

    public AuthenticationResponse login(LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String authenticationToken = jwtProvider.generateToken(authenticate);
        return new AuthenticationResponse(authenticationToken, loginRequest.getUsername());
    }
}
