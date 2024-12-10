package Proyecto.Final.SistemaEscuela.services;

import Proyecto.Final.SistemaEscuela.dtos.AlumnoDTO;
import Proyecto.Final.SistemaEscuela.models.Alumno;
import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.repositories.AlumnoRepository;
import Proyecto.Final.SistemaEscuela.repositories.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    // Obtener todos los alumnos
    public List<Alumno> obtenerTodos() {
        return alumnoRepository.findAll();
    }

    // Guardar un nuevo alumno
    public Alumno guardarAlumno(Alumno alumnoRequest) {
        if (alumnoRequest.getCarrera() == null) {
            throw new IllegalArgumentException("La carrera no puede ser nula");
        }

        Carrera carrera = carreraRepository.findById(alumnoRequest.getCarrera().getId())
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada con ID: " + alumnoRequest.getCarrera().getId()));


        // Configurar la relación
        alumnoRequest.setCarrera(carrera);

        return alumnoRepository.save(alumnoRequest);
    }

    // Obtener un alumno por ID
    public Optional<Alumno> obtenerPorId(Long id) {
        return alumnoRepository.findById(id);
    }

    // Eliminar un alumno
    public void eliminarAlumno(Long id) {
        alumnoRepository.deleteById(id);
    }

    // Actualizar un alumno
    public Alumno actualizarAlumno(Long id, Alumno alumnoActualizado) {
        // Buscar el alumno existente por ID
        Alumno alumnoExistente = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el alumno con ID: " + id));

        // Actualizar solo los campos que vienen en el cuerpo de la solicitud
        if (alumnoActualizado.getNombre() != null) {
            alumnoExistente.setNombre(alumnoActualizado.getNombre());
        }
        if (alumnoActualizado.getApellido() != null) {
            alumnoExistente.setApellido(alumnoActualizado.getApellido());
        }
        if (alumnoActualizado.getDni() != null) {
            alumnoExistente.setDni(alumnoActualizado.getDni());
        }

        return alumnoRepository.save(alumnoExistente);
    }

    // Convertir un Alumno a DTO
    public AlumnoDTO convertirADTO(Alumno alumno) {
        String carrera = alumno.getCarrera() != null ? alumno.getCarrera().getNombre() : "Sin Carrera";

        List<String> asignaturas = alumno.getAsignaturas().stream()
                .map(asignatura -> asignatura.getNombre() + " (" + asignatura.getEstado() + ")")
                .collect(Collectors.toList());

        return new AlumnoDTO(
                alumno.getId(),
                alumno.getNombre(),
                alumno.getApellido(),
                alumno.getDni(),
                carrera,
                asignaturas
        );
    }

    // Obtener todos los alumnos como DTOs
    public List<AlumnoDTO> obtenerTodosComoDTO() {
        return alumnoRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }
}
