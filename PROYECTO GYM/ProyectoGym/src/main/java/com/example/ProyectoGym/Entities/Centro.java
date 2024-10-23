package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.Set;

@Entity
@JsonIgnoreProperties("clases")
@Table(name = "centro")
public class Centro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_centro")
    private Long idCentro;

    @Column(name = "nombre_centro", nullable = false)
    private String nombreCentro;

    @Column(name = "ubicacion", nullable = false)
    private String ubicacion;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "capacidad_maxima", nullable = false)
    private int capacidadMaxima;

    @Column(name = "horario_apertura", nullable = false)
    private String horarioApertura;

    @Column(name = "horario_cierre", nullable = false)
    private String horarioCierre;

    @Column(name = "descripcion", nullable = true)
    private String descripcion; // Nueva descripción opcional del centro


    // Relación 1 a N con clases
    @OneToMany(mappedBy = "centro")
    @JsonIgnoreProperties("centro")
    private Set<Clase> clases;

    // Relación 1 a N con accesos
    @OneToMany(mappedBy = "centro")
    @JsonIgnoreProperties("centro")
    private Set<Acceso> accesos;

    // Getters y Setters...

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


    public Set<Clase> getClases() {
        return clases;
    }

    public void setClases(Set<Clase> clases) {
        this.clases = clases;
    }

    public Set<Acceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(Set<Acceso> accesos) {
        this.accesos = accesos;
    }
}
