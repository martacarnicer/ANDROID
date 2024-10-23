package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractPerfilModel;
import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.AccesoService;
import com.example.gymproject.services.CentroService;
import com.example.gymproject.services.PerfilService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilModel implements ContractPerfilModel {

    private CentroService centroService;
    private PerfilService perfilService;
    private AccesoService accesoService;

    public PerfilModel() {
        this.centroService = RetrofitClient.getRetrofitInstance().create(CentroService.class);
        this.perfilService = RetrofitClient.getRetrofitInstance().create(PerfilService.class);
        this.accesoService = RetrofitClient.getRetrofitInstance().create(AccesoService.class);
    }

    @Override
    public void obtenerCentros(CentrosCallback callback) {
        centroService.obtenerCentros().enqueue(new Callback<List<Centro>>() {
            @Override
            public void onResponse(Call<List<Centro>> call, Response<List<Centro>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onCentrosLoaded(response.body());
                } else {
                    callback.onError("Error al obtener los centros.");
                }
            }

            @Override
            public void onFailure(Call<List<Centro>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void obtenerDatosUsuario(Long idUsuario, PerfilCallback callback) {
        perfilService.obtenerUsuario(idUsuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful()) {
                    accesoService.obtenerAccesosPorUsuario(idUsuario).enqueue(new Callback<List<Acceso>>() {
                        @Override
                        public void onResponse(Call<List<Acceso>> call, Response<List<Acceso>> response) {
                            if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                                callback.onSuccess(response.body().get(0).getUsuario(), response.body().get(0));  // Pasamos Usuario y Acceso
                            } else {
                                callback.onSuccess(response.body().get(0).getUsuario(), null);  // Sin acceso
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Acceso>> call, Throwable t) {
                            callback.onError("Error al obtener el acceso");
                        }
                    });
                } else {
                    callback.onError("Error al obtener datos del usuario");
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }

    @Override
    public void actualizarUsuario(Usuario usuario, UpdateCallback callback) {
        perfilService.actualizarUsuario(usuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onUpdateSuccess();
                } else {
                    callback.onUpdateError("Error al actualizar datos");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onUpdateError(t.getMessage());
            }
        });
    }

    @Override
    public void desactivarUsuario(Long idUsuario, UpdateCallback callback) {
        perfilService.desactivarUsuario(idUsuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onUpdateSuccess();
                } else {
                    callback.onUpdateError("Error al desactivar usuario");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onUpdateError(t.getMessage());
            }
        });
    }

    @Override
    public void actualizarCentroAcceso(Long idUsuario, Long idCentro, UpdateCallback callback) {
        accesoService.actualizarCentroUsuario(idUsuario, idCentro).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onUpdateSuccess();
                } else {
                    callback.onUpdateError("Error al actualizar el centro.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onUpdateError(t.getMessage());
            }
        });
    }

    @Override
    public void otorgarAccesoPremium(Long idUsuario, UpdateCallback callback) {
        accesoService.otorgarAccesoPremium(idUsuario).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    callback.onUpdateSuccess();
                } else {
                    callback.onUpdateError("Error al otorgar acceso premium.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onUpdateError(t.getMessage());
            }
        });
    }
}
