package com.I2Taste.Comidas_PP1.controller;

import java.time.LocalDate;

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
        pedidoService.guardarPedido(pedido.getEmail(), pedido.getMenuId(), pedido.getFechaPedido());
    }

    @GetMapping("/menuUsuarioFecha/{email}/{fecha}")
    public Long getPedidosPorUsuarioYFecha(@PathVariable String email, @PathVariable LocalDate fecha){
        return pedidoService.getPedidosPorUsuarioYFecha(email, fecha);
    }
}