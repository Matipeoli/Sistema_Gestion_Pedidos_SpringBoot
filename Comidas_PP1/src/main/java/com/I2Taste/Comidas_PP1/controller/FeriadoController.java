package com.I2Taste.Comidas_PP1.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.I2Taste.Comidas_PP1.service.FeriadoService;

@RestController
@RequestMapping("/feriado")
@CrossOrigin(origins = "*") 
public class FeriadoController {

    @Autowired
    private FeriadoService feriadoService;

    @PostMapping("/save/{fecha}")
    public void saveFeriado(@PathVariable LocalDate fecha){
        feriadoService.saveFeriado(fecha);
    }

    @DeleteMapping("/delete/{fecha}")
    public void deleteFeriado(@PathVariable LocalDate fecha){
        feriadoService.deleteByFecha(fecha);
    }

    @GetMapping("/buscarRango/{desde}/{hasta}")
    public List<LocalDate> findByFechaBetween(@PathVariable LocalDate desde,@PathVariable LocalDate hasta){
        return feriadoService.findByFechaBetween(desde, hasta);
    }
    
}