package com.example.gymproject.models;

import com.example.gymproject.contratosModel.ContractInscribirModel;
import com.example.gymproject.entities.Clase;
import com.example.gymproject.entities.Inscripcion;
import com.example.gymproject.retrofit.RetrofitClient;
import com.example.gymproject.services.InscripcionService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InscribirModel implements ContractInscribirModel {

    private InscripcionService inscripcionService;

    public InscribirModel() {
        inscripcionService = RetrofitClient.getRetrofitInstance().create(InscripcionService.class);
    }

    @Override
    public void obtenerDetallesClase(Long idClase, OnFinishedListener listener) {
        inscripcionService.obtenerClasePorId(idClase).enqueue(new Callback<Clase>() {
            @Override
            public void onResponse(Call<Clase> call, Response<Clase> response) {
                if (response.isSuccessful()) {
                    Clase clase = response.body();
                    // Obtener las plazas disponibles
                    inscripcionService.obtenerDisponibilidad(idClase).enqueue(new Callback<Integer>() {
                        @Override
                        public void onResponse(Call<Integer> call, Response<Integer> response) {
                            if (response.isSuccessful()) {
                                listener.onDetallesClaseCargados(clase, response.body());
                            } else {
                                listener.onError("Error al obtener las plazas disponibles.");
                            }
                        }

                        @Override
                        public void onFailure(Call<Integer> call, Throwable t) {
                            listener.onError("Error de red al obtener la disponibilidad: " + t.getMessage());
                        }
                    });
                } else {
                    listener.onError("Error al obtener los detalles de la clase");
                }
            }

            @Override
            public void onFailure(Call<Clase> call, Throwable t) {
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void verificarInscripcion(Long idUsuario, Long idClase, OnFinishedListener listener) {
        inscripcionService.obtenerInscripcionesDeClase(idClase).enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                if (response.isSuccessful()) {
                    List<Inscripcion> inscripciones = response.body();
                    boolean estaInscrito = inscripciones.stream().anyMatch(i -> i.getUsuario().getIdUsuario().equals(idUsuario));
                    listener.onInscripcionVerificada(estaInscrito);
                } else {
                    listener.onError("Error al verificar la inscripción");
                }
            }

            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void inscribirUsuario(Long idUsuario, Long idClase, OnFinishedListener listener) {
        inscripcionService.inscribirUsuario(idUsuario, idClase).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    listener.onReservaRealizada();
                } else {
                    listener.onError("Error al realizar la inscripción");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }

    @Override
    public void cancelarInscripcion(Long idUsuario, Long idClase, OnFinishedListener listener) {
        inscripcionService.obtenerInscripcionesDeClase(idClase).enqueue(new Callback<List<Inscripcion>>() {
            @Override
            public void onResponse(Call<List<Inscripcion>> call, Response<List<Inscripcion>> response) {
                if (response.isSuccessful()) {
                    List<Inscripcion> inscripciones = response.body();
                    // Buscar la inscripción del usuario
                    Inscripcion inscripcion = inscripciones.stream()
                            .filter(i -> i.getUsuario().getIdUsuario().equals(idUsuario))
                            .findFirst()
                            .orElse(null);
                    if (inscripcion != null) {
                        inscripcionService.cancelarInscripcion(inscripcion.getIdInscripcion()).enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                if (response.isSuccessful()) {
                                    listener.onReservaCancelada();
                                } else {
                                    listener.onError("Error al cancelar la inscripción");
                                }
                            }

                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {
                                listener.onError("Error de red: " + t.getMessage());
                            }
                        });
                    } else {
                        listener.onError("Inscripción no encontrada");
                    }
                } else {
                    listener.onError("Error al obtener las inscripciones");
                }
            }

            @Override
            public void onFailure(Call<List<Inscripcion>> call, Throwable t) {
                listener.onError("Error de red: " + t.getMessage());
            }
        });
    }
}
