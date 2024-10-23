package com.example.gymproject.views;

import com.example.gymproject.entities.Clase;

import java.util.List;

public interface ClasesView {
    void mostrarClases(List<Clase> clases);
    void mostrarError(String mensaje);
    void setIsLoadingClases(boolean isLoading);  // Para controlar el estado de carga

}
