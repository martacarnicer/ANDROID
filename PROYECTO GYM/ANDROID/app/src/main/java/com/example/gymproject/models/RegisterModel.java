package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractRegisterModel;
import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Centro;
import com.example.gymproject.entities.Usuario;
import com.example.gymproject.services.AccesoService;
import com.example.gymproject.services.AuthService;
import com.example.gymproject.services.CentroService;
import com.example.gymproject.retrofit.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterModel implements ContractRegisterModel {

    private AuthService authService;
    private AccesoService accesoService;
    private CentroService centroService;

    public RegisterModel() {
        this.authService = RetrofitClient.getRetrofitInstance().create(AuthService.class);
        this.accesoService = RetrofitClient.getRetrofitInstance().create(AccesoService.class);
        this.centroService = RetrofitClient.getRetrofitInstance().create(CentroService.class);
    }


    @Override
    public void registerUser(Usuario usuario, ContractRegisterModel.RegisterCallback callback) {
        authService.registrarUsuario(usuario).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    if (response.code() == 400) {
                        callback.onError("El email ya está registrado.");
                    } else {
                        callback.onError("Error al registrar usuario.");
                    }
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });

    }

    @Override
    public void insertarAcceso(Long idUsuario, Long idCentro, ContractRegisterModel.RegisterCallback callback) {
        Acceso acceso = new Acceso();
        acceso.setUsuario(new Usuario(idUsuario));
        acceso.setCentro(new Centro(idCentro));
        acceso.setFechaInicioAcceso(getCurrentDate());
        acceso.setFechaFinAcceso(null); // O tu lógica

        accesoService.insertarAcceso(acceso).enqueue(new Callback<Acceso>() {
            @Override
            public void onResponse(Call<Acceso> call, Response<Acceso> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess("Acceso registrado correctamente.");
                } else {
                    callback.onError("Error al registrar acceso.");
                }
            }

            @Override
            public void onFailure(Call<Acceso> call, Throwable t) {
                callback.onError("Error de red al registrar acceso: " + t.getMessage());
            }
        });

    }

    @Override
    public void obtenerCentros(ContractRegisterModel.CentrosCallback callback) {
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


    private String getCurrentDate() {
        return new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
    }

}
