package com.example.gymproject.services;

import com.example.gymproject.entities.Clase;
import com.example.gymproject.entities.Inscripcion;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface InscripcionService {

    // Obtener detalles de una clase
    @GET("/clases/{idClase}")
    Call<Clase> obtenerClasePorId(@Path("idClase") Long idClase);

    // Obtener inscripciones de una clase
    @GET("/inscripciones/clase/{idClase}")
    Call<List<Inscripcion>> obtenerInscripcionesDeClase(@Path("idClase") Long idClase);

    // Inscribir a un usuario en una clase
    @POST("/inscripciones/inscribir")
    Call<Void> inscribirUsuario(@Query("idUsuario") Long idUsuario, @Query("idClase") Long idClase);

    // Cancelar la inscripción del usuario en una clase
    @DELETE("/inscripciones/cancelar/{idInscripcion}")
    Call<Void> cancelarInscripcion(@Path("idInscripcion") Long idInscripcion);

    // Obtener disponibilidad de plazas en una clase
    @GET("/clases/{idClase}/disponibilidad")
    Call<Integer> obtenerDisponibilidad(@Path("idClase") Long idClase);


}
