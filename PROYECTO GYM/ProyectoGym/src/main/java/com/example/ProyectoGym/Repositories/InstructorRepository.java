package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Instructor;

public interface InstructorRepository extends JpaRepository<Instructor, Long> {
    // Métodos personalizados si se necesitan en el futuro
}
