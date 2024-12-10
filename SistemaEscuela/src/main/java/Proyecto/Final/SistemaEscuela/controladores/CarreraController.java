package Proyecto.Final.SistemaEscuela.controladores;

import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import Proyecto.Final.SistemaEscuela.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;
    @Autowired
    private MateriaRepository materiaRepository; // validar las materias



    @GetMapping
    public ResponseEntity<List<Carrera>> obtenerTodas() {
        return ResponseEntity.ok(carreraService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrera> obtenerPorId(@PathVariable Long id) {
        return carreraService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrera> guardarCarrera(@RequestBody Carrera carreraRequest) {
        // Buscar las materias por sus IDs
        List<Materia> materias = carreraRequest.getMaterias().stream()
                .map(materia -> materiaRepository.findById(materia.getId())
                        .orElseThrow(() -> new RuntimeException("Materia no encontrada con ID: " + materia.getId())))
                .collect(Collectors.toList());

        // Crear la carrera con las materias existentes
        Carrera carrera = new Carrera();
        carrera.setNombre(carreraRequest.getNombre());
        carrera.setMaterias(materias);

        return new ResponseEntity<>(carreraService.guardarCarrera(carrera), HttpStatus.CREATED);
    }


    // Método para actualizar la carrera
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCarrera(@PathVariable Long id, @RequestBody Carrera carreraRequest) {
        try {
            Carrera carreraActualizada = carreraService.actualizarCarrera(id, carreraRequest);
            return ResponseEntity.ok(carreraActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCarrera(@PathVariable Long id) {
        carreraService.eliminarCarrera(id);
        return ResponseEntity.noContent().build();
    }
}
