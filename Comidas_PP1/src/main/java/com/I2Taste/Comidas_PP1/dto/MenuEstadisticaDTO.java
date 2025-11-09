package com.I2Taste.Comidas_PP1.dto;

import java.util.List;

public record MenuEstadisticaDTO(
    int id,
    String titulo,
    String img,
    String tipo,
    int cantidad,
    List<String> observaciones
) {}
