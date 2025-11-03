package com.I2Taste.Comidas_PP1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.I2Taste.Comidas_PP1.dto.MenuRequest;
import com.I2Taste.Comidas_PP1.entity.Menu;

import com.I2Taste.Comidas_PP1.service.MenuService;

@RestController
@RequestMapping("/menu/")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/todos")
    public List<Menu> findAll(){
        return menuService.findAll();
    }

    @GetMapping("/obtener/{id}")
    public Menu findById(@PathVariable long id){
        return menuService.findById(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable Long id){
        menuService.deleteById(id);
    }

    @PostMapping("/save")
    public void saveMenu(@RequestBody MenuRequest menuRequest){
        menuService.saveMenu(menuRequest);
    }

    @PutMapping("/edit")
    public void editMenu(@RequestBody MenuRequest menuRequest){
        menuService.editMenu(menuRequest);
    }
    
}