package Proyecto.Final.SistemaEscuela.services;

import Proyecto.Final.SistemaEscuela.dtos.AlumnoDTO;
import Proyecto.Final.SistemaEscuela.dtos.AsignaturaDTO;
import Proyecto.Final.SistemaEscuela.models.Alumno;
import Proyecto.Final.SistemaEscuela.models.Asignatura;
import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.models.EstadoAsignatura;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.repositories.AlumnoRepository;
import Proyecto.Final.SistemaEscuela.repositories.AsignaturaRepository;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsignaturaService {

    @Autowired
    private AsignaturaRepository asignaturaRepository;

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private MateriaRepository materiaRepository;

    public List<Asignatura> obtenerTodas() {
        return asignaturaRepository.findAll();
    }

    public Asignatura guardarAsignatura(Asignatura asignatura) {
        return asignaturaRepository.save(asignatura);
    }

    public Optional<Asignatura> obtenerPorId(Long id) {
        return asignaturaRepository.findById(id);
    }

    public void eliminarAsignatura(Long id) {
        asignaturaRepository.deleteById(id);
    }

    public List<Asignatura> obtenerPorEstado(String estado) {
        return asignaturaRepository.findByEstado(estado);
    }

    public Asignatura actualizarEstado(Long id, EstadoAsignatura nuevoEstado) {
        Asignatura asignatura = asignaturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Asignatura no encontrada con ID: " + id));
        asignatura.setEstado(nuevoEstado);
        return asignaturaRepository.save(asignatura);
    }

    // Nuevo método para asignar materia a un alumno
    public Asignatura agregarAsignaturaAAlumno(Long alumnoId, Long materiaId, EstadoAsignatura estado) {
        // Obtener el alumno por ID
        Alumno alumno = alumnoRepository.findById(alumnoId)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con ID: " + alumnoId));

        // Obtener la materia por ID
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materiaId));

        // Verificar si el alumno tiene una carrera asignada
        Carrera carrera = alumno.getCarrera();
        if (carrera == null) {
            throw new RuntimeException("El alumno no tiene una carrera asignada.");
        }

        // Validar si la materia pertenece a la carrera
        if (!carrera.getMaterias().contains(materia)) {
            throw new RuntimeException("La materia '" + materia.getNombre() + "' no pertenece a la carrera '" + carrera.getNombre() + "'.");
        }

        // Crear la asignatura y asociarla con el alumno
        Asignatura asignatura = new Asignatura();
        asignatura.setNombre(materia.getNombre());
        asignatura.setEstado(estado);
        asignatura.setAlumno(alumno);
        asignatura.setMateria(materia);

        // Guardar y devolver la asignatura
        return asignaturaRepository.save(asignatura);
    }


    public AsignaturaDTO convertirADTO(Asignatura asignatura) {
        String carrera = asignatura.getAlumno().getCarrera() != null
                ? asignatura.getAlumno().getCarrera().getNombre()
                : "Sin Carrera";

        AlumnoDTO alumnoDTO = new AlumnoDTO(
                asignatura.getAlumno().getId(),
                asignatura.getAlumno().getNombre(),
                asignatura.getAlumno().getApellido(),
                asignatura.getAlumno().getDni(),
                carrera
        );

        return new AsignaturaDTO(
                asignatura.getId(),
                asignatura.getNombre(),
                asignatura.getEstado().name(),
                alumnoDTO
        );
    }

    public List<AsignaturaDTO> obtenerTodasComoDTO() {
        return asignaturaRepository.findAll().stream()
                .map(this::convertirADTO) // Convierte cada asignatura en un DTO
                .collect(Collectors.toList());
    }
}
