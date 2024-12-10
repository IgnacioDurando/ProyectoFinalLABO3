package Proyecto.Final.SistemaEscuela.repositories;

import Proyecto.Final.SistemaEscuela.models.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Optional<Profesor> findByNombreAndApellido(String nombre, String apellido);
}
