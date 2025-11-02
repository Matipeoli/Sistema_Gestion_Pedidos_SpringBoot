package com.I2Taste.Comidas_PP1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.I2Taste.Comidas_PP1.entity.Feriado;
import com.I2Taste.Comidas_PP1.repository.FeriadoRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class FeriadoService {
    
    @Autowired
    FeriadoRepository feriadoRepository;

    public void saveFeriado(LocalDate fecha){
        Feriado feriado = new Feriado();
        feriado.setFecha(fecha);
        feriadoRepository.save(feriado);
    }

    public List<LocalDate> findByFechaBetween(LocalDate desde, LocalDate hasta){

        List<Feriado> feriados = feriadoRepository.findByFechaBetween(desde,hasta);
        List<LocalDate> diasFeriados = new ArrayList<>();

        for(Feriado f : feriados){
            diasFeriados.add(f.getFecha());
        }

        return diasFeriados;
    }

    public void deleteByFecha(LocalDate fecha){
        Feriado feriado = feriadoRepository.findByFecha(fecha);
        feriadoRepository.delete(feriado);
    }
}
