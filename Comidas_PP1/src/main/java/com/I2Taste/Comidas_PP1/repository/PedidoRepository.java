package com.I2Taste.Comidas_PP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    //Metodos que se usan en el service
    
};