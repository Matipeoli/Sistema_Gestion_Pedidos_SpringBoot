package com.I2Taste.Comidas_PP1.repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.I2Taste.Comidas_PP1.dto.CantidadMenuRequest;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
    Pedido findByUsuarioAndFechaPedido(Usuario usuario, LocalDate fecha);
    List<Pedido> findByFechaPedido(LocalDate fecha);
    List<Pedido> findByFechaPedidoBetween(LocalDate desde, LocalDate hasta);
    List<Pedido> findByUsuarioAndFechaPedidoBetween(Usuario usuario, LocalDate desde, LocalDate hasta); 
    void deleteByFechaPedidoAndUsuario(LocalDate fecha, Usuario usuario);

    @Query("SELECT p.menuDiario.id, COUNT(p) " +
       "FROM Pedido p " +
       "WHERE p.fechaPedido BETWEEN :desde AND :hasta " +
       "GROUP BY p.menuDiario.id " +
       "ORDER BY COUNT(p) DESC")
    List<CantidadMenuRequest> contarPedidosPorMenuDiarioEnRango(
            @Param("desde") LocalDate desde,
            @Param("hasta") LocalDate hasta);

};