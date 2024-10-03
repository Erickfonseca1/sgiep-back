package com.sgiep.sgiep_back.controller;

import com.sgiep.sgiep_back.dto.LoginRequestDTO;
import com.sgiep.sgiep_back.dto.RegisterRequestDTO;
import com.sgiep.sgiep_back.dto.ResponseDTO;
import com.sgiep.sgiep_back.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "${cors.allowedOrigins}")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO request) {
        ResponseDTO response = authService.login(request.email(), request.password());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequestDTO request) {
        String result = authService.register(request.name(), request.email(), request.password(), request.role(), request.phone(), request.address());
        return ResponseEntity.ok(result);
    }
}
