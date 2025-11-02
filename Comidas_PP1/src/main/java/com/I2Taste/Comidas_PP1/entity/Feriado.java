package com.I2Taste.Comidas_PP1.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feriado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "dia", nullable = false)
    private LocalDate fecha;

}
