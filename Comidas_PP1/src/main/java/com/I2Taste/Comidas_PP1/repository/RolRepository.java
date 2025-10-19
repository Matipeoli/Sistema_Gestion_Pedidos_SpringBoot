package com.I2Taste.Comidas_PP1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre);
};