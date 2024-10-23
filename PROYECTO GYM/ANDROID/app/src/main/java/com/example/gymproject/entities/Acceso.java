package com.example.gymproject.entities;


public class Acceso {
    private Long idAcceso;
    private String fechaInicioAcceso;
    private String fechaFinAcceso;
    private Usuario usuario;
    private Centro centro;

    // Getters y Setters

    public Long getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Long idAcceso) {
        this.idAcceso = idAcceso;
    }

    public String getFechaInicioAcceso() {
        return fechaInicioAcceso;
    }

    public void setFechaInicioAcceso(String fechaInicioAcceso) {
        this.fechaInicioAcceso = fechaInicioAcceso;
    }

    public String getFechaFinAcceso() {
        return fechaFinAcceso;
    }

    public void setFechaFinAcceso(String fechaFinAcceso) {
        this.fechaFinAcceso = fechaFinAcceso;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }
}
