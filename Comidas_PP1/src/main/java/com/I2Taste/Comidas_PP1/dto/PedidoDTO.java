package com.I2Taste.Comidas_PP1.dto;

import lombok.Data;

import java.time.LocalDate;


@Data
public class PedidoDTO {
    private Long menuId;
    private String email; // aca no se si le ponemos jwt token
    private LocalDate fechaPedido;
    private String observaciones;
}
