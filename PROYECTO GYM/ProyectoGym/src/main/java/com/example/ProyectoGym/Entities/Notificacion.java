package com.example.ProyectoGym.Entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacion")
public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificacion")
    private Long idNotificacion;

    @Column(name = "titulo", nullable = false)
    private String titulo;  // Título de la notificación

    @Column(name = "contenido", nullable = false)
    private String contenido;  // Contenido del mensaje (dinámico)

    @Column(name = "fecha_programada", nullable = false)
    private LocalDateTime fechaProgramada;  // Fecha y hora para enviar la notificación

    @Column(name = "enviado", nullable = false)
    private boolean enviado;  // Para marcar si la notificación ya ha sido enviada

    @Column(name = "tipo_notificacion", nullable = false)
    private String tipoNotificacion;  // Tipo de notificación (ej. "recordatorio_clase", "informacion_asistentes")




    // RELACION CON EL USUARIO

    // ManyToOne con Usuario: Muchas notificaciones pueden pertenecer a un usuario, pero cada notificación está asociada a un solo usuario.
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;  // Usuario que recibirá la notificación





    // Getters y Setters
    public Long getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Long idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDateTime getFechaProgramada() {
        return fechaProgramada;
    }

    public void setFechaProgramada(LocalDateTime fechaProgramada) {
        this.fechaProgramada = fechaProgramada;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public boolean isEnviado() {
        return enviado;
    }

    public void setEnviado(boolean enviado) {
        this.enviado = enviado;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }
}
