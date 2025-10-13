package com.I2Taste.Comidas_PP1.entity;

import java.util.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    //id del menu
    
    @Column(name = "fecha_pedido", nullable = false)
    private Date fecha_pedido;

    @Column(name = "obeservaciones")
    private String observaciones;
   
   
}