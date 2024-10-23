package com.example.gymproject.presenters;

import android.util.Log;

import com.example.gymproject.contratosModel.ContractReservasModel;
import com.example.gymproject.contratosPresenter.ContractReservasPresenter;
import com.example.gymproject.entities.Reserva;
import com.example.gymproject.views.MisReservasView;

import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MisReservasPresenter implements ContractReservasPresenter {
    private MisReservasView misReservasView;
    private ContractReservasModel misReservasModel;

    // Variables para almacenar las reservas cargadas
    private List<Reserva> proximasReservas;
    private List<Reserva> pasadasReservas;

    public MisReservasPresenter(MisReservasView view, ContractReservasModel model) {
        this.misReservasView = view;
        this.misReservasModel = model;
    }

    // Método general que carga las reservas (próximas o pasadas)
    private void manejarReservas(Long idUsuario, boolean sonProximas) {
        ContractReservasModel.OnReservasCargadasListener listener = new ContractReservasModel.OnReservasCargadasListener() {
            @Override
            public void onReservasCargadas(List<Reserva> reservas) {
                misReservasView.ocultarCargando();  // Oculta el indicador de carga
                if (reservas != null && !reservas.isEmpty()) {

                    // Ordenar las reservas por fecha
                    ordenarReservasPorFecha(reservas);

                    if (sonProximas) {
                        proximasReservas = reservas; // Guarda las reservas en cache
                        misReservasView.mostrarReservasProximas(reservas);
                    } else {
                        pasadasReservas = reservas; // Guarda las reservas pasadas en cache
                        misReservasView.mostrarReservasPasadas(reservas);
                    }
                } else {
                    if (sonProximas) {
                        misReservasView.mostrarMensajeNoReservasProximas(); // Mostrar mensaje de no hay reservas
                    } else {
                        misReservasView.mostrarMensajeNoReservasPasadas();
                    }
                }
            }

            @Override
            public void onError(String mensaje) {
                Log.e("MisReservasPresenter", "Error al cargar reservas: " + mensaje);
                misReservasView.ocultarCargando();
                misReservasView.mostrarError("Error al cargar reservas: " + mensaje);  // Mostrar mensaje específico
            }
        };

        if (sonProximas) {
            misReservasModel.obtenerReservasProximas(idUsuario, listener);
        } else {
            misReservasModel.obtenerReservasPasadas(idUsuario, listener);
        }
    }

    // Método para ordenar reservas por la fecha
    private void ordenarReservasPorFecha(List<Reserva> reservas) {
        Collections.sort(reservas, new Comparator<Reserva>() {
            @Override
            public int compare(Reserva r1, Reserva r2) {
                SimpleDateFormat formatoEntrada = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                try {
                    Date fecha1 = formatoEntrada.parse(r1.getFechaExactaClase());
                    Date fecha2 = formatoEntrada.parse(r2.getFechaExactaClase());
                    return fecha2.compareTo(fecha1);  // Orden descendente (más reciente primero)
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }


    public void cargarReservasProximas(Long idUsuario) {
        // Opción de usar cache si ya se cargaron antes
        if (proximasReservas != null && !proximasReservas.isEmpty()) {
            misReservasView.mostrarReservasProximas(proximasReservas); // Mostrar de inmediato si ya están en cache
        } else {
            manejarReservas(idUsuario, true);  // Cargar del servidor
        }
    }

    public void cargarReservasPasadas(Long idUsuario) {
        if (pasadasReservas != null && !pasadasReservas.isEmpty()) {
            misReservasView.mostrarReservasPasadas(pasadasReservas); // Mostrar de inmediato si ya están en cache
        } else {
            manejarReservas(idUsuario, false);  // Cargar del servidor
        }
    }

    @Override
    public void cargarReservas(Long idUsuario) {
        // Implementación futura si es necesario
    }
}
