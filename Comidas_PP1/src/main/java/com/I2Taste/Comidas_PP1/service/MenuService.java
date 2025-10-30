package com.I2Taste.Comidas_PP1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.dto.MenuRequest;
import com.I2Taste.Comidas_PP1.entity.Menu;
import com.I2Taste.Comidas_PP1.repository.MenuRepository;

@Service
public class MenuService {
    
    @Autowired
    private MenuRepository menuRepository ;

    @Autowired
    private TipoService tipoService;

    public List<Menu> findAll(){
        return menuRepository.findAll();
    }

    public void deleteById(long id){
        menuRepository.deleteById(id);
    }

    public Menu findById(long id) {
        return menuRepository.findById(id).orElse(null);
    }
    
    public void saveMenu(MenuRequest menuRequest){
        
        Menu menu = new Menu();
        menu.setImg(menuRequest.getImg());
        menu.setTitulo(menuRequest.getTitulo());
        menu.setDescripcion(menuRequest.getDescripcion());
        menu.setTipo(tipoService.getTipo(menuRequest.getId_tipo()));
        
        menuRepository.save(menu);
    }

    public void editMenu(MenuRequest menuRequest){
        
        Menu menu = new Menu();
        menu.setId(menuRequest.getId());
        menu.setImg(menuRequest.getImg());
        menu.setTitulo(menuRequest.getTitulo());
        menu.setDescripcion(menuRequest.getDescripcion());
        menu.setTipo(tipoService.getTipo(menuRequest.getId_tipo()));

        menuRepository.save(menu);
    }

    
}
    

