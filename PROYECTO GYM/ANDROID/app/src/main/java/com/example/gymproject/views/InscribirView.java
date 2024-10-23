package com.example.gymproject.views;

public interface InscribirView {
    void mostrarDetallesClase(String nombreClase, String horaClase, String emailInstructor, String descripcion, int plazasDisponibles, String fechaClase, String photoUrl);
    void actualizarPlazasDisponibles(int nuevasPlazasDisponibles);
    void actualizarBotonReserva(boolean estaInscrito);
    void mostrarCargando();
    void ocultarCargando();
    void mostrarError(String mensaje);
    void mostrarMensaje(String mensaje);
}
