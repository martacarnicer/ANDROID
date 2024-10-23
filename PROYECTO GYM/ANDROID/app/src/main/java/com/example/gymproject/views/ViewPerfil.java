package com.example.gymproject.views;

import com.example.gymproject.entities.Acceso;
import com.example.gymproject.entities.Usuario;

public interface ViewPerfil {
    void mostrarDatosUsuario(Usuario usuario, Acceso acceso);  // Acepta Usuario y Acceso
    void mostrarError(String error);
    void mostrarExitoActualizacion();
    void mostrarExitoBaja();
}
