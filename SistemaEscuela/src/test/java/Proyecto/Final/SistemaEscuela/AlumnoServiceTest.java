package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.Alumno;
import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.repositories.AlumnoRepository;
import Proyecto.Final.SistemaEscuela.repositories.CarreraRepository;
import Proyecto.Final.SistemaEscuela.services.AlumnoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AlumnoServiceTest {

    @InjectMocks
    private AlumnoService alumnoService;

    @Mock
    private AlumnoRepository alumnoRepository;
    @Mock
    private CarreraRepository carreraRepository;

    public AlumnoServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarAlumno() {
        // Crear una carrera de ejemplo
        Carrera carrera = new Carrera();
        carrera.setId(1L);
        carrera.setNombre("Ingeniería");

        when(carreraRepository.findById(1L)).thenReturn(Optional.of(carrera));

        // Crear un alumno con la carrera asignada
        Alumno alumno = new Alumno("Juan", "Perez", "12345678", null);
        alumno.setCarrera(carrera);

        // Configurar el mock del alumnoRepository para guardar el alumno
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumno);

        // Ejecutar el método del servicio
        Alumno resultado = alumnoService.guardarAlumno(alumno);

        // Validar los resultados
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Perez", resultado.getApellido());
        assertEquals("Ingeniería", resultado.getCarrera().getNombre());
    }


    @Test
    void testActualizarAlumno() {
        Alumno alumnoExistente = new Alumno("Juan", "Perez", "12345678", null);
        Alumno alumnoActualizado = new Alumno("Juan", "Lopez", "12345678", null);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumnoExistente));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(alumnoActualizado);

        Alumno resultado = alumnoService.actualizarAlumno(1L, alumnoActualizado);

        assertNotNull(resultado);
        assertEquals("Lopez", resultado.getApellido());
    }

    @Test
    void testEliminarAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", "12345678", null);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        doNothing().when(alumnoRepository).deleteById(1L);

        assertDoesNotThrow(() -> alumnoService.eliminarAlumno(1L));
        verify(alumnoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerPorId() {
        Alumno alumno = new Alumno("Juan", "Perez", "12345678", null);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));

        Optional<Alumno> resultado = alumnoService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Juan", resultado.get().getNombre());
    }

    @Test
    void testObtenerTodos() {
        Alumno alumno1 = new Alumno("Juan", "Perez", "12345678", null);
        Alumno alumno2 = new Alumno("Ana", "Gomez", "87654321", null);

        when(alumnoRepository.findAll()).thenReturn(Arrays.asList(alumno1, alumno2));

        var resultado = alumnoService.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
    }
}
