package com.I2Taste.Comidas_PP1.dto;

import com.I2Taste.Comidas_PP1.entity.Menu;

import lombok.Data;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Data
public class CantidadMenuResponse {
    private Menu menu;
    private Long cantidad;
}