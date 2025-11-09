package com.I2Taste.Comidas_PP1.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.MenuDiario;

@Repository
public interface MenuDiarioRepository extends JpaRepository<MenuDiario, Long> {
    List<MenuDiario> findAllByFecha(LocalDate date);

    void deleteAllByFecha(LocalDate date);

    MenuDiario findById(int id);

    @Query("SELECT DISTINCT md.fecha FROM MenuDiario md WHERE md.fecha BETWEEN :fechaInicio AND :fechaFin ORDER BY md.fecha ASC")
    List<LocalDate> findFechasConMenuEntreFechas(
        @Param("fechaInicio") LocalDate fechaInicio,
        @Param("fechaFin") LocalDate fechaFin);


};
