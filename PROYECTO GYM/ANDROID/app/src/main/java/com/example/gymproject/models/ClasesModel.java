package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractClasesModel;
import com.example.gymproject.entities.Clase;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.ClaseService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClasesModel implements ContractClasesModel {

    private ClaseService claseService;

    public ClasesModel() {
        this.claseService = RetrofitClient.getRetrofitInstance().create(ClaseService.class);
    }

    @Override
    public void getClases(ModelCallback<List<Clase>> callback) {
        claseService.getClases().enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener las clases");
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getClasesPorCentro(Long idCentro, ModelCallback<List<Clase>> callback) {
        claseService.obtenerClasesPorCentro(idCentro).enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener las clases");
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void getClasesPorDiaYCentro(String dia, Long idCentro, ModelCallback<List<Clase>> callback) {
        claseService.obtenerClasesPorDiaYCentro(dia, idCentro).enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener las clases");
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro, ModelCallback<List<Clase>> callback) {
        claseService.filtrarClases(nombre, diaSemana, idInstructor, idCentro).enqueue(new Callback<List<Clase>>() {
            @Override
            public void onResponse(Call<List<Clase>> call, Response<List<Clase>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al filtrar las clases");
                }
            }

            @Override
            public void onFailure(Call<List<Clase>> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
}
