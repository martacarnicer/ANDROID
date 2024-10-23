package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Acceso;

import java.util.List;

public interface AccesoRepository extends JpaRepository<Acceso, Long> {
    List<Acceso> findByUsuario_IdUsuario(Long idUsuario);
    void deleteByUsuario_IdUsuario(Long idUsuario);  // Borra los accesos por ID de usuario

}
