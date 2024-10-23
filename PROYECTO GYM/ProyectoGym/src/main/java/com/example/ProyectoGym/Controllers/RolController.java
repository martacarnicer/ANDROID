package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Rol;
import com.example.ProyectoGym.Services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    // Obtener un rol por su nombre
    @GetMapping("/{nombreRol}")
    public ResponseEntity<Rol> obtenerRolPorNombre(@PathVariable String nombreRol) {
        Optional<Rol> rol = rolService.buscarRolPorNombre(nombreRol);
        return rol.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
