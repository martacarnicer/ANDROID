package com.example.gymproject.entities;


public class Inscripcion {

    private Long idInscripcion;
    private String fechaInscripcion;
    private Usuario usuario; // Relación con el usuario
    private Clase clase; // Relación con la clase

    // Constructor vacío
    public Inscripcion() {}

    // Constructor con parámetros
    public Inscripcion(Long idInscripcion, String fechaInscripcion, Usuario usuario, Clase clase) {
        this.idInscripcion = idInscripcion;
        this.fechaInscripcion = fechaInscripcion;
        this.usuario = usuario;
        this.clase = clase;
    }

    // Getters y setters
    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Clase getClase() {
        return clase;
    }

    public void setClase(Clase clase) {
        this.clase = clase;
    }
}
