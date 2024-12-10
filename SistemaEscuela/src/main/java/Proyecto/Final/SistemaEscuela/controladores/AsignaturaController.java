package Proyecto.Final.SistemaEscuela.controladores;

import Proyecto.Final.SistemaEscuela.dtos.AsignaturaDTO;
import Proyecto.Final.SistemaEscuela.models.Asignatura;
import Proyecto.Final.SistemaEscuela.models.EstadoAsignatura;
import Proyecto.Final.SistemaEscuela.services.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    @GetMapping
    public ResponseEntity<List<AsignaturaDTO>> obtenerTodas() {
        List<AsignaturaDTO> asignaturas = asignaturaService.obtenerTodasComoDTO();
        return ResponseEntity.ok(asignaturas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Asignatura> obtenerPorId(@PathVariable Long id) {
        return asignaturaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Asignatura> guardarAsignatura(@RequestBody Asignatura asignatura) {
        return new ResponseEntity<>(asignaturaService.guardarAsignatura(asignatura), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/estado")
    public ResponseEntity<Void> cambiarEstado(@PathVariable Long id, @RequestBody Map<String, String> request) {
        String estadoStr = request.get("estado");
        EstadoAsignatura nuevoEstado = EstadoAsignatura.valueOf(estadoStr);  // Convierte la cadena al enum

        asignaturaService.actualizarEstado(id, nuevoEstado);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarAsignatura(@PathVariable Long id) {
        asignaturaService.eliminarAsignatura(id);
        return ResponseEntity.noContent().build();
    }

}
