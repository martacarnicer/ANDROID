package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import java.util.Set;

@Entity
@Table(name = "instructor")
@JsonIgnoreProperties("clases") // Ignora la relación inversa para evitar el ciclo

public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_instructor")
    private Long idInstructor;

    @Column(name = "nombre_instructor", nullable = false)
    private String nombreInstructor;

    @Column(name = "especialidad", nullable = false)
    private String especialidad;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "photo_url", nullable = true)
    private String photoUrl;



    // Relación 1 a N con clases
    // OneToMany con Clase: Un instructor puede impartir muchas clases, pero cada clase tiene un solo instructor.
    @OneToMany(mappedBy = "instructor")
    private Set<Clase> clases;







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

    public Set<Clase> getClases() {
        return clases;
    }

    public void setClases(Set<Clase> clases) {
        this.clases = clases;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
