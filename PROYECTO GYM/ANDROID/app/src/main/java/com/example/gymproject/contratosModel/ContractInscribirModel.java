package com.example.gymproject.contratosModel;


import com.example.gymproject.entities.Clase;

public interface ContractInscribirModel {
    interface OnFinishedListener {
        void onDetallesClaseCargados(Clase clase, int plazasDisponibles);
        void onError(String mensaje);
        void onInscripcionVerificada(boolean estaInscrito);
        void onReservaRealizada();
        void onReservaCancelada();
    }

    void obtenerDetallesClase(Long idClase, OnFinishedListener listener);
    void verificarInscripcion(Long idUsuario, Long idClase, OnFinishedListener listener);
    void inscribirUsuario(Long idUsuario, Long idClase, OnFinishedListener listener);
    void cancelarInscripcion(Long idUsuario, Long idClase, OnFinishedListener listener);
}