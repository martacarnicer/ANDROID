package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Clase;

import java.util.List;

public interface ContractClasesModel {
    void getClases(ModelCallback<List<Clase>> callback);
    void getClasesPorCentro(Long idCentro, ModelCallback<List<Clase>> callback);
    void getClasesPorDiaYCentro(String dia, Long idCentro, ModelCallback<List<Clase>> callback);
    void filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro, ModelCallback<List<Clase>> callback);

    interface ModelCallback<T> {
        void onSuccess(T result);
        void onError(String error);
    }
}

