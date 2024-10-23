package com.example.ProyectoGym.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ProyectoGym.Entities.Clase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ClaseRepository extends JpaRepository<Clase, Long> {

    List<Clase> findAll();

    Optional<Clase> findById(Long id);

    @Query("SELECT c FROM Clase c WHERE c.horaInicio >= :horaInicio AND c.horaInicio <= :horaFin")
    List<Clase> findByHoraInicioBetween(@Param("horaInicio") String horaInicio, @Param("horaFin") String horaFin);




    Clase save(Clase clase);

    void deleteById(Long id);

    @Query("SELECT c FROM Clase c WHERE (:nombre IS NULL OR c.nombreClase LIKE %:nombre%) " +
            "AND (:diaSemana IS NULL OR c.diaSemana = :diaSemana) " +
            "AND (:instructorId IS NULL OR c.instructor.idInstructor = :instructorId) " +
            "AND (:centroId IS NULL OR c.centro.idCentro = :centroId)")
    List<Clase> findByFilters(@Param("nombre") String nombre,
                              @Param("diaSemana") String diaSemana,
                              @Param("instructorId") Long instructorId,
                              @Param("centroId") Long centroId);

    @Query("SELECT c FROM Clase c WHERE c.instructor.idInstructor = :instructorId")
    List<Clase> findByInstructor(@Param("instructorId") Long instructorId);

    @Query("SELECT c FROM Clase c WHERE c.diaSemana = :diaSemana " +
            "AND c.horaInicio >= :horaInicio AND c.horaFin <= :horaFin")
    List<Clase> findByDiaSemanaAndHoraBetween(@Param("diaSemana") String diaSemana,
                                              @Param("horaInicio") String horaInicio,
                                              @Param("horaFin") String horaFin);


    @Query("SELECT c FROM Clase c JOIN c.inscripciones i WHERE i.usuario.idUsuario = :idUsuario")
    List<Clase> findByUsuarioId(@Param("idUsuario") Long idUsuario);


    @Query("SELECT c FROM Clase c JOIN c.inscripciones i " +
            "WHERE i.usuario.idUsuario = :idUsuario " +
            "AND (CASE " +
            "  WHEN c.diaSemana = 'Lunes' THEN 1 " +
            "  WHEN c.diaSemana = 'Martes' THEN 2 " +
            "  WHEN c.diaSemana = 'Miércoles' THEN 3 " +
            "  WHEN c.diaSemana = 'Jueves' THEN 4 " +
            "  WHEN c.diaSemana = 'Viernes' THEN 5 " +
            "  WHEN c.diaSemana = 'Sábado' THEN 6 " +
            "  WHEN c.diaSemana = 'Domingo' THEN 7 " +
            "END) > :diaActualNumero " +
            "OR (c.diaSemana = :diaActualTexto AND c.horaInicio > :horaActual)")
    List<Clase> findClasesProximas(
            @Param("idUsuario") Long idUsuario,
            @Param("diaActualNumero") int diaActualNumero,
            @Param("diaActualTexto") String diaActualTexto,
            @Param("horaActual") String horaActual);


    @Query("SELECT c FROM Clase c JOIN c.inscripciones i " +
            "WHERE i.usuario.idUsuario = :idUsuario " +
            "AND (c.diaSemana < :diaActual " +
            "OR (c.diaSemana = :diaActual AND c.horaFin < :horaActual))")
    List<Clase> findClasesPasadas(@Param("idUsuario") Long idUsuario,
                                  @Param("diaActual") String diaActual,
                                  @Param("horaActual") String horaActual);


    @Query("SELECT c FROM Clase c WHERE c.diaSemana = :dia")
    List<Clase> findByDiaSemana(@Param("dia") String dia);


    @Query("SELECT c FROM Clase c WHERE c.diaSemana = :dia AND c.centro.idCentro = :idCentro")
    List<Clase> findClasesPorDiaYCentro(@Param("dia") String dia, @Param("idCentro") Long idCentro);



}
