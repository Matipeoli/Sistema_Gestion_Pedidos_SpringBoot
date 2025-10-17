package com.I2Taste.Comidas_PP1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.service.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/get/{email}")
    public Usuario findByEmail(@PathVariable String email){
        return usuarioService.findByEmail(email);
    }
    
}
