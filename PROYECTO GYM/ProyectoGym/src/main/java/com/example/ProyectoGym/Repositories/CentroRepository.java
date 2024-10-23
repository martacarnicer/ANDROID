package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Centro;

public interface CentroRepository extends JpaRepository<Centro, Long> {
    // Consultas personalizadas si es necesario
}
