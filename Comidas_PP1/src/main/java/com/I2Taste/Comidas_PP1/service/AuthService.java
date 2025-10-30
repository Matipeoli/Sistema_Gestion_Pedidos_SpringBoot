package com.I2Taste.Comidas_PP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.I2Taste.Comidas_PP1.dto.LoginRequest;
import com.I2Taste.Comidas_PP1.dto.LoginResponse;
import com.I2Taste.Comidas_PP1.dto.RegisterRequest;
import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;
import com.I2Taste.Comidas_PP1.security.jwt.JwtService;

@Service
public class AuthService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    RolService rolService;

    public String register(@RequestBody RegisterRequest request) {
        if (usuarioRepository.findByEmail(request.getEmail()) != null) {
            return "El usuario ya existe";
        }

        Usuario nuevo = new Usuario();

        nuevo.setEmail(request.getEmail());
        nuevo.setNombre(request.getNombre());
        nuevo.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
        nuevo.setApellido(request.getApellido());
        nuevo.setRol(this.rolService.findByNombre(request.getRol() == 1 ? "Usuario" : "AromasLigth"));

        usuarioRepository.save(nuevo);

        return "Usuario registrado correctamente";
    }

    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
        }

        boolean passwordOk = passwordEncoder.matches(request.getContrasenia(), usuario.getContrasenia());

        if (!passwordOk) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Contraseña incorrecta");
        }

        String token = jwtService.generateToken(usuario.getEmail());

        LoginResponse response = new LoginResponse(token, usuario.getNombre(), usuario.getEmail(),
                usuario.getRol().getNombre(), usuario.getApellido());

        return ResponseEntity.ok(response);
    }
}
