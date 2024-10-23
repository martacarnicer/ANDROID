package com.example.gymproject.contratosPresenter;

public interface ContractInscribirPresenter {
    void cargarDetallesClase(Long idClase);
    void verificarInscripcion(Long idUsuario, Long idClase);
    void manejarReserva(Long idUsuario, Long idClase);
}