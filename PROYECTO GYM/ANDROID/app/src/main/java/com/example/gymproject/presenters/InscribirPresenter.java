package com.example.gymproject.presenters;

import com.example.gymproject.contratosModel.ContractInscribirModel;
import com.example.gymproject.contratosPresenter.ContractInscribirPresenter;
import com.example.gymproject.entities.Clase;
import com.example.gymproject.views.InscribirView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class InscribirPresenter implements ContractInscribirPresenter, ContractInscribirModel.OnFinishedListener {

    private InscribirView inscribirView;
    private ContractInscribirModel inscribirModel;
    private int plazasDisponibles;  // Variable de instancia para almacenar plazas disponibles

    public InscribirPresenter(InscribirView view, ContractInscribirModel model) {
        this.inscribirView = view;
        this.inscribirModel = model;
    }

    @Override
    public void cargarDetallesClase(Long idClase) {
        if (inscribirView != null) {
            inscribirView.mostrarCargando();
        }
        inscribirModel.obtenerDetallesClase(idClase, this);
    }

    @Override
    public void verificarInscripcion(Long idUsuario, Long idClase) {
        inscribirModel.verificarInscripcion(idUsuario, idClase, this);
    }

    @Override
    public void manejarReserva(Long idUsuario, Long idClase) {
        if (inscribirView != null) {
            inscribirView.mostrarCargando();
        }

        inscribirModel.obtenerDetallesClase(idClase, new ContractInscribirModel.OnFinishedListener() {
            @Override
            public void onDetallesClaseCargados(Clase clase, int plazas) {
                plazasDisponibles = plazas;

                // Verificar si el usuario ya está inscrito o no
                inscribirModel.verificarInscripcion(idUsuario, idClase, new ContractInscribirModel.OnFinishedListener() {
                    @Override
                    public void onInscripcionVerificada(boolean estaInscrito) {
                        if (estaInscrito) {
                            inscribirModel.cancelarInscripcion(idUsuario, idClase, InscribirPresenter.this);
                        } else {
                            inscribirModel.inscribirUsuario(idUsuario, idClase, InscribirPresenter.this);
                        }
                    }

                    @Override
                    public void onReservaRealizada() {
                        if (inscribirView != null) {
                            inscribirView.ocultarCargando();
                            inscribirView.actualizarBotonReserva(true);

                            // Restar una plaza disponible después de una reserva exitosa
                            plazasDisponibles--;
                            inscribirView.actualizarPlazasDisponibles(plazasDisponibles);

                            inscribirView.mostrarMensaje("Reserva realizada");
                        }
                    }

                    @Override
                    public void onReservaCancelada() {
                        if (inscribirView != null) {
                            inscribirView.ocultarCargando();
                            inscribirView.actualizarBotonReserva(false);

                            // Sumar una plaza disponible después de cancelar la reserva
                            plazasDisponibles++;
                            inscribirView.actualizarPlazasDisponibles(plazasDisponibles);

                            inscribirView.mostrarMensaje("Reserva cancelada");
                        }
                    }

                    @Override
                    public void onError(String mensaje) {
                        if (inscribirView != null) {
                            inscribirView.ocultarCargando();
                            inscribirView.mostrarError(mensaje);
                        }
                    }

                    @Override
                    public void onDetallesClaseCargados(Clase clase, int plazasDisponibles) {}
                });
            }

            @Override
            public void onError(String mensaje) {
                if (inscribirView != null) {
                    inscribirView.ocultarCargando();
                    inscribirView.mostrarError(mensaje);
                }
            }

            @Override
            public void onInscripcionVerificada(boolean estaInscrito) {}

            @Override
            public void onReservaRealizada() {}

            @Override
            public void onReservaCancelada() {}
        });
    }

    @Override
    public void onDetallesClaseCargados(Clase clase, int plazasDisponibles) {
        if (inscribirView != null) {
            inscribirView.ocultarCargando();

            this.plazasDisponibles = plazasDisponibles;

            String horaClase = clase.getHoraInicio() + " - " + clase.getHoraFin();

            Calendar fechaActual = Calendar.getInstance();
            int diaSemanaActual = fechaActual.get(Calendar.DAY_OF_WEEK);
            int diaClase = convertirDiaSemanaAInt(clase.getDiaSemana());
            int diasHastaClase = calcularDiasHastaClase(diaSemanaActual, diaClase);
            fechaActual.add(Calendar.DAY_OF_YEAR, diasHastaClase);

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
            String fechaClaseStr = dateFormat.format(fechaActual.getTime());

            String emailInstructor = clase.getInstructor() != null ? clase.getInstructor().getEmail() : "Email no disponible";
            String photoUrl = clase.getInstructor().getPhotoUrl();


            inscribirView.mostrarDetallesClase(
                    clase.getNombreClase(),
                    horaClase,
                    emailInstructor,
                    clase.getDescripcion(),
                    plazasDisponibles,
                    fechaClaseStr,
                    photoUrl        // Pasamos la URL de la foto aquí

            );

            if (plazasDisponibles == 0) {
                inscribirView.actualizarBotonReserva(false);
            }
        }
    }

    @Override
    public void onError(String mensaje) {
        if (inscribirView != null) {
            inscribirView.ocultarCargando();
            inscribirView.mostrarError(mensaje);
        }
    }

    @Override
    public void onInscripcionVerificada(boolean estaInscrito) {
        if (inscribirView != null) {
            inscribirView.actualizarBotonReserva(estaInscrito);
        }
    }

    @Override
    public void onReservaRealizada() {
        if (inscribirView != null) {
            inscribirView.ocultarCargando();
            inscribirView.actualizarBotonReserva(true);

            // Restar una plaza disponible después de una reserva exitosa
            if (plazasDisponibles > 0) {
                plazasDisponibles--;  // Asegurarse de que las plazas no sean negativas
            }
            inscribirView.actualizarPlazasDisponibles(plazasDisponibles); // Actualiza la vista
            inscribirView.mostrarMensaje("Reserva realizada");
        }
    }

    @Override
    public void onReservaCancelada() {
        if (inscribirView != null) {
            inscribirView.ocultarCargando();
            inscribirView.actualizarBotonReserva(false);

            // Sumar una plaza disponible después de cancelar la reserva
            plazasDisponibles++;
            inscribirView.actualizarPlazasDisponibles(plazasDisponibles); // Actualiza la vista
            inscribirView.mostrarMensaje("Reserva cancelada");
        }
    }


    private int convertirDiaSemanaAInt(String diaSemana) {
        switch (diaSemana.toLowerCase()) {
            case "lunes": return Calendar.MONDAY;
            case "martes": return Calendar.TUESDAY;
            case "miércoles": return Calendar.WEDNESDAY;
            case "jueves": return Calendar.THURSDAY;
            case "viernes": return Calendar.FRIDAY;
            case "sábado": return Calendar.SATURDAY;
            case "domingo": return Calendar.SUNDAY;
            default: return -1;
        }
    }

    private int calcularDiasHastaClase(int diaActual, int diaClase) {
        if (diaClase >= diaActual) {
            return diaClase - diaActual;
        } else {
            return (7 - diaActual) + diaClase;
        }
    }
}
