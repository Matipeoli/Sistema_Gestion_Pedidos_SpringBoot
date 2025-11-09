package com.I2Taste.Comidas_PP1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import com.I2Taste.Comidas_PP1.dto.MenuEstadisticaDTO;
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
        pedidoService.guardarPedido(pedido.getEmail(), pedido.getMenuId(), pedido.getFechaPedido(),pedido.getObservaciones());
    }

    @GetMapping("/menuUsuarioFecha/{email}/{fecha}")
    public Long getPedidosPorUsuarioYFecha(@PathVariable String email, @PathVariable LocalDate fecha){
        return pedidoService.getPedidosPorUsuarioYFecha(email, fecha);
    }

    @GetMapping("/obtenerFecha")
    public List<Pedido> findByFecha(@RequestParam LocalDate fecha) {
        return pedidoService.findByFecha(fecha);
    }

    @GetMapping("/estadisticas/{desde}/{hasta}")
    public List<MenuEstadisticaDTO> obtenerEstadisticas(
            @PathVariable LocalDate desde,
            @PathVariable LocalDate hasta) {
        return pedidoService.obtenerEstadisticas(desde, hasta);
    }

    @GetMapping("/obtenerFechasPedido/{email}")
    public List<LocalDate> obtenerFechasPedidos(
            @PathVariable String email,
            @RequestParam LocalDate fechaInicio,
            @RequestParam LocalDate fechaFin
    ) {
        return pedidoService.obtenerFechasPedidosPorUsuarioYRango(email, fechaInicio, fechaFin);
    }
}