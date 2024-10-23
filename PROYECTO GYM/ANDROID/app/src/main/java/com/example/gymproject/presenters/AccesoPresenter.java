package com.example.gymproject.presenters;

import com.example.gymproject.entities.Acceso;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.AccesoService;
import com.example.gymproject.views.AccesoView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccesoPresenter {
    private AccesoView view;
    private AccesoService accesoService;

    public AccesoPresenter(AccesoView view) {
        this.view = view;
        this.accesoService = RetrofitClient.getRetrofitInstance().create(AccesoService.class);
    }

    public void obtenerAccesos(Long idUsuario) {
        accesoService.obtenerAccesosPorUsuario(idUsuario).enqueue(new Callback<List<Acceso>>() {
            @Override
            public void onResponse(Call<List<Acceso>> call, Response<List<Acceso>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    view.mostrarAccesos(response.body());
                } else {
                    view.mostrarError("Error al obtener los accesos");
                }
            }

            @Override
            public void onFailure(Call<List<Acceso>> call, Throwable t) {
                view.mostrarError(t.getMessage());
            }
        });
    }
}