package Proyecto.Final.SistemaEscuela.controladores;

import Proyecto.Final.SistemaEscuela.models.Profesor;
import Proyecto.Final.SistemaEscuela.services.ProfesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    @Autowired
    private ProfesorService profesorService;

    @GetMapping
    public ResponseEntity<List<Profesor>> obtenerTodos() {
        return ResponseEntity.ok(profesorService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        return profesorService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Profesor> guardarProfesor(@RequestBody Profesor profesor) {
        return new ResponseEntity<>(profesorService.guardarProfesor(profesor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesorActualizado) {
        Profesor profesor = profesorService.actualizarProfesor(id, profesorActualizado);
        return ResponseEntity.ok(profesor);
    }
}
