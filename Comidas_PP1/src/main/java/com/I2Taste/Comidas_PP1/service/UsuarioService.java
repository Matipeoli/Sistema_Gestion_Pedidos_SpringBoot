package com.I2Taste.Comidas_PP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.repository.UsuarioRepository;

@Service
public class UsuarioService {
    
    @Autowired
    private UsuarioRepository UsuarioRepository;

    public Usuario findByEmail(String email){
        return UsuarioRepository.findByEmail(email);
    }

}
