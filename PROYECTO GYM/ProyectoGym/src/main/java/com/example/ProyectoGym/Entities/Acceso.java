package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "acceso")
public class Acceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_acceso")
    private Long idAcceso;

    @Column(name = "fecha_inicio_acceso", nullable = false)
    private String fechaInicioAcceso;

    @Column(name = "fecha_fin_acceso")
    private String fechaFinAcceso;


    // RELACIONES

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("accesos") // Evitamos que se serialice la lista de accesos en Usuario
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_centro", nullable = false)
    @JsonIgnoreProperties("accesos") // Evitamos que se serialice la lista de accesos en Centro
    private Centro centro;


    // Getters y Setters

    public Long getIdAcceso() {
        return idAcceso;
    }

    public void setIdAcceso(Long idAcceso) {
        this.idAcceso = idAcceso;
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
}