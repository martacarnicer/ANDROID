package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Rol;
import com.example.ProyectoGym.Repositories.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Override
    public Optional<Rol> buscarRolPorNombre(String nombreRol) {
        Rol rol = rolRepository.findByNombreRol(nombreRol);
        return Optional.ofNullable(rol);  // Envuelve el rol en un Optional, puede ser null
    }
}
