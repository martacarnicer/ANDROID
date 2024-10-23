package com.example.gymproject.contratosModel;

import com.example.gymproject.entities.Reserva;
import java.util.List;

public interface ContractReservasModel {
    void obtenerReservasProximas(Long idUsuario, OnReservasCargadasListener listener);
    void obtenerReservasPasadas(Long idUsuario, OnReservasCargadasListener listener);

    interface OnReservasCargadasListener {
        void onReservasCargadas(List<Reserva> reservas);
        void onError(String mensaje);
    }
}
