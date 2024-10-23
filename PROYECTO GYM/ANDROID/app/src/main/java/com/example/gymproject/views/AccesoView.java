package com.example.gymproject.views;

import com.example.gymproject.entities.Acceso;
import java.util.List;

public interface AccesoView {
    void mostrarAccesos(List<Acceso> accesos);
    void mostrarError(String mensaje);
}
