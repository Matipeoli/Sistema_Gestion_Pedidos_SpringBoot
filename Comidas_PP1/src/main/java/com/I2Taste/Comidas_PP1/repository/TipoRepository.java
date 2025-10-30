package com.I2Taste.Comidas_PP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Long> {
    
};
