package com.example.ProyectoGym.Services;

import com.example.ProyectoGym.Entities.Acceso;
import com.example.ProyectoGym.Entities.Centro;
import com.example.ProyectoGym.Entities.Usuario;
import com.example.ProyectoGym.Repositories.AccesoRepository;
import com.example.ProyectoGym.Repositories.CentroRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AccesoServiceImpl implements AccesoService {

    @Autowired
    private AccesoRepository accesoRepository;

    @Autowired
    private CentroRepository centroRepository;  // Inyectamos el CentroRepository

    @Override
    public List<Acceso> obtenerAccesosPorUsuario(Long idUsuario) {
        return accesoRepository.findByUsuario_IdUsuario(idUsuario);
    }

    @Override
    public Acceso crearAcceso(Acceso acceso) {
        return accesoRepository.save(acceso);
    }

    @Override
    public List<Acceso> obtenerTodosLosAccesos() {
        return accesoRepository.findAll();  // Retorna todos los accesos
    }


    @Override
    public void actualizarCentroAcceso(Long idUsuario, Long idCentro) {
        // Actualiza el centro en el acceso del usuario
        List<Acceso> accesos = accesoRepository.findByUsuario_IdUsuario(idUsuario);
        if (!accesos.isEmpty()) {
            Acceso acceso = accesos.get(0);  // Asumiendo un solo acceso para usuarios Básicos
            acceso.setCentro(centroRepository.findById(idCentro).orElse(null));  // Usamos centroRepository
            accesoRepository.save(acceso);
        }
    }

    @Override
    public void actualizarCentroUsuario(Long idUsuario, Long idCentro) {
        List<Acceso> accesos = accesoRepository.findByUsuario_IdUsuario(idUsuario);
        if (!accesos.isEmpty()) {
            Acceso acceso = accesos.get(0);  // Asumiendo que solo tiene un acceso cuando es básico
            acceso.setCentro(centroRepository.findById(idCentro).orElse(null));
            accesoRepository.save(acceso);
        }
    }

    @Transactional  // Añadimos esta anotación para gestionar la transacción
    @Override
    public void otorgarAccesoPremium(Long idUsuario) {
        // Eliminar los accesos actuales del usuario
        accesoRepository.deleteByUsuario_IdUsuario(idUsuario);

        // Obtener todos los centros
        List<Centro> centros = centroRepository.findAll();

        // Crear un acceso a cada centro para el usuario
        for (Centro centro : centros) {
            Acceso nuevoAcceso = new Acceso();
            nuevoAcceso.setUsuario(new Usuario(idUsuario)); // Establecemos el usuario
            nuevoAcceso.setCentro(centro); // Establecemos el centro
            nuevoAcceso.setFechaInicioAcceso(LocalDate.now().toString()); // Definir la fecha de inicio
            accesoRepository.save(nuevoAcceso); // Guardamos el acceso
        }
    }
}
