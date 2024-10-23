package com.example.gymproject.entities;

public class Reserva {
    private Long idReserva;
    private String nombreClase;
    private String diaSemana;
    private String fechaInscripcion; // Cambiado de Date a String
    private String horaInicio;
    private String horaFin;
    private Centro centro;  // Change this from String to Centro
    private String fechaExactaClase; // Agregar este campo para almacenar la fecha exacta de la clase

    public String getFechaExactaClase() {
        return fechaExactaClase;
    }

    public void setFechaExactaClase(String fechaExactaClase) {
        this.fechaExactaClase = fechaExactaClase;
    }

    // Getters y setters
    public Long getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(Long idReserva) {
        this.idReserva = idReserva;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
}
