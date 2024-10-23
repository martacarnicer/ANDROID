package com.example.gymproject.entities;

public class Instructor {
    private Long idInstructor;
    private String nombreInstructor;
    private String especialidad;
    private String email;
    private String telefono;
    private String photoUrl; // Agregar el campo para la URL de la foto

    // Constructor vacío
    public Instructor() {}

    // Constructor con parámetros
    public Instructor(Long idInstructor, String nombreInstructor, String especialidad, String email, String telefono, String photoUrl) {
        this.idInstructor = idInstructor;
        this.nombreInstructor = nombreInstructor;
        this.especialidad = especialidad;
        this.email = email;
        this.telefono = telefono;
        this.photoUrl = photoUrl; // Inicializar el campo photoUrl
    }

    // Getters y Setters
    public Long getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(Long idInstructor) {
        this.idInstructor = idInstructor;
    }

    public String getNombreInstructor() {
        return nombreInstructor;
    }

    public void setNombreInstructor(String nombreInstructor) {
        this.nombreInstructor = nombreInstructor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPhotoUrl() {  // Getter para photoUrl
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {  // Setter para photoUrl
        this.photoUrl = photoUrl;
    }
}
