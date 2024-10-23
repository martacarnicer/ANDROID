package com.example.gymproject.entities;


public class Centro {
    private Long idCentro;
    private String nombreCentro;
    private String ubicacion;
    private String telefono;
    private int capacidadMaxima;
    private String horarioApertura;
    private String horarioCierre;
    private String descripcion; // Descripción opcional del centro

    // Constructor vacío
    public Centro() {}

    // Constructor con parámetros
    public Centro(Long idCentro, String nombreCentro, String ubicacion, String telefono, int capacidadMaxima,
                  String horarioApertura, String horarioCierre, String descripcion) {
        this.idCentro = idCentro;
        this.nombreCentro = nombreCentro;
        this.ubicacion = ubicacion;
        this.telefono = telefono;
        this.capacidadMaxima = capacidadMaxima;
        this.horarioApertura = horarioApertura;
        this.horarioCierre = horarioCierre;
        this.descripcion = descripcion;
    }

    public Centro(Long idCentro) {
        this.idCentro = idCentro;
    }


    @Override
    public String toString() {
        return nombreCentro; // Devuelve el nombre del centro para que se muestre en el Spinner
    }

    // Getters y Setters
    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }

    public String getNombreCentro() {
        return nombreCentro;
    }

    public void setNombreCentro(String nombreCentro) {
        this.nombreCentro = nombreCentro;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public String getHorarioApertura() {
        return horarioApertura;
    }

    public void setHorarioApertura(String horarioApertura) {
        this.horarioApertura = horarioApertura;
    }

    public String getHorarioCierre() {
        return horarioCierre;
    }

    public void setHorarioCierre(String horarioCierre) {
        this.horarioCierre = horarioCierre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
