package Proyecto.Final.SistemaEscuela.repositories;

import Proyecto.Final.SistemaEscuela.models.Materia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MateriaRepository extends JpaRepository<Materia, Long> {

    // Método para encontrar materias por profesor y ordenarlas alfabéticamente
    List<Materia> findByProfesorIdOrderByNombreAsc(Long profesorId);

    // Método para buscar una materia por su nombre
    Optional<Materia> findByNombre(String nombre);

}
