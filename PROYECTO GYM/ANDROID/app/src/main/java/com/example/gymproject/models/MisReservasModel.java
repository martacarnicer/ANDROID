package com.example.gymproject.models;

import android.util.Log;

import com.example.gymproject.contratosModel.ContractReservasModel;
import com.example.gymproject.entities.Reserva;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.ReservasService;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisReservasModel implements ContractReservasModel {
    private ReservasService reservasService;

    public MisReservasModel() {
        reservasService = RetrofitClient.getRetrofitInstance().create(ReservasService.class);
    }

    @Override
    public void obtenerReservasProximas(Long idUsuario, OnReservasCargadasListener listener) {
        Log.d("MisReservasModel", "Iniciando carga de reservas para el usuario: " + idUsuario);
        long startTime = System.currentTimeMillis();

        Call<List<Reserva>> call = reservasService.obtenerReservasProximas(idUsuario);
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MisReservasModel", "Reservas cargadas en: " + (System.currentTimeMillis() - startTime) + " ms");

                    listener.onReservasCargadas(response.body());
                } else {
                    Log.e("MisReservasModel", "Error en la respuesta: " + response.code());
                    listener.onError("No se encontraron reservas próximas.");
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.e("MisReservasModel", "Error de red: " + t.getMessage());
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void obtenerReservasPasadas(Long idUsuario, OnReservasCargadasListener listener) {
        Log.d("MisReservasModel", "Iniciando carga de reservas pasadas para el usuario: " + idUsuario);
        long startTime = System.currentTimeMillis();

        Call<List<Reserva>> call = reservasService.obtenerReservasPasadas(idUsuario);
        call.enqueue(new Callback<List<Reserva>>() {
            @Override
            public void onResponse(Call<List<Reserva>> call, Response<List<Reserva>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("MisReservasModel", "Reservas pasadas cargadas en: " + (System.currentTimeMillis() - startTime) + " ms");

                    listener.onReservasCargadas(response.body());
                } else {
                    Log.e("MisReservasModel", "Error en la respuesta del servidor.");
                    listener.onError("No se encontraron reservas pasadas.");
                }
            }

            @Override
            public void onFailure(Call<List<Reserva>> call, Throwable t) {
                Log.e("MisReservasModel", "Error de red: " + t.getMessage());
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }


}
