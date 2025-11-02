package com.I2Taste.Comidas_PP1.repository;


import java.time.LocalDate;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.I2Taste.Comidas_PP1.entity.MenuDiario;

@Repository
public interface MenuDiarioRepository extends JpaRepository<MenuDiario, Long> {
    List<MenuDiario> findAllByFecha(LocalDate date);
    void deleteAllByFecha(LocalDate date);
    MenuDiario findById(int id);
};
