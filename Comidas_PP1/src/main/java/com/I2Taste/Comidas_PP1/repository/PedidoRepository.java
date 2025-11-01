package com.I2Taste.Comidas_PP1.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Pedido;
import com.I2Taste.Comidas_PP1.entity.Usuario;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByUsuarioAndFecha(Usuario usuario, java.util.Date fecha);
};