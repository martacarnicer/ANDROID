package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Centro;
import java.util.List;

public interface CentroService {
    List<Centro> obtenerTodosLosCentros();
    Centro obtenerCentroPorId(Long idCentro);
}
