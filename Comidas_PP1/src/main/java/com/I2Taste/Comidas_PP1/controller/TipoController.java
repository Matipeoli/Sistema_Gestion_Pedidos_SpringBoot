package com.I2Taste.Comidas_PP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.I2Taste.Comidas_PP1.entity.Tipo;
import com.I2Taste.Comidas_PP1.service.TipoService;

@RestController
@RequestMapping("/tipo")
public class TipoController {

    @Autowired
    private TipoService tipoService;

    @GetMapping("/todos")
    public List<Tipo> getAllTipos(){
        return tipoService.getAllTipos();
    }

}
    