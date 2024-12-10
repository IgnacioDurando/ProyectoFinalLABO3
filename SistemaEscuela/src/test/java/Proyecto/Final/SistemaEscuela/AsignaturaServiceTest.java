package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.*;
import Proyecto.Final.SistemaEscuela.repositories.AlumnoRepository;
import Proyecto.Final.SistemaEscuela.repositories.AsignaturaRepository;
import Proyecto.Final.SistemaEscuela.repositories.CarreraRepository;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import Proyecto.Final.SistemaEscuela.services.AsignaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AsignaturaServiceTest {

    @InjectMocks
    private AsignaturaService asignaturaService;

    @Mock
    private AsignaturaRepository asignaturaRepository;

    @Mock
    private AlumnoRepository alumnoRepository;

    @Mock
    private MateriaRepository materiaRepository;
    @Mock
    private CarreraRepository carreraRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarAsignatura() {
        Asignatura asignatura = new Asignatura();
        asignatura.setNombre("Matemáticas");
        asignatura.setEstado(EstadoAsignatura.CURSANDO);

        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);

        Asignatura resultado = asignaturaService.guardarAsignatura(asignatura);

        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        assertEquals(EstadoAsignatura.CURSANDO, resultado.getEstado());
        verify(asignaturaRepository, times(1)).save(asignatura);
    }

    @Test
    void testActualizarEstado() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1L);
        asignatura.setNombre("Matemáticas");
        asignatura.setEstado(EstadoAsignatura.CURSANDO);

        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));
        when(asignaturaRepository.save(any(Asignatura.class))).thenReturn(asignatura);

        Asignatura resultado = asignaturaService.actualizarEstado(1L, EstadoAsignatura.APROBADA);

        assertNotNull(resultado);
        assertEquals(EstadoAsignatura.APROBADA, resultado.getEstado());
        verify(asignaturaRepository, times(1)).findById(1L);
        verify(asignaturaRepository, times(1)).save(asignatura);
    }

    @Test
    void testEliminarAsignatura() {
        when(asignaturaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(asignaturaRepository).deleteById(1L);

        assertDoesNotThrow(() -> asignaturaService.eliminarAsignatura(1L));
        verify(asignaturaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerPorId() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1L);
        asignatura.setNombre("Matemáticas");

        when(asignaturaRepository.findById(1L)).thenReturn(Optional.of(asignatura));

        Optional<Asignatura> resultado = asignaturaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Matemáticas", resultado.get().getNombre());
        verify(asignaturaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTodas() {
        Asignatura asignatura1 = new Asignatura();
        asignatura1.setNombre("Matemáticas");

        Asignatura asignatura2 = new Asignatura();
        asignatura2.setNombre("Física");

        List<Asignatura> asignaturas = Arrays.asList(asignatura1, asignatura2);

        when(asignaturaRepository.findAll()).thenReturn(asignaturas);

        List<Asignatura> resultado = asignaturaService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Matemáticas", resultado.get(0).getNombre());
        assertEquals("Física", resultado.get(1).getNombre());
        verify(asignaturaRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorEstado() {
        Asignatura asignatura = new Asignatura();
        asignatura.setNombre("Matemáticas");
        asignatura.setEstado(EstadoAsignatura.APROBADA);

        when(asignaturaRepository.findByEstado("APROBADA")).thenReturn(List.of(asignatura));

        List<Asignatura> resultado = asignaturaService.obtenerPorEstado("APROBADA");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Matemáticas", resultado.get(0).getNombre());
        assertEquals(EstadoAsignatura.APROBADA, resultado.get(0).getEstado());
        verify(asignaturaRepository, times(1)).findByEstado("APROBADA");
    }

    @Test
    void testAgregarAsignaturaAAlumno() {
        Carrera carrera = new Carrera();
        carrera.setId(1L);
        carrera.setNombre("Ingeniería");
        carrera.setMaterias(new ArrayList<>());

        Materia materia = new Materia();
        materia.setId(1L);
        materia.setNombre("Matemáticas");

        carrera.getMaterias().add(materia);

        Alumno alumno = new Alumno();
        alumno.setId(1L);
        alumno.setNombre("Juan Perez");
        alumno.setCarrera(carrera);

        Asignatura asignatura = new Asignatura();
        asignatura.setNombre("Matemáticas");
        asignatura.setEstado(EstadoAsignatura.CURSANDO);
        asignatura.setAlumno(alumno);
        asignatura.setMateria(materia);

        when(alumnoRepository.findById(1L)).thenReturn(Optional.of(alumno));
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));
        when(asignaturaRepository.save(any(Asignatura.class))).thenAnswer(invocation -> invocation.getArgument(0));


        Asignatura resultado = asignaturaService.agregarAsignaturaAAlumno(1L, 1L, EstadoAsignatura.CURSANDO);


        assertNotNull(resultado);
        assertEquals("Matemáticas", resultado.getNombre());
        assertEquals(EstadoAsignatura.CURSANDO, resultado.getEstado());
        assertEquals(alumno, resultado.getAlumno());
        assertEquals(materia, resultado.getMateria());

        verify(alumnoRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).findById(1L);
        verify(asignaturaRepository, times(1)).save(any(Asignatura.class));
    }

}
