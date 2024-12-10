package Proyecto.Final.SistemaEscuela.repositories;

import Proyecto.Final.SistemaEscuela.models.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

    Optional<Alumno> findByNombreAndApellido(String nombre, String apellido);
}
