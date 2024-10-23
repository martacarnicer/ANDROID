package com.example.gymproject.entities;

public class Usuario {
    private Long idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String fechaNacimiento;  // Ahora es un String en lugar de LocalDate
    private String tipoCuota;
    private String telefono;
    private String fechaRegistro;  // Ahora es un String en lugar de LocalDate
    private boolean estadoActivo;

    // Constructor para el login (solo email y password)
    public Usuario(String email, String password) {
        this.email = email;
        this.password = password;
    }


    // Constructor completo para el registro
    public Usuario(String nombre, String apellido, String email, String password, String telefono, String tipoCuota) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
        this.telefono = telefono;
        this.tipoCuota = tipoCuota;
        this.estadoActivo = true;  // Asumimos que al registrarse está activo
    }

    public Usuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

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

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isEstadoActivo() {
        return estadoActivo;
    }

    public void setEstadoActivo(boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }
}
