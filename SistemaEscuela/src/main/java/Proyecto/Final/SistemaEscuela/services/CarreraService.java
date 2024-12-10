package Proyecto.Final.SistemaEscuela.services;

import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.repositories.CarreraRepository;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarreraService {

    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private MateriaRepository materiaRepository;

    public List<Carrera> obtenerTodas() {
        return carreraRepository.findAll();
    }

    // Guardar una nueva carrera con validación para evitar duplicados
    public Carrera guardarCarrera(Carrera carreraRequest) {
        // Validación para evitar duplicados de carreras por nombre
        if (carreraRepository.findByNombre(carreraRequest.getNombre()).isPresent()) {
            throw new RuntimeException("Ya existe una carrera con el nombre: " + carreraRequest.getNombre());
        }

        // Validar las materias enviadas en la solicitud
        List<Materia> materiasValidas = carreraRequest.getMaterias().stream()
                .map(materia -> materiaRepository.findById(materia.getId())
                        .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materia.getId())))
                .collect(Collectors.toList());

        // Crear una nueva carrera con las materias validadas
        Carrera nuevaCarrera = new Carrera();
        nuevaCarrera.setNombre(carreraRequest.getNombre());
        nuevaCarrera.setMaterias(materiasValidas);

        // Guardar la carrera
        return carreraRepository.save(nuevaCarrera);
    }

    public Optional<Carrera> obtenerPorId(Long id) {
        return carreraRepository.findById(id);
    }


    public Carrera actualizarCarrera(Long id, Carrera carreraRequest) {
        // Buscar la carrera existente en la base de datos
        Carrera carreraExistente = carreraRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la carrera con ID: " + id));

        // Actualizar el nombre de la carrera solo si se proporciona uno nuevo
        if (carreraRequest.getNombre() != null && !carreraRequest.getNombre().isEmpty()) {
            carreraExistente.setNombre(carreraRequest.getNombre());
        }

        // Validar las materias enviadas
        if (carreraRequest.getMaterias() != null) {
            List<Materia> materiasValidas = carreraRequest.getMaterias().stream()
                    .map(materia -> materiaRepository.findById(materia.getId())
                            .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materia.getId())))
                    .collect(Collectors.toList());

            // Actualizar la relación con las materias sin afectar otras carreras
            carreraExistente.getMaterias().clear();
            carreraExistente.getMaterias().addAll(materiasValidas);
        }

        // Guardar los cambios
        return carreraRepository.save(carreraExistente);
    }



    public void eliminarCarrera(Long id) {
        carreraRepository.deleteById(id);
    }
}
