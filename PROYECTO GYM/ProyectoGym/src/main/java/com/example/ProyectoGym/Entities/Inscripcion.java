package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "inscripcion")
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_inscripcion")
    private Long idInscripcion;

    @Column(name = "fecha_inscripcion", nullable = false)
    private String fechaInscripcion;


    // RELACIONES


    // ManyToOne con Usuario: Muchas inscripciones pueden pertenecer a un usuario, pero cada inscripción está asociada a un solo usuario.
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties({"inscripciones", "password", "accesos"})  // Excluye campos innecesarios

    private Usuario usuario;

    // ManyToOne con Clase: Muchas inscripciones pueden pertenecer a una clase, pero cada inscripción está asociada a una sola clase.

    @ManyToOne
    @JoinColumn(name = "id_clase", nullable = false)
    @JsonIgnoreProperties({"inscripciones", "centro", "instructor"})  // Evita ciclos o datos redundantes

    private Clase clase;








    // Getters y Setters

    public Long getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(Long idInscripcion) {
        this.idInscripcion = idInscripcion;
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

    public String getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(String fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}
