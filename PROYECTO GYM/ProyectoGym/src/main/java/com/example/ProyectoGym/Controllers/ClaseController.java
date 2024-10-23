package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Entities.Clase;
import com.example.ProyectoGym.Entities.Instructor;
import com.example.ProyectoGym.Repositories.CentroRepository;
import com.example.ProyectoGym.Repositories.InstructorRepository;
import com.example.ProyectoGym.Services.ClaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/clases")
public class ClaseController {
    @Autowired
    private ClaseService claseService;
    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CentroRepository centroRepository;

    private static final Map<String, String> DIA_TRADUCCION = Map.of(
            "Monday", "Lunes",
            "Tuesday", "Martes",
            "Wednesday", "Miércoles",
            "Thursday", "Jueves",
            "Friday", "Viernes",
            "Saturday", "Sábado",
            "Sunday", "Domingo"
    );


    // Filtrar clases por nombre, día de la semana, instructor, centro, y rango de días
    @GetMapping("/filtrar")
    public ResponseEntity<List<Clase>> filtrarClases(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String diaSemana,
            @RequestParam(required = false) Long idInstructor,
            @RequestParam(required = false) Long idCentro) {

        List<Clase> clases = claseService.filtrarClases(nombre, diaSemana, idInstructor, idCentro);
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/filtrar/horario")
    public ResponseEntity<List<Clase>> obtenerClasesPorRangoHorario(
            @RequestParam String diaSemana,
            @RequestParam String horaInicio,
            @RequestParam String horaFin) {
        List<Clase> clases = claseService.filtrarPorHorario(diaSemana, horaInicio, horaFin);
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/{idClase}/disponibilidad")
    public ResponseEntity<Integer> obtenerDisponibilidad(@PathVariable Long idClase) {
        int cuposDisponibles = claseService.obtenerDisponibilidad(idClase);
        return ResponseEntity.ok(cuposDisponibles);
    }



    // Método para crear una nueva clase
    @PostMapping("/crear")
    public ResponseEntity<Clase> crearClase(@RequestBody Map<String, Object> requestData) {
        // Extraer los datos del JSON
        String nombreClase = (String) requestData.get("nombreClase");
        int capacidadMaxima = (int) requestData.get("capacidadMaxima");
        String diaSemana = (String) requestData.get("diaSemana");  // Usamos String para el día de la semana
        String horaInicio = (String) requestData.get("horaInicio");  // Usamos String
        String horaFin = (String) requestData.get("horaFin");        // Usamos String
        String descripcion = (String) requestData.get("descripcion");  // Descripción de la clase

        Long idInstructor = Long.parseLong(requestData.get("idInstructor").toString());
        Long idCentro = Long.parseLong(requestData.get("idCentro").toString());

        // Buscar el instructor por su ID
        Instructor instructor = instructorRepository.findById(idInstructor)
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        // Buscar el centro por su ID
        Centro centro = centroRepository.findById(idCentro)
                .orElseThrow(() -> new RuntimeException("Centro no encontrado"));

        // Crear la clase
        Clase clase = new Clase();
        clase.setNombreClase(nombreClase);
        clase.setCapacidadMaxima(capacidadMaxima);
        clase.setDiaSemana(diaSemana);  // Usamos día de la semana
        clase.setHoraInicio(horaInicio);
        clase.setHoraFin(horaFin);
        clase.setDescripcion(descripcion);  // Seteamos la descripción
        clase.setInstructor(instructor);
        clase.setCentro(centro);

        // Guardar la nueva clase
        Clase nuevaClase = claseService.crearClase(clase);
        return ResponseEntity.ok(nuevaClase);
    }

    // Obtener una clase específica por su ID
    @GetMapping("/{idClase}")
    public ResponseEntity<Clase> obtenerClasePorId(@PathVariable Long idClase) {
        Optional<Clase> clase = claseService.obtenerClasePorId(idClase);
        return clase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cancelar una clase (solo instructores)
    @DeleteMapping("/cancelar/{idClase}")
    public ResponseEntity<Void> cancelarClase(@PathVariable Long idClase) {
        claseService.cancelarClase(idClase);
        return ResponseEntity.noContent().build();
    }

    // Obtener todas las clases (sin filtros)
    @GetMapping
    public ResponseEntity<List<Clase>> obtenerTodasLasClases() {
        List<Clase> clases = claseService.obtenerTodasLasClases();
        return ResponseEntity.ok(clases);
    }

    // Crear o actualizar una clase
    @PostMapping("/guardar")
    public ResponseEntity<Clase> guardarClase(@RequestBody Clase clase) {
        Clase claseGuardada = claseService.guardarClase(clase);
        return ResponseEntity.ok(claseGuardada);
    }

    // Eliminar una clase por ID
    @DeleteMapping("/{idClase}")
    public ResponseEntity<Void> eliminarClase(@PathVariable Long idClase) {
        claseService.eliminarClase(idClase);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/dia/{dia}")
    public ResponseEntity<List<Clase>> obtenerClasesPorDia(@PathVariable String dia) {
        List<Clase> clases = claseService.obtenerClasesPorDia(dia);
        return ResponseEntity.ok(clases);
    }

    @GetMapping("/dia/{dia}/centro/{idCentro}")
    public ResponseEntity<List<Clase>> obtenerClasesPorDiaYCentro(@PathVariable String dia, @PathVariable Long idCentro) {
        // Convertir día de inglés a español si es necesario
        String diaTraducido = DIA_TRADUCCION.getOrDefault(dia, dia);

        // Ahora buscar usando el día traducido
        List<Clase> clases = claseService.obtenerClasesPorDiaYCentro(diaTraducido, idCentro);
        return ResponseEntity.ok(clases);
    }






}
