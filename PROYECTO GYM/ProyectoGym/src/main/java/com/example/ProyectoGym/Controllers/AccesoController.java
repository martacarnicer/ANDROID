package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Acceso;
import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.CentroRepository;
import com.example.ProyectoGym.Repositories.UsuarioRepository;
import com.example.ProyectoGym.Services.AccesoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/accesos")
public class AccesoController {

    @Autowired
    private AccesoService accesoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CentroRepository centroRepository;

    // Método para obtener todos los accesos
    @GetMapping
    public ResponseEntity<List<Acceso>> obtenerTodosLosAccesos() {
        List<Acceso> accesos = accesoService.obtenerTodosLosAccesos();
        return ResponseEntity.ok(accesos);
    }

    // Obtener todos los accesos de un usuario a los centros
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Acceso>> obtenerAccesosDeUsuario(@PathVariable Long idUsuario) {
        List<Acceso> accesos = accesoService.obtenerAccesosPorUsuario(idUsuario);
        return ResponseEntity.ok(accesos);
    }

    @PostMapping
    public ResponseEntity<Acceso> crearAcceso(@RequestBody Acceso acceso) {
        // Validar que el acceso no sea nulo
        if (acceso == null || acceso.getUsuario() == null || acceso.getCentro() == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // Guardar el nuevo acceso
        Acceso nuevoAcceso = accesoService.crearAcceso(acceso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoAcceso); // Respuesta 201
    }

    @PutMapping("/usuario/{idUsuario}/centro/{idCentro}")
    public ResponseEntity<?> actualizarCentroUsuario(@PathVariable Long idUsuario, @PathVariable Long idCentro) {
        try {
            accesoService.actualizarCentroUsuario(idUsuario, idCentro);
            return ResponseEntity.ok("Centro actualizado exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al actualizar el centro.");
        }
    }


    @PutMapping("/usuario/{idUsuario}/premium")
    public ResponseEntity<?> otorgarAccesoPremium(@PathVariable Long idUsuario) {
        try {
            accesoService.otorgarAccesoPremium(idUsuario);
            return ResponseEntity.ok("Accesos premium otorgados exitosamente.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al otorgar accesos premium.");
        }
    }




}




