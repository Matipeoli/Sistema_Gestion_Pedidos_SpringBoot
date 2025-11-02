package com.I2Taste.Comidas_PP1.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByUsuarioAndFechaPedido(Usuario usuario, LocalDate fecha);
    List<Pedido> findByFecha(LocalDate fecha);
    List<Pedido> findByFechaPedidoBetween(LocalDate desde, LocalDate hasta);
    List<Pedido> findByUsuarioAndFechaPedidoBetween(Usuario usuario, LocalDate desde, LocalDate hasta); 

};