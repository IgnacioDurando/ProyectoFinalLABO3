package Proyecto.Final.SistemaEscuela.services;

import Proyecto.Final.SistemaEscuela.models.Profesor;
import Proyecto.Final.SistemaEscuela.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfesorService {

    @Autowired
    private ProfesorRepository profesorRepository;

    public List<Profesor> obtenerTodos() {
        return profesorRepository.findAll();
    }

    public Profesor guardarProfesor(Profesor profesor) {
        // Validar si ya existe un profesor con el mismo nombre y apellido
        if (profesorRepository.findByNombreAndApellido(profesor.getNombre(), profesor.getApellido()).isPresent()) {
            throw new RuntimeException("Ya existe un profesor con el nombre y apellido: " + profesor.getNombre() + " " + profesor.getApellido());
        }
        return profesorRepository.save(profesor);
    }
    public Profesor actualizarProfesor(Long id, Profesor profesorActualizado) {
        // Buscar el profesor existente por ID
        Profesor profesorExistente = profesorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el profesor con ID: " + id));

        // Actualizar solo los campos proporcionados en la solicitud
        if (profesorActualizado.getNombre() != null) {
            profesorExistente.setNombre(profesorActualizado.getNombre());
        }
        if (profesorActualizado.getApellido() != null) {
            profesorExistente.setApellido(profesorActualizado.getApellido());
        }

        // Guardar los cambios
        return profesorRepository.save(profesorExistente);
    }

    public Optional<Profesor> obtenerPorId(Long id) {
        return profesorRepository.findById(id);
    }

    public void eliminarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }
}
