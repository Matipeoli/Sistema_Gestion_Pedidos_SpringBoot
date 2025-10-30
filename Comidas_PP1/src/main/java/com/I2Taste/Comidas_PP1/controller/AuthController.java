package com.I2Taste.Comidas_PP1.controller;

import com.I2Taste.Comidas_PP1.dto.LoginRequest;
import com.I2Taste.Comidas_PP1.dto.RegisterRequest;
import com.I2Taste.Comidas_PP1.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500") 
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
       return authService.register(request);
    }

    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
