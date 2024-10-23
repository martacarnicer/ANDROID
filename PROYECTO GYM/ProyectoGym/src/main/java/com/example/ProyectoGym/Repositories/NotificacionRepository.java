package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Notificacion;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    // Consultar notificaciones por usuario
    // Buscar notificaciones por el id del usuario
    List<Notificacion> findByUsuario_IdUsuario(Long usuarioId);
}
