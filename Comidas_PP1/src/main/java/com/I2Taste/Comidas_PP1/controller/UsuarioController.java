package com.I2Taste.Comidas_PP1.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.I2Taste.Comidas_PP1.entity.Usuario;
import com.I2Taste.Comidas_PP1.service.UsuarioService;
import com.I2Taste.Comidas_PP1.dto.CambioContraseniaRequest;

@RestController
@RequestMapping("/usuario")
@CrossOrigin(origins = "*") 
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/get/{email}")
    public Usuario findByEmail(@PathVariable String email){
        return usuarioService.findByEmail(email);
    }
    
    @PostMapping("/cambiar-contrasenia")
    public ResponseEntity<String> cambiarContrasenia(@RequestBody CambioContraseniaRequest request) {
        boolean exito = usuarioService.cambiarContrasenia(
                request.getIdUsuario(),
                request.getContraseniaActual(),
                request.getContraseniaNueva()
        );

        if (exito) {
            return ResponseEntity.ok("Contraseña actualizada correctamente.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: la contraseña actual es incorrecta o la nueva es igual a la actual.");
        }
    }
}