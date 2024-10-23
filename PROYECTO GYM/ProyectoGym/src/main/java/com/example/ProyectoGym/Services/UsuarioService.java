package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    Usuario registrarUsuario(Usuario usuario);

    Optional<Usuario> buscarPorEmail(String email);

    void actualizarUsuario(Usuario usuario);

    void desactivarUsuario(Long idUsuario);

    Optional<Usuario> login(String email, String password);

    List<Usuario> obtenerTodosLosUsuarios();

    // Nuevo método para obtener un usuario por su ID
    Optional<Usuario> obtenerUsuarioPorId(Long idUsuario);

}
