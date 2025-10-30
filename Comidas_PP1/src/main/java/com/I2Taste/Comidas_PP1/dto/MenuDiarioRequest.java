package com.I2Taste.Comidas_PP1.dto;

import java.time.LocalDate;

public class MenuDiarioRequest {
    private int menuId;
    private LocalDate fecha;
    
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
        this.menuId = menuId;
    }
    
}
