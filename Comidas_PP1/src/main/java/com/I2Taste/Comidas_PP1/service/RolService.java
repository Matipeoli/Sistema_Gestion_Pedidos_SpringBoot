package com.I2Taste.Comidas_PP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.entity.Rol;
import com.I2Taste.Comidas_PP1.repository.RolRepository;

@Service
public class RolService {
    
    @Autowired
    private RolRepository rolRepository ;

    public Rol findByNombre(String nombre){
        return this.rolRepository.findByNombre(nombre);
    }

}