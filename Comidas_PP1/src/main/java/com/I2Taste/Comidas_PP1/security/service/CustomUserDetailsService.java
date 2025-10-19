package com.I2Taste.Comidas_PP1.security.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;


public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // aquí usas findByEmail porque tu repo define findByEmail(String)
        Usuario usuario = usuarioRepository.findByEmail(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuario no encontrado con email: " + username);
        }
        // Si la clase UserDetailsImpl no define el método estático build, construir un User básico
        return User.withUsername(usuario.getEmail())
                .password(usuario.getContrasenia())
                .authorities(new ArrayList<>())
                .accountExpired(false   )
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build();
    }
}

