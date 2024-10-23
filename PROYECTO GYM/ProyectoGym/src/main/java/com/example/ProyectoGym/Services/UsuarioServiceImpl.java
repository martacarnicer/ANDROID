package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Rol;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.RolRepository;
import com.example.ProyectoGym.Repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private RolRepository rolRepository;


    @Override
    public Usuario registrarUsuario(Usuario usuario) {
        // Validar si el email ya existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        // Asignar directamente el rol con id 1
        Rol rol = new Rol();
        rol.setIdRol(1L); // Suponiendo que el rol con id 1 es para nuevos usuarios
        usuario.setRol(rol);

        // Establecer la fecha de registro en el formato "dd-MM-yyyy"
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy");
        String fechaActual = formatoFecha.format(new Date());
        usuario.setFechaRegistro(fechaActual);

        // Guardar el nuevo usuario
        return usuarioRepository.save(usuario);
    }




    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == null) {
            Rol rolPorDefecto = new Rol();
            rolPorDefecto.setIdRol(1L); // Asigna el id del rol por defecto
            usuario.setRol(rolPorDefecto);
        }
        usuarioRepository.save(usuario);
    }

    @Override
    public void desactivarUsuario(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
        usuario.ifPresent(u -> {
            u.setEstadoActivo(false);  // Desactivar el usuario
            usuarioRepository.save(u);
        });
    }

    // Método para login
    @Override
    public Optional<Usuario> login(String email, String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        if (usuario.isPresent()) {
            Usuario usuarioEncontrado = usuario.get();
            // Verificamos si las credenciales son correctas
            if (usuarioEncontrado.getPassword().equals(password)) {
                // Verificamos si el usuario está activo
                if (usuarioEncontrado.isEstadoActivo()) {
                    return Optional.of(usuarioEncontrado);  // Usuario autenticado y activo
                } else {
                    // Usuario inactivo, retornamos vacío
                    throw new IllegalArgumentException("Usuario no activo. Contacte con soporte.");
                }
            }
        }
        return Optional.empty();  // Credenciales incorrectas
    }

    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioRepository.findAll();  // Esto devuelve todos los usuarios desde la base de datos
    }

    // Implementación del nuevo método para obtener un usuario por su ID
    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario);
    }
}
