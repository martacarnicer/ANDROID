package com.example.gymproject.contratosPresenter;

public interface ContractClasesPresenter {
    void obtenerClases();
    void obtenerClasesPorCentro(Long idCentro);
    void obtenerClasesPorDiaYCentro(String dia, Long idCentro);
    void filtrarClases(String nombre, String diaSemana, Long idInstructor, Long idCentro);
}
