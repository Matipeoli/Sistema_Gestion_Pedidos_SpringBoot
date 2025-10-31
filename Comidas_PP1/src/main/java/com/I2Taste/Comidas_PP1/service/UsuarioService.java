package com.I2Taste.Comidas_PP1.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository UsuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario findByEmail(String email){
        return UsuarioRepository.findByEmail(email);
    }

     public boolean cambiarContrasenia(Long idUsuario, String contraseniaActual, String contraseniaNueva) {
        Optional<Usuario> optionalUsuario = UsuarioRepository.findById(idUsuario);
        if (!optionalUsuario.isPresent()) {
            return false; // Usuario no encontrado
        }

        Usuario usuario = optionalUsuario.get();

        // Verifica que la contraseña actual coincida
        if (!passwordEncoder.matches(contraseniaActual, usuario.getContrasenia())) {
            return false; // Contraseña actual incorrecta
        }

        // Verifica que la nueva contraseña sea distinta
        if (passwordEncoder.matches(contraseniaNueva, usuario.getContrasenia())) {
            return false; // La nueva contraseña es igual a la actual
        }

        // Guardar nueva contraseña encriptada
        usuario.setContrasenia(passwordEncoder.encode(contraseniaNueva));
        UsuarioRepository.save(usuario);

        return true;
    }

}
