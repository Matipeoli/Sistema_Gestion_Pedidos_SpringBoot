package com.I2Taste.Comidas_PP1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.Feriado;


@Repository
public interface FeriadoRepository extends JpaRepository<Feriado, Long> {
    Feriado findByFecha(LocalDate fecha);
    List<Feriado> findByFechaBetween(LocalDate desde, LocalDate hasta);
};
