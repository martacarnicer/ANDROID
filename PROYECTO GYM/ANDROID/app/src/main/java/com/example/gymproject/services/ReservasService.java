package com.example.gymproject.services;


import com.example.gymproject.entities.Reserva;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface ReservasService {

    @GET("usuarios/{idUsuario}/mis-reservas/proximas")
    Call<List<Reserva>> obtenerReservasProximas(@Path("idUsuario") Long idUsuario);

    @GET("usuarios/{idUsuario}/mis-reservas/pasadas")
    Call<List<Reserva>> obtenerReservasPasadas(@Path("idUsuario") Long idUsuario);
}
