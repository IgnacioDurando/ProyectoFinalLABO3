package Proyecto.Final.SistemaEscuela.services;

import Proyecto.Final.SistemaEscuela.dtos.MateriaDTO;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.models.Profesor;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import Proyecto.Final.SistemaEscuela.repositories.ProfesorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Autowired
    private ProfesorRepository profesorRepository;

    // Obtener todas las materias
    public List<Materia> obtenerTodas() {
        return materiaRepository.findAll();
    }

    // Guardar una nueva materia con validación de correlatividades
    public Materia guardarMateria(Materia materia) {
        // Validar si ya existe una materia con el mismo nombre
        if (materiaRepository.findByNombre(materia.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una materia con el nombre: " + materia.getNombre());
        }

        // Validar correlatividades si están presentes
        if (materia.getCorrelatividades() != null) {
            List<Materia> correlativas = materia.getCorrelatividades()
                    .stream()
                    .map(correlativa -> materiaRepository.findByNombre(correlativa.getNombre())
                            .orElseThrow(() -> new RuntimeException("Materia correlativa no encontrada: " + correlativa.getNombre())))
                    .collect(Collectors.toList());
            materia.setCorrelatividades(correlativas);
        }
        return materiaRepository.save(materia);
    }

    // Obtener una materia por ID
    public Optional<Materia> obtenerPorId(Long id) {
        return materiaRepository.findById(id);
    }

    public Materia actualizarMateria(Long id, Materia materiaActualizada) {
        Materia materiaExistente = materiaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la materia con ID: " + id));

        // Actualizar solo los campos que vienen en el cuerpo de la solicitud
        if (materiaActualizada.getNombre() != null) {
            materiaExistente.setNombre(materiaActualizada.getNombre());
        }
        if (materiaActualizada.getAnio() != 0) { // Suponiendo que no usarás 0 como año válido
            materiaExistente.setAnio(materiaActualizada.getAnio());
        }
        if (materiaActualizada.getCuatrimestre() != 0) { // Suponiendo que no usarás 0 como cuatrimestre válido
            materiaExistente.setCuatrimestre(materiaActualizada.getCuatrimestre());
        }

        // Validar correlatividades si están presentes
        if (materiaActualizada.getCorrelatividades() != null) {
            List<Materia> correlativas = materiaActualizada.getCorrelatividades()
                    .stream()
                    .map(correlativa -> materiaRepository.findByNombre(correlativa.getNombre())
                            .orElseThrow(() -> new RuntimeException("Materia correlativa no encontrada: " + correlativa.getNombre())))
                    .collect(Collectors.toList());
            materiaExistente.setCorrelatividades(correlativas);
        }

        return materiaRepository.save(materiaExistente);
    }


    public void eliminarMateria(Long id) {
        if (!materiaRepository.existsById(id)) {
            throw new RuntimeException("No existe una materia con ese ID: " + id);
        }
        materiaRepository.deleteById(id);
    }


    // Obtener todas las materias asignadas a un profesor
    public List<Materia> obtenerPorProfesorId(Long profesorId) {
        return materiaRepository.findByProfesorIdOrderByNombreAsc(profesorId);
    }

    // Obtener todas las materias como DTOs
    public List<MateriaDTO> obtenerTodasComoDTO() {
        return materiaRepository.findAll().stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    public List<MateriaDTO> obtenerPorProfesorIdComoDTO(Long profesorId) {
        return materiaRepository.findByProfesorIdOrderByNombreAsc(profesorId)
                .stream()
                .map(this::convertirADTO)
                .collect(Collectors.toList());
    }

    // Método para convertir una Materia a DTO

    public MateriaDTO convertirADTO(Materia materia) {
        // Convertir correlatividades a nombres
        List<String> nombresCorrelatividades = materia.getCorrelatividades() != null
                ? materia.getCorrelatividades().stream()
                .map(Materia::getNombre)
                .collect(Collectors.toList())
                : List.of("Sin correlatividades");

        String nombreProfesor = materia.getProfesor() != null ? materia.getProfesor().getNombre() : "Sin Profesor";

        return new MateriaDTO(
                materia.getId(),
                materia.getNombre(),
                materia.getAnio(),
                materia.getCuatrimestre(),
                nombresCorrelatividades,
                nombreProfesor
        );
    }



    // Asignar una materia a un profesor
    public Materia asignarMateriaAProfesor(Long materiaId, Long profesorId) {
        Materia materia = materiaRepository.findById(materiaId)
                .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materiaId));

        Profesor profesor = profesorRepository.findById(profesorId)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + profesorId));

        // Validación para evitar duplicados
        if (profesor.getMaterias().contains(materia)) {
            throw new RuntimeException("La materia ya está asignada a este profesor");
        }

        // Relación bidireccional
        materia.setProfesor(profesor);
        profesor.getMaterias().add(materia);

        profesorRepository.save(profesor);
        return materiaRepository.save(materia);
    }
}
