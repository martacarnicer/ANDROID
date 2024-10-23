package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    // Consultar rol por nombre (para asignar roles fácilmente)
    Rol findByNombreRol(String nombreRol);
}
