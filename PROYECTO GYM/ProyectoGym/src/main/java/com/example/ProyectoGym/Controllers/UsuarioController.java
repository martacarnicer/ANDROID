package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Inscripcion;
import com.example.ProyectoGym.Entities.Rol;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Services.ClaseService;
import com.example.ProyectoGym.Services.InscripcionService;
import com.example.ProyectoGym.Services.UsuarioService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private ClaseService claseService;
    @Autowired
    private InscripcionService inscripcionService;


    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUsuarioPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.obtenerUsuarioPorId(id);
        if (usuario.isPresent()) {
            return ResponseEntity.ok(usuario.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Usuario no encontrado
        }
    }

    // Registro de usuario
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {
        try {
            Usuario nuevoUsuario = usuarioService.registrarUsuario(usuario);
            return ResponseEntity.ok(nuevoUsuario); // Devuelve el objeto Usuario en caso de éxito
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Correo electrónico ya registrado. Intenta con otro."); // Error de correo duplicado
        } catch (ConstraintViolationException e) {
            // Manejo de errores de validación
            String message = "Errores de validación: ";
            for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
                String campo = violation.getPropertyPath().toString(); // Obtener el nombre del campo
                switch (campo) {
                    case "email":
                        message = "Formato de email incorrecto.";
                        break;
                    case "nombre":
                    case "apellido":
                    case "telefono":
                        message = "Por favor completa todos los campos obligatorios.";
                        break;
                    default:
                        message = "Error de validación.";
                        break;
                }
                break; // Solo tomamos el primer error encontrado
            }
            return ResponseEntity.badRequest().body(message); // Enviar mensaje detallado al cliente
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ocurrió un error inesperado. Inténtalo de nuevo más tarde."); // Error inesperado
        }
    }




    // Login de usuario
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginData) {
        try {
            Optional<Usuario> usuario = usuarioService.login(loginData.getEmail(), loginData.getPassword());
            if (usuario.isPresent()) {
                return ResponseEntity.ok(usuario.get());  // Usuario activo y autenticado
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body("Credenciales incorrectas. Por favor, revisa tu email y contraseña.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());  // Retorna mensaje de cuenta inactiva
        }
    }


    @PostMapping("/{idUsuario}/reservar/{idClase}")
    public ResponseEntity<String> reservarClase(@PathVariable Long idUsuario, @PathVariable Long idClase) {
        try {
            Inscripcion inscripcion = inscripcionService.inscribirUsuario(idUsuario, idClase);
            return ResponseEntity.ok("Reserva realizada con éxito: " + inscripcion.getIdInscripcion());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
        }
    }


    // Obtener las reservas del usuario
    @GetMapping("/{idUsuario}/mis-reservas")
    public ResponseEntity<List<Clase>> obtenerClasesReservadasPorUsuario(@PathVariable Long idUsuario) {
        List<Clase> clases = claseService.obtenerClasesReservadasPorUsuario(idUsuario);
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/{idUsuario}/mis-reservas/proximas")
    public ResponseEntity<List<Clase>> obtenerClasesReservadasProximas(@PathVariable Long idUsuario) {
        List<Clase> clases = claseService.obtenerClasesReservadasProximas(idUsuario);
        return ResponseEntity.ok(clases);  // Incluye fechaExactaClase
    }


    @GetMapping("/{idUsuario}/mis-reservas/pasadas")
    public ResponseEntity<List<Clase>> obtenerClasesReservadasPasadas(@PathVariable Long idUsuario) {
        List<Clase> clases = claseService.obtenerClasesReservadasPasadas(idUsuario);
        return ResponseEntity.ok(clases);  // Incluye fechaExactaClase
    }




    // Actualizar perfil del usuario
    // Actualizar perfil del usuario
    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuario(@RequestBody Usuario usuario) {
        // Validar si el usuario tiene un rol asignado
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == null) {
            // Asignar un rol por defecto si no se especifica (Ejemplo: id de rol = 1 para 'usuario')
            Rol rolPorDefecto = new Rol();
            rolPorDefecto.setIdRol(1L); // Supón que 1 es el id del rol por defecto para usuarios
            usuario.setRol(rolPorDefecto);
        }

        usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok(usuario);
    }


    // Baja de usuario (desactivar cuenta)
    @DeleteMapping("/baja/{idUsuario}")
    public ResponseEntity<Void> desactivarUsuario(@PathVariable Long idUsuario) {
        usuarioService.desactivarUsuario(idUsuario);
        return ResponseEntity.noContent().build();
    }

    // Obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerTodosLosUsuarios();
        return ResponseEntity.ok(usuarios);
    }


}
