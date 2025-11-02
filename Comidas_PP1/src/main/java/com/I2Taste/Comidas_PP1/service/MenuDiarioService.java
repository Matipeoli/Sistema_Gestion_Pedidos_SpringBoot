package com.I2Taste.Comidas_PP1.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.repository.MenuDiarioRepository;
import com.I2Taste.Comidas_PP1.dto.MenuDiarioRequest;
import com.I2Taste.Comidas_PP1.entity.MenuDiario;

import jakarta.transaction.Transactional;

@Service
public class MenuDiarioService {
    
    @Autowired
    private MenuDiarioRepository menuDiarioRepository;

    @Autowired 
    private MenuService menuService;

    public List<MenuDiario> findByFecha(LocalDate date){
        return menuDiarioRepository.findAllByFecha(date);
    }

    public void save(MenuDiarioRequest menuDiario){
        
        MenuDiario menuDiarioSave = new MenuDiario();

        menuDiarioSave.setFecha(menuDiario.getFecha());
        menuDiarioSave.setMenu(menuService.findById(menuDiario.getMenuId()));

        menuDiarioRepository.save(menuDiarioSave);
    }

    @Transactional
    public void saveAll(List<MenuDiarioRequest> menuDiario){
        

        List<MenuDiario> guardar = new ArrayList<>();

        for(MenuDiarioRequest m : menuDiario){
            MenuDiario menu = new MenuDiario();
            menu.setFecha(m.getFecha());
            menu.setMenu(menuService.findById(m.getMenuId()));

            guardar.add(menu);

        }
        
        //buscar los menu que tengan pedido ese dia y eliminarlos
        this.deleteAllByFecha(menuDiario.get(0).getFecha());

        menuDiarioRepository.saveAll(guardar);
    }

    public void deleteAllByFecha(LocalDate fecha){
        menuDiarioRepository.deleteAllByFecha(fecha);
    }
}
