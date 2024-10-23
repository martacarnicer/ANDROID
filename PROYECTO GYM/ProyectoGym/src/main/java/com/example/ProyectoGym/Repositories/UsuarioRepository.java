package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Consultar usuario por email (para login)
    Optional<Usuario> findByEmail(String email);

    // Verificar si el email ya existe (para registro)
    boolean existsByEmail(String email);


}
