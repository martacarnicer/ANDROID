package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Rol;
import java.util.Optional;

public interface RolService {
    Optional<Rol> buscarRolPorNombre(String nombreRol);
}
