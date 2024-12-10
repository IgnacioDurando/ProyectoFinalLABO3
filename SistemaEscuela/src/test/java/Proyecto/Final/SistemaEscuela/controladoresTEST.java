package Proyecto.Final.SistemaEscuela;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.ArgumentCaptor;
import Proyecto.Final.SistemaEscuela.controladores.*;
import Proyecto.Final.SistemaEscuela.models.*;
import Proyecto.Final.SistemaEscuela.services.*;
import Proyecto.Final.SistemaEscuela.dtos.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

class ControladoresTest {

    @InjectMocks
    private AlumnoController alumnoController;

    @InjectMocks
    private ProfesorController profesorController;

    @InjectMocks
    private MateriaController materiaController;

    @InjectMocks
    private AsignaturaController asignaturaController;

    @InjectMocks
    private CarreraController carreraController;

    @Mock
    private AlumnoService alumnoService;

    @Mock
    private ProfesorService profesorService;

    @Mock
    private MateriaService materiaService;

    @Mock
    private AsignaturaService asignaturaService;

    @Mock
    private CarreraService carreraService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Pruebas para AlumnoController
    @Test
    void testObtenerTodosLosAlumnos() {
        AlumnoDTO alumno1 = new AlumnoDTO(1L, "Juan", "Perez", "12345678", "Ingeniería");
        AlumnoDTO alumno2 = new AlumnoDTO(2L, "Ana", "Gomez", "87654321", "Medicina");
        when(alumnoService.obtenerTodosComoDTO()).thenReturn(Arrays.asList(alumno1, alumno2));

        ResponseEntity<List<AlumnoDTO>> response = alumnoController.obtenerTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(alumnoService, times(1)).obtenerTodosComoDTO();
    }

    @Test
    void testGuardarAlumno() {
        Alumno alumno = new Alumno("Juan", "Perez", "12345678", null);
        when(alumnoService.guardarAlumno(any(Alumno.class))).thenReturn(alumno);

        ResponseEntity<Alumno> response = alumnoController.guardarAlumno(alumno);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Juan", response.getBody().getNombre());
        verify(alumnoService, times(1)).guardarAlumno(alumno);
    }

    @Test
    void testEliminarAlumno() {
        doNothing().when(alumnoService).eliminarAlumno(1L);

        ResponseEntity<Void> response = alumnoController.eliminarAlumno(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(alumnoService, times(1)).eliminarAlumno(1L);
    }

    // Pruebas para ProfesorController
    @Test
    void testObtenerTodosLosProfesores() {
        Profesor profesor1 = new Profesor("Carlos", "Gomez", null);
        Profesor profesor2 = new Profesor("Ana", "Martinez", null);
        when(profesorService.obtenerTodos()).thenReturn(Arrays.asList(profesor1, profesor2));

        ResponseEntity<List<Profesor>> response = profesorController.obtenerTodos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(profesorService, times(1)).obtenerTodos();
    }

    @Test
    void testGuardarProfesor() {
        Profesor profesor = new Profesor("Carlos", "Gomez", null);
        when(profesorService.guardarProfesor(any(Profesor.class))).thenReturn(profesor);

        ResponseEntity<Profesor> response = profesorController.guardarProfesor(profesor);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Carlos", response.getBody().getNombre());
        verify(profesorService, times(1)).guardarProfesor(profesor);
    }

    // Pruebas para MateriaController
    @Test
    void testObtenerTodasLasMaterias() {
        MateriaDTO materia1 = new MateriaDTO(1L, "Matemáticas", 1, 1, null, "Carlos Gomez");
        MateriaDTO materia2 = new MateriaDTO(2L, "Física", 2, 1, null, "Ana Martinez");
        when(materiaService.obtenerTodasComoDTO()).thenReturn(Arrays.asList(materia1, materia2));

        ResponseEntity<List<MateriaDTO>> response = materiaController.obtenerTodas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(materiaService, times(1)).obtenerTodasComoDTO();
    }

    @Test
    void testGuardarMateria() {
        Materia materia = new Materia("Programación", 1, 1, null, null);
        when(materiaService.guardarMateria(any(Materia.class))).thenReturn(materia);

        ResponseEntity<Materia> response = materiaController.guardarMateria(materia);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Programación", response.getBody().getNombre());
        verify(materiaService, times(1)).guardarMateria(materia);
    }

    // Pruebas para AsignaturaController
    @Test
    void testObtenerTodasLasAsignaturas() {
        AsignaturaDTO asignatura1 = new AsignaturaDTO(1L, "Matemáticas", "CURSANDO", null);
        AsignaturaDTO asignatura2 = new AsignaturaDTO(2L, "Física", "APROBADA", null);
        when(asignaturaService.obtenerTodasComoDTO()).thenReturn(Arrays.asList(asignatura1, asignatura2));

        ResponseEntity<List<AsignaturaDTO>> response = asignaturaController.obtenerTodas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(asignaturaService, times(1)).obtenerTodasComoDTO();
    }

    @Test
    void testCambiarEstadoAsignatura() {
        Asignatura asignatura = new Asignatura();
        asignatura.setId(1L);
        asignatura.setEstado(EstadoAsignatura.CURSANDO);

        when(asignaturaService.actualizarEstado(eq(1L), eq(EstadoAsignatura.APROBADA)))
                .thenReturn(asignatura);

        ResponseEntity<Void> response = asignaturaController.cambiarEstado(1L, Map.of("estado", "APROBADA"));

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(asignaturaService, times(1)).actualizarEstado(1L, EstadoAsignatura.APROBADA);
    }

    // Pruebas para CarreraController
    @Test
    void testObtenerTodasLasCarreras() {
        Carrera carrera1 = new Carrera("Ingeniería", null);
        Carrera carrera2 = new Carrera("Medicina", null);
        when(carreraService.obtenerTodas()).thenReturn(Arrays.asList(carrera1, carrera2));

        ResponseEntity<List<Carrera>> response = carreraController.obtenerTodas();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        verify(carreraService, times(1)).obtenerTodas();
    }

    @Test
    void testGuardarCarrera() {
        Carrera carrera = new Carrera("Ingeniería", new ArrayList<>());

        // Capturador de argumentos
        ArgumentCaptor<Carrera> captor = ArgumentCaptor.forClass(Carrera.class);

        when(carreraService.guardarCarrera(any(Carrera.class))).thenReturn(carrera);

        ResponseEntity<Carrera> response = carreraController.guardarCarrera(carrera);

        // Validaciones de la respuesta
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Ingeniería", response.getBody().getNombre());

        // Validamos que el servicio fue invocado con un objeto similar
        verify(carreraService).guardarCarrera(captor.capture());
        Carrera capturada = captor.getValue();
        assertEquals("Ingeniería", capturada.getNombre());
        assertNotNull(capturada.getMaterias());
    }

}