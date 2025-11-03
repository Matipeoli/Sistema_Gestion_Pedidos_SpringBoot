package com.I2Taste.Comidas_PP1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.I2Taste.Comidas_PP1.dto.MenuDiarioRequest;
import com.I2Taste.Comidas_PP1.service.MenuDiarioService;
import com.I2Taste.Comidas_PP1.entity.MenuDiario;


@RestController
@RequestMapping("/menuDiario/")
@CrossOrigin(origins = "*") 
public class MenuDiarioController {
    
    @Autowired
    private MenuDiarioService menuDiarioService;

    @GetMapping("/todos/{date}")
    public List<MenuDiario> findAllByDate(@PathVariable LocalDate date){
        return menuDiarioService.findByFecha(date);
    }

    @PostMapping("/agregar")
    public void saveMenuDiario(@RequestBody MenuDiarioRequest menuDiario){
        menuDiarioService.save(menuDiario);
    }

    @PostMapping("/agregarVarios")
    public void saveMenuDiario(@RequestBody List<MenuDiarioRequest> menuDiario){
        menuDiarioService.saveAll(menuDiario);
    }
}
