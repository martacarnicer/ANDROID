package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Instructor;
import com.example.ProyectoGym.Repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    @Override
    public Optional<Instructor> buscarPorId(Long idInstructor) {
        return instructorRepository.findById(idInstructor);
    }
}
