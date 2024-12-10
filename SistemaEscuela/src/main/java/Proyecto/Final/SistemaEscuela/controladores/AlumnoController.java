package Proyecto.Final.SistemaEscuela.controladores;

import Proyecto.Final.SistemaEscuela.dtos.AlumnoDTO;
import Proyecto.Final.SistemaEscuela.models.Alumno;
import Proyecto.Final.SistemaEscuela.services.AlumnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alumnos")
public class AlumnoController {

    @Autowired
    private AlumnoService alumnoService;

    // Obtener todos los alumnos como DTO
    @GetMapping
    public ResponseEntity<List<AlumnoDTO>> obtenerTodos() {
        return ResponseEntity.ok(alumnoService.obtenerTodosComoDTO());
    }

    // Obtener un alumno por ID como DTO
    @GetMapping("/{id}")
    public ResponseEntity<AlumnoDTO> obtenerPorId(@PathVariable Long id) {
        return alumnoService.obtenerPorId(id)
                .map(alumnoService::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar un nuevo alumno
    @PostMapping
    public ResponseEntity<Alumno> guardarAlumno(@RequestBody Alumno alumno) {
        return new ResponseEntity<>(alumnoService.guardarAlumno(alumno), HttpStatus.CREATED);
    }

    // Eliminar un alumno por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAlumno(@PathVariable Long id) {
        alumnoService.eliminarAlumno(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar un alumno por ID
    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizarAlumno(@PathVariable Long id, @RequestBody Alumno alumnoActualizado) {
        Alumno alumno = alumnoService.actualizarAlumno(id, alumnoActualizado);
        return ResponseEntity.ok(alumno);
    }
}
