package com.example.gymproject.services;

import com.example.gymproject.entities.Clase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ClaseService {
    @GET("/clases")
    Call<List<Clase>> getClases(); // Obtener todas las clases

    @GET("/clases/filtrar")
    Call<List<Clase>> filtrarClases(@Query("nombre") String nombre,
                                    @Query("diaSemana") String diaSemana,
                                    @Query("idInstructor") Long idInstructor,
                                    @Query("idCentro") Long idCentro);

    @GET("/clases/centro/{idCentro}")
    Call<List<Clase>> obtenerClasesPorCentro(@Path("idCentro") Long idCentro);


    @GET("/clases/dia/{dia}/centro/{idCentro}")
    Call<List<Clase>> obtenerClasesPorDiaYCentro(@Path("dia") String dia, @Path("idCentro") Long idCentro);

}
