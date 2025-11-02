package com.I2Taste.Comidas_PP1.dto;

import lombok.Data;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Data
public class CantidadMenuRequest {
    private Long idMenuDiario;
    private Long cantidad;
}
