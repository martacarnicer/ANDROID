package com.example.gymproject.services;

import com.example.gymproject.entities.Centro;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CentroService {

    // Obtener todos los centros
    @GET("/centros") // Cambia esto según la ruta de tu API
    Call<List<Centro>> obtenerCentros();
}
