package com.I2Taste.Comidas_PP1.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;
    
    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "contrasenia", nullable = false)
    private String contrasenia;

    @Column(name = "rol", nullable = false)
    private int rol;

}