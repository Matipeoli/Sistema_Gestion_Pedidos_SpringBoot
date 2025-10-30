package com.I2Taste.Comidas_PP1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.entity.Tipo;
import com.I2Taste.Comidas_PP1.repository.TipoRepository;

@Service
public class TipoService {
    
    @Autowired
    private TipoRepository tipoRepository;

    public List<Tipo> getAllTipos(){
        return tipoRepository.findAll();
    }

    public Tipo getTipo(long id){
        return tipoRepository.findById(id).orElse(null);
    }

}
