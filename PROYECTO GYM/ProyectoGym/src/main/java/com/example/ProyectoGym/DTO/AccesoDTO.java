package com.example.ProyectoGym.DTO;

import java.time.LocalDate;

public class AccesoDTO {
    private LocalDate fechaInicioAcceso;
    private LocalDate fechaFinAcceso;
    private Long idUsuario;
    private Long idCentro;

    public LocalDate getFechaInicioAcceso() {
        return fechaInicioAcceso;
    }

    public void setFechaInicioAcceso(LocalDate fechaInicioAcceso) {
        this.fechaInicioAcceso = fechaInicioAcceso;
    }

    public LocalDate getFechaFinAcceso() {
        return fechaFinAcceso;
    }

    public void setFechaFinAcceso(LocalDate fechaFinAcceso) {
        this.fechaFinAcceso = fechaFinAcceso;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Long getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Long idCentro) {
        this.idCentro = idCentro;
    }
}
