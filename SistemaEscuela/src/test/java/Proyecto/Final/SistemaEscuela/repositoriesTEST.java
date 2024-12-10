package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.*;
import Proyecto.Final.SistemaEscuela.repositories.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RepositoryTests {

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private MateriaRepository materiaRepository;

    @Mock
    private ProfesorRepository profesorRepository;

    @Mock
    private CarreraRepository carreraRepository;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @Test
    void testFindAlumnoByNombreAndApellido() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");
        alumno.setApellido("Perez");

        when(alumnoRepository.findByNombreAndApellido("Juan", "Perez"))
                .thenReturn(Optional.of(alumno));

        Optional<Alumno> result = alumnoRepository.findByNombreAndApellido("Juan", "Perez");

        assertTrue(result.isPresent());
        assertEquals("Juan", result.get().getNombre());
        assertEquals("Perez", result.get().getApellido());
    }

    @Test
    void testFindMateriaByNombre() {
        Materia materia = new Materia();
        materia.setNombre("Matemáticas");

        when(materiaRepository.findByNombre("Matemáticas"))
                .thenReturn(Optional.of(materia));

        Optional<Materia> result = materiaRepository.findByNombre("Matemáticas");

        assertTrue(result.isPresent());
        assertEquals("Matemáticas", result.get().getNombre());
    }

    @Test
    void testFindProfesorByNombreAndApellido() {
        Profesor profesor = new Profesor();
        profesor.setNombre("Carlos");
        profesor.setApellido("Gomez");

        when(profesorRepository.findByNombreAndApellido("Carlos", "Gomez"))
                .thenReturn(Optional.of(profesor));

        Optional<Profesor> result = profesorRepository.findByNombreAndApellido("Carlos", "Gomez");

        assertTrue(result.isPresent());
        assertEquals("Carlos", result.get().getNombre());
        assertEquals("Gomez", result.get().getApellido());
    }

    @Test
    void testFindCarreraByNombre() {
        Carrera carrera = new Carrera();
        carrera.setNombre("Ingeniería");

        when(carreraRepository.findByNombre("Ingeniería"))
                .thenReturn(Optional.of(carrera));

        Optional<Carrera> result = carreraRepository.findByNombre("Ingeniería");

        assertTrue(result.isPresent());
        assertEquals("Ingeniería", result.get().getNombre());
    }

    @Test
    void testFindAsignaturaByEstado() {
        Asignatura asignatura = new Asignatura();
        asignatura.setEstado(EstadoAsignatura.CURSANDO);

        when(asignaturaRepository.findByEstado("CURSANDO"))
                .thenReturn(List.of(asignatura));

        List<Asignatura> result = asignaturaRepository.findByEstado("CURSANDO");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(EstadoAsignatura.CURSANDO, result.get(0).getEstado());
    }

    @Test
    void testSaveAlumno() {
        Alumno alumno = new Alumno();
        alumno.setNombre("Juan");

        when(alumnoRepository.save(alumno)).thenReturn(alumno);

        Alumno result = alumnoRepository.save(alumno);

        assertNotNull(result);
        assertEquals("Juan", result.getNombre());
    }

    @Test
    void testSaveMateria() {
        Materia materia = new Materia();
        materia.setNombre("Física");

        when(materiaRepository.save(materia)).thenReturn(materia);

        Materia result = materiaRepository.save(materia);

        assertNotNull(result);
        assertEquals("Física", result.getNombre());
    }

    @Test
    void testSaveProfesor() {
        Profesor profesor = new Profesor();
        profesor.setNombre("Ana");

        when(profesorRepository.save(profesor)).thenReturn(profesor);

        Profesor result = profesorRepository.save(profesor);

        assertNotNull(result);
        assertEquals("Ana", result.getNombre());
    }

    @Test
    void testSaveCarrera() {
        Carrera carrera = new Carrera();
        carrera.setNombre("Medicina");

        when(carreraRepository.save(carrera)).thenReturn(carrera);

        Carrera result = carreraRepository.save(carrera);

        assertNotNull(result);
        assertEquals("Medicina", result.getNombre());
    }

    @Test
    void testSaveAsignatura() {
        Asignatura asignatura = new Asignatura();
        asignatura.setNombre("Química");

        when(asignaturaRepository.save(asignatura)).thenReturn(asignatura);

        Asignatura result = asignaturaRepository.save(asignatura);

        assertNotNull(result);
        assertEquals("Química", result.getNombre());
    }
}
