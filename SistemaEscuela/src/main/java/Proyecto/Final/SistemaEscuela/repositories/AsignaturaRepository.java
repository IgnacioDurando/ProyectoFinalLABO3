package Proyecto.Final.SistemaEscuela.repositories;

import Proyecto.Final.SistemaEscuela.models.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {
    // Método para buscar asignaturas por estado
    List<Asignatura> findByEstado(String estado);
}
