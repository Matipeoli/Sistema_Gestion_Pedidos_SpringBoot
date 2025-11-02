package com.I2Taste.Comidas_PP1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.I2Taste.Comidas_PP1.dto.CantidadMenuResponse;
import com.I2Taste.Comidas_PP1.dto.PedidoDTO;
import com.I2Taste.Comidas_PP1.entity.Pedido;
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

    @GetMapping("/obtenerFecha")
    public List<Pedido> findByFecha(@RequestParam LocalDate fecha) {
        return pedidoService.findByFecha(fecha);
    }

    @GetMapping("/obtenerFechaRango/{desde}/{hasta}")
    public List<CantidadMenuResponse> obtenerPedidosDeLaSemana(@PathVariable LocalDate desde, @PathVariable LocalDate hasta) {
        return pedidoService.obtenerPedidosDeLaSemana(desde, hasta);
    }
}