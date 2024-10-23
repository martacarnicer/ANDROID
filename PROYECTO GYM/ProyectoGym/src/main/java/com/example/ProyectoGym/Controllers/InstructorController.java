package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Instructor;
import com.example.ProyectoGym.Repositories.CentroRepository;
import com.example.ProyectoGym.Services.CentroService;
import com.example.ProyectoGym.Services.ClaseService;
import com.example.ProyectoGym.Services.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/instructores")
public class InstructorController {

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private ClaseService claseService;

    @Autowired
    private CentroRepository centroRepository;

    // Obtener las clases que imparte un instructor
    @GetMapping("/{idInstructor}/clases")
    public ResponseEntity<List<Clase>> obtenerClasesDelInstructor(@PathVariable Long idInstructor) {
        Optional<Instructor> instructor = instructorService.buscarPorId(idInstructor);
        if (instructor.isPresent()) {
            List<Clase> clases = claseService.obtenerClasesPorInstructor(idInstructor);
            return ResponseEntity.ok(clases);
        } else {
            return ResponseEntity.notFound().build();  // Instructor no encontrado
        }
    }


    // Obtener detalles de una clase específica impartida por el instructor
    @GetMapping("/{idInstructor}/clases/{idClase}")
    public ResponseEntity<Clase> obtenerDetallesClase(@PathVariable Long idInstructor, @PathVariable Long idClase) {
        Optional<Clase> clase = claseService.obtenerClasePorId(idClase);
        if (clase.isPresent() && clase.get().getInstructor().getIdInstructor().equals(idInstructor)) {
            return ResponseEntity.ok(clase.get());
        } else {
            return ResponseEntity.notFound().build();  // Clase no encontrada o no pertenece al instructor
        }
    }

    // Actualizar una clase
    @PutMapping("/{idInstructor}/clases/{idClase}")
    public ResponseEntity<Clase> actualizarClase(
            @PathVariable Long idInstructor,
            @PathVariable Long idClase,
            @RequestBody Map<String, Object> claseActualizada) {

        Optional<Clase> claseExistente = claseService.obtenerClasePorId(idClase);
        if (claseExistente.isPresent() && claseExistente.get().getInstructor().getIdInstructor().equals(idInstructor)) {
            // Extraer los valores de la solicitud JSON
            String nombreClase = (String) claseActualizada.get("nombreClase");
            int capacidadMaxima = (int) claseActualizada.get("capacidadMaxima");
            String diaSemana = (String) claseActualizada.get("diaSemana");  // Actualizado a diaSemana
            String horaInicio = (String) claseActualizada.get("horaInicio");
            String horaFin = (String) claseActualizada.get("horaFin");
            String descripcion = (String) claseActualizada.get("descripcion");  // Incluimos la descripción
            Long idCentro = Long.parseLong(claseActualizada.get("idCentro").toString());

            // Buscar el centro por su ID
            Centro centro = centroRepository.findById(idCentro)
                    .orElseThrow(() -> new RuntimeException("Centro no encontrado"));

            // Actualizar la clase
            Clase clase = claseExistente.get();
            clase.setNombreClase(nombreClase);
            clase.setCapacidadMaxima(capacidadMaxima);
            clase.setDiaSemana(diaSemana);  // Actualizado a usar diaSemana
            clase.setHoraInicio(horaInicio);
            clase.setHoraFin(horaFin);
            clase.setDescripcion(descripcion);  // Seteamos la nueva descripción
            clase.setCentro(centro);  // Asignar el centro actualizado

            Clase claseGuardada = claseService.guardarClase(clase);  // Reutilizamos guardarClase para actualizar
            return ResponseEntity.ok(claseGuardada);
        } else {
            return ResponseEntity.notFound().build();  // Clase no encontrada o no pertenece al instructor
        }
    }

    // Cancelar una clase
    @DeleteMapping("/{idInstructor}/clases/{idClase}")
    public ResponseEntity<Void> cancelarClase(@PathVariable Long idInstructor, @PathVariable Long idClase) {
        Optional<Clase> clase = claseService.obtenerClasePorId(idClase);
        if (clase.isPresent() && clase.get().getInstructor().getIdInstructor().equals(idInstructor)) {
            claseService.cancelarClase(idClase);  // Cancelar la clase
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();  // Clase no encontrada o no pertenece al instructor
        }
    }
}
