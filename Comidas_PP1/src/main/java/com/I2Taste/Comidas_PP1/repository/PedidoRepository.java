package com.I2Taste.Comidas_PP1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
      List<Pedido> findByUsuario(Usuario usuario);

      Pedido findByUsuarioAndFechaPedido(Usuario usuario, LocalDate fecha);

      List<Pedido> findByFechaPedido(LocalDate fecha);

      List<Pedido> findByFechaPedidoBetween(LocalDate desde, LocalDate hasta);

      List<Pedido> findByUsuarioAndFechaPedidoBetween(Usuario usuario, LocalDate desde, LocalDate hasta);

      void deleteByFechaPedidoAndUsuario(LocalDate fecha, Usuario usuario);

      @Query("""
                  SELECT p.menuDiario.menu.id AS id,
                         p.menuDiario.menu.titulo AS titulo,
                         p.menuDiario.menu.img AS img,
                         p.menuDiario.menu.tipo.nombre AS tipo,
                         COUNT(p) AS cantidad
                  FROM Pedido p
                  WHERE p.fechaPedido BETWEEN :desde AND :hasta
                  GROUP BY p.menuDiario.menu.id, p.menuDiario.menu.titulo, p.menuDiario.menu.img, p.menuDiario.menu.tipo.nombre
                  ORDER BY COUNT(p) DESC
                  """)
      List<Object[]> obtenerResumenMenus(@Param("desde") LocalDate desde,
                  @Param("hasta") LocalDate hasta);

      @Query("""
       SELECT p.menuDiario.menu.id, p.observaciones
       FROM Pedido p
       WHERE p.fechaPedido BETWEEN :desde AND :hasta
       """)
      List<Object[]> obtenerObservacionesPorMenu(@Param("desde") LocalDate desde,
                                                @Param("hasta") LocalDate hasta);

      @Query("SELECT p.fechaPedido FROM Pedido p WHERE p.usuario.email = :email AND p.fechaPedido BETWEEN :fechaInicio AND :fechaFin ORDER BY p.fechaPedido ASC")
      List<LocalDate> findFechasByEmailAndRangoFechas(
                  @Param("email") String email,
                  @Param("fechaInicio") LocalDate fechaInicio,
                  @Param("fechaFin") LocalDate fechaFin);

};