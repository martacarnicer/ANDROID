package com.example.ProyectoGym.Controllers;

import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Services.CentroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/centros")
public class CentroController {

    @Autowired
    private CentroService centroService;

    // Obtener todos los centros disponibles
    @GetMapping
    public ResponseEntity<List<Centro>> obtenerTodosLosCentros() {
        List<Centro> centros = centroService.obtenerTodosLosCentros();
        return ResponseEntity.ok(centros);
    }

    // Obtener detalles de un centro por su ID
    @GetMapping("/{idCentro}")
    public ResponseEntity<Centro> obtenerCentroPorId(@PathVariable Long idCentro) {
        Centro centro = centroService.obtenerCentroPorId(idCentro);
        return ResponseEntity.ok(centro);
    }
}
