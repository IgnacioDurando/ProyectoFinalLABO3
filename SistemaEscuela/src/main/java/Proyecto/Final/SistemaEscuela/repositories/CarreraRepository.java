package Proyecto.Final.SistemaEscuela.repositories;

import Proyecto.Final.SistemaEscuela.models.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
    Optional<Carrera> findByNombre(String nombre);
}
