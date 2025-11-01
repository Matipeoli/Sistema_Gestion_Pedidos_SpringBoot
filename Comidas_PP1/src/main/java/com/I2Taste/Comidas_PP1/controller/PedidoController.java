package com.I2Taste.Comidas_PP1.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.I2Taste.Comidas_PP1.dto.PedidoDTO;
import com.I2Taste.Comidas_PP1.service.PedidoService;

@RestController
@RequestMapping("/pedido")
@CrossOrigin(origins = "*") 
public class PedidoController{

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/save")
    public void saveMenu(@RequestBody PedidoDTO pedido){
        pedidoService.guardarPedido(pedido.getUsuarioId(), pedido.getMenuId(), pedido.getFechaPedido());
    }
}