package com.I2Taste.Comidas_PP1.controller;

import com.I2Taste.Comidas_PP1.dto.LoginRequest;
import com.I2Taste.Comidas_PP1.dto.RegisterRequest;
import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.security.jwt.JwtService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500") 
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

   
    @PostMapping("/register")
    public String register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()) != null) {
            return "El usuario ya existe";
        }

        Usuario nuevo = new Usuario();
        nuevo.setEmail(request.getEmail());
        nuevo.setNombre(request.getNombre());
        nuevo.setContrasenia(passwordEncoder.encode(request.getContrasenia()));

        usuarioRepository.save(nuevo);

        return "Usuario registrado correctamente";
    }

    
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());

        if (usuario == null) {
            return "404 Usuario no encontrado";
        }

        boolean passwordOk = passwordEncoder.matches(request.getContrasenia(), usuario.getContrasenia());

        if (!passwordOk) {
            return "403 Contraseña incorrecta";
        }

        
        String token = jwtService.generateToken(usuario.getEmail());

        return "400 Login exitoso";
    }
}
