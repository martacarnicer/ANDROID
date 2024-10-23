package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Instructor;
import java.util.Optional;

public interface InstructorService {
    Optional<Instructor> buscarPorId(Long idInstructor);
}
