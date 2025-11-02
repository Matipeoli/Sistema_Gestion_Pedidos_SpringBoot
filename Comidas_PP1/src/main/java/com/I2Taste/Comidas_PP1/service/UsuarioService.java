package com.I2Taste.Comidas_PP1.service;

import java.util.List;
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


    public List<Usuario> obtenerTodosLosUsuarios() {
    return UsuarioRepository.findAll();
    }

    public Usuario findByEmail(String email){
        return UsuarioRepository.findByEmail(email);
    }

    public boolean cambiarContrasenia(Long idUsuario, String contraseniaActual, String contraseniaNueva) {
        Optional<Usuario> optionalUsuario = UsuarioRepository.findById(idUsuario);
        if (!optionalUsuario.isPresent()) {
        return false; 
    }

    Usuario usuario = optionalUsuario.get();

        // verifica que la contraseña actual sea correcta
        if (!passwordEncoder.matches(contraseniaActual, usuario.getContrasenia())) {
            return false; 
        }

        // verifica que la contraseña sea diferente a la actual
        if (passwordEncoder.matches(contraseniaNueva, usuario.getContrasenia())) {
            return false; // La nueva contraseña es igual a la actual
        }

        // guarda nueva contraseña
        usuario.setContrasenia(passwordEncoder.encode(contraseniaNueva));
        UsuarioRepository.save(usuario);

        return true;
    }

}
