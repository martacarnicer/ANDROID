package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "clase")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clase")
    private Long idClase;

    @Column(name = "nombre_clase", nullable = false)
    private String nombreClase;

    @Column(name = "capacidad_maxima", nullable = false)
    private int capacidadMaxima;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana; // Almacena el día de la semana (ejemplo: "Lunes")

    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private String horaFin;

    @Column(name = "descripcion", nullable = true, length = 500)
    private String descripcion; // Nueva descripción de la clase

    @Transient  // No persistimos esta columna en la base de datos
    private LocalDate fechaExactaClase;

    // Getter y setter para la fecha exacta de la clase
    public LocalDate getFechaExactaClase() {
        return fechaExactaClase;
    }

    public void setFechaExactaClase(LocalDate fechaExactaClase) {
        this.fechaExactaClase = fechaExactaClase;
    }



    // Relación con el instructor (no ignoramos aquí para poder obtener los detalles completos)
    @ManyToOne
    @JoinColumn(name = "id_instructor", nullable = false)
    private Instructor instructor;

    // Relación con el centro (puedes ignorar otras propiedades para evitar ciclos infinitos)
    @ManyToOne
    @JoinColumn(name = "id_centro", nullable = false)
    @JsonIgnoreProperties("clases") // Ignora la relación inversa si hay un bucle entre clase y centro
    private Centro centro;

    // Relación 1 a N con inscripciones (ignorar inscripciones para evitar bucles infinitos)
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Inscripcion> inscripciones;


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
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public Centro getCentro() {
        return centro;
    }

    public void setCentro(Centro centro) {
        this.centro = centro;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }
}
