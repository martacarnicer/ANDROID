package com.example.gymproject.presenters;

import com.example.gymproject.contratosModel.ContractClasesModel;
import com.example.gymproject.contratosPresenter.ContractClasesPresenter;
import com.example.gymproject.entities.Clase;
import com.example.gymproject.views.ClasesView;

import java.util.List;

public class ClasesPresenter implements ContractClasesPresenter {

    private ClasesView view;
    private ContractClasesModel model;

    public ClasesPresenter(ClasesView view, ContractClasesModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void obtenerClases() {
        view.setIsLoadingClases(true);
        model.getClases(new ContractClasesModel.ModelCallback<List<Clase>>() {
            @Override
            public void onSuccess(List<Clase> result) {
                view.setIsLoadingClases(false);
                view.mostrarClases(result);
            }

            @Override
            public void onError(String error) {
                view.setIsLoadingClases(false);
                view.mostrarError(error);
            }
        });
    }

    @Override
    public void obtenerClasesPorCentro(Long idCentro) {
        view.setIsLoadingClases(true);
        model.getClasesPorCentro(idCentro, new ContractClasesModel.ModelCallback<List<Clase>>() {
            @Override
            public void onSuccess(List<Clase> result) {
                view.setIsLoadingClases(false);
                view.mostrarClases(result);
            }

            @Override
            public void onError(String error) {
                view.setIsLoadingClases(false);
                view.mostrarError(error);
            }
        });
    }

    @Override
    public void obtenerClasesPorDiaYCentro(String dia, Long idCentro) {
        view.setIsLoadingClases(true);
        model.getClasesPorDiaYCentro(dia, idCentro, new ContractClasesModel.ModelCallback<List<Clase>>() {
            @Override
            public void onSuccess(List<Clase> result) {
                view.setIsLoadingClases(false);
                view.mostrarClases(result);
            }

            @Override
            public void onError(String error) {
                view.setIsLoadingClases(false);
                view.mostrarError(error);
            }
        });
    }

    @Override
    public void filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro) {
        view.setIsLoadingClases(true);
        model.filtrarClases(nombre, diaSemana, idInstructor, idCentro, new ContractClasesModel.ModelCallback<List<Clase>>() {
            @Override
            public void onSuccess(List<Clase> result) {
                view.setIsLoadingClases(false);
                view.mostrarClases(result);
            }

            @Override
            public void onError(String error) {
                view.setIsLoadingClases(false);
                view.mostrarError(error);
            }
        });
    }
}
