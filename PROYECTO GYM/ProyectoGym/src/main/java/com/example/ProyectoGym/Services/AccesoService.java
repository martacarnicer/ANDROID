package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Acceso;
import java.util.List;

public interface AccesoService {
    List<Acceso> obtenerAccesosPorUsuario(Long idUsuario);
    Acceso crearAcceso(Acceso acceso);
    List<Acceso> obtenerTodosLosAccesos();  // Método para obtener todos los accesos
    void actualizarCentroUsuario(Long idUsuario, Long idCentro);
    void otorgarAccesoPremium(Long idUsuario);

    // Agrega este método a la interfaz
    void actualizarCentroAcceso(Long idUsuario, Long idCentro);
}
