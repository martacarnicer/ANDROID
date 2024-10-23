package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Repositories.CentroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CentroServiceImpl implements CentroService {

    @Autowired
    private CentroRepository centroRepository;

    @Override
    public List<Centro> obtenerTodosLosCentros() {
        return centroRepository.findAll();
    }

    @Override
    public Centro obtenerCentroPorId(Long idCentro) {
        return centroRepository.findById(idCentro)
                .orElseThrow(() -> new IllegalArgumentException("Centro no encontrado"));
    }


}
