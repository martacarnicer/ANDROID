package com.example.ProyectoGym.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;

    @Column(name = "tipo_cuota", nullable = false)
    private String tipoCuota;

    @Column(name = "fecha_registro")
    private String fechaRegistro;

    @Column(name = "telefono", nullable = true)
    private String telefono;

    @Column(name = "estado_activo", nullable = false)
    private boolean estadoActivo;

    // Constructor con idUsuario
    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuario(){

    }



    // Relación 1 a N con inscripciones (con la tabla intermedia)
    // OneToMany con Inscripcion: Un usuario puede tener muchas inscripciones en clases, pero cada inscripción está asociada a un solo usuario.
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore  // Evitamos que las inscripciones sean serializadas al devolver el usuario
    private Set<Inscripcion> inscripciones;

    // Relación 1 a N con accesos (con la tabla intermedia)
    // OneToMany con Acceso: Un usuario puede tener muchos accesos a centros, pero cada acceso pertenece a un solo usuario.
    @OneToMany(mappedBy = "usuario")
    @JsonIgnore  // Evitamos que los accesos sean serializados al devolver el usuario

    private Set<Acceso> accesos;

    // Relación ManyToOne con Rol
    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    @JsonIgnoreProperties("usuarios") // Evitamos que la lista de usuarios dentro de Rol sea serializada
    private Rol rol;











    // Getters y Setters

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getTipoCuota() {
        return tipoCuota;
    }

    public void setTipoCuota(String tipoCuota) {
        this.tipoCuota = tipoCuota;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public Set<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(Set<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    public Set<Acceso> getAccesos() {
        return accesos;
    }

    public void setAccesos(Set<Acceso> accesos) {
        this.accesos = accesos;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
