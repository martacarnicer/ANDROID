package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Inscripcion;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
    // Consultar el historial de inscripciones de un usuario
    List<Inscripcion> findByUsuario_IdUsuario(Long usuarioId);

    // Consultar inscripciones por clase
    List<Inscripcion> findByClase_IdClase(Long claseId);

    @Query("SELECT COUNT(i) FROM Inscripcion i WHERE i.clase.idClase = :idClase")
    int countByClaseId(@Param("idClase") Long idClase);
}
