package Proyecto.Final.SistemaEscuela.controladores;

import Proyecto.Final.SistemaEscuela.dtos.MateriaDTO;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.services.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
public class MateriaController {

    @Autowired
    private MateriaService materiaService;

    // Obtener todas las materias (convertidas a DTO)
    @GetMapping
    public ResponseEntity<List<MateriaDTO>> obtenerTodas() {
        return ResponseEntity.ok(materiaService.obtenerTodasComoDTO());
    }

    // Obtener una materia por ID (convertida a DTO)
    @GetMapping("/{id}")
    public ResponseEntity<MateriaDTO> obtenerPorId(@PathVariable Long id) {
        return materiaService.obtenerPorId(id)
                .map(materiaService::convertirADTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Guardar una nueva materia
    @PostMapping
    public ResponseEntity<Materia> guardarMateria(@RequestBody Materia materia) {
        return new ResponseEntity<>(materiaService.guardarMateria(materia), HttpStatus.CREATED);
    }

    //Actualizar una materia
    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable Long id, @RequestBody Materia materiaActualizada) {
        Materia materia = materiaService.actualizarMateria(id, materiaActualizada);
        return ResponseEntity.ok(materia);
    }

    // Obtener materias asignadas a un profesor (convertidas a DTO)
    @GetMapping("/profesor/{idProfesor}")
    public ResponseEntity<List<MateriaDTO>> obtenerPorProfesor(@PathVariable Long idProfesor) {
        return ResponseEntity.ok(materiaService.obtenerPorProfesorIdComoDTO(idProfesor));
    }

    // Eliminar una materia por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMateria(@PathVariable Long id) {
        materiaService.eliminarMateria(id);
        return ResponseEntity.noContent().build();
    }

    // Asignar una materia a un profesor
    @PutMapping("/{materiaId}/asignar-profesor/{profesorId}")
    public ResponseEntity<Materia> asignarProfesor(@PathVariable Long materiaId, @PathVariable Long profesorId) {
        Materia materia = materiaService.asignarMateriaAProfesor(materiaId, profesorId);
        return ResponseEntity.ok(materia);
    }
}
