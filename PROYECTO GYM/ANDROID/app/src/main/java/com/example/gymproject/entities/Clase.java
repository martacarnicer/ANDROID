package com.example.gymproject.entities;

public class Clase {
    private Long idClase;
    private String nombreClase;
    private int capacidadMaxima;
    private String diaSemana;
    private String horaInicio;
    private String horaFin;
    private String descripcion;
    private Instructor instructor; // Objeto Instructor
    private Centro centro; // Objeto Centro

    // Getters y Setters
    public Long getIdClase() {
        return idClase;
    }

    public void setIdClase(Long idClase) {
        this.idClase = idClase;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Instructor getInstructor() {
        return instructor; // Ahora es un objeto Instructor
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor; // Ahora es un objeto Instructor
    }

    public Centro getCentro() {
        return centro; // Ahora es un objeto Centro
    }

    public void setCentro(Centro centro) {
        this.centro = centro; // Ahora es un objeto Centro
    }
}
