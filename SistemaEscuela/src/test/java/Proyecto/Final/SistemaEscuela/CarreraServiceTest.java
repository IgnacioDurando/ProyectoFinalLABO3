package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.Carrera;
import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.repositories.CarreraRepository;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import Proyecto.Final.SistemaEscuela.services.CarreraService;
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

class CarreraServiceTest {

    @InjectMocks
    private CarreraService carreraService;

    @Mock
    private CarreraRepository carreraRepository;
    @Mock
    private MateriaRepository materiaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGuardarCarrera() {
        Carrera carrera = new Carrera();
        carrera.setNombre("Ingeniería en Sistemas");
        carrera.setMaterias(new ArrayList<>());

        Carrera carreraGuardada = new Carrera();
        carreraGuardada.setId(1L);
        carreraGuardada.setNombre("Ingeniería en Sistemas");
        carreraGuardada.setMaterias(new ArrayList<>());

        when(carreraRepository.findByNombre("Ingeniería en Sistemas"))
                .thenReturn(Optional.empty());
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carreraGuardada);

        Carrera resultado = carreraService.guardarCarrera(carrera);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Ingeniería en Sistemas", resultado.getNombre());
        verify(carreraRepository, times(1)).save(any(Carrera.class)); // Relaja la comparación
    }




    @Test
    void testGuardarCarreraDuplicada() {
        Carrera carrera = new Carrera();
        carrera.setNombre("Ingeniería en Sistemas");

        when(carreraRepository.findByNombre("Ingeniería en Sistemas"))
                .thenReturn(Optional.of(carrera));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            carreraService.guardarCarrera(carrera);
        });

        assertEquals("Ya existe una carrera con el nombre: Ingeniería en Sistemas", exception.getMessage());
        verify(carreraRepository, never()).save(any(Carrera.class));
    }

    @Test
    void testActualizarCarreraConMaterias() {
        Carrera carreraExistente = new Carrera();
        carreraExistente.setId(1L);
        carreraExistente.setNombre("Ingeniería en Sistemas");
        carreraExistente.setMaterias(new ArrayList<>()); // Lista inicial de materias

        Carrera carreraActualizada = new Carrera();
        carreraActualizada.setNombre("Ingeniería Industrial");

        Materia nuevaMateria = new Materia();
        nuevaMateria.setId(1L);
        nuevaMateria.setNombre("Matemáticas");
        carreraActualizada.setMaterias(List.of(nuevaMateria));

        when(carreraRepository.findById(1L)).thenReturn(Optional.of(carreraExistente));
        when(materiaRepository.findById(1L)).thenReturn(Optional.of(nuevaMateria));
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carreraExistente);

        Carrera resultado = carreraService.actualizarCarrera(1L, carreraActualizada);

        assertNotNull(resultado);
        assertEquals("Ingeniería Industrial", resultado.getNombre());
        assertEquals(1, resultado.getMaterias().size());
        assertEquals("Matemáticas", resultado.getMaterias().get(0).getNombre());
        verify(carreraRepository, times(1)).findById(1L);
        verify(carreraRepository, times(1)).save(carreraExistente);
    }


    @Test
    void testActualizarCarrera() {
        Carrera carreraExistente = new Carrera();
        carreraExistente.setId(1L);
        carreraExistente.setNombre("Ingeniería en Sistemas");

        Carrera carreraActualizada = new Carrera();
        carreraActualizada.setNombre("Ingeniería Industrial");

        when(carreraRepository.findById(1L)).thenReturn(Optional.of(carreraExistente));
        when(carreraRepository.save(any(Carrera.class))).thenReturn(carreraExistente);

        Carrera resultado = carreraService.actualizarCarrera(1L, carreraActualizada);

        assertNotNull(resultado);
        assertEquals("Ingeniería Industrial", resultado.getNombre());
        verify(carreraRepository, times(1)).findById(1L);
        verify(carreraRepository, times(1)).save(carreraExistente);
    }

    @Test
    void testEliminarCarrera() {
        when(carreraRepository.existsById(1L)).thenReturn(true);
        doNothing().when(carreraRepository).deleteById(1L);

        assertDoesNotThrow(() -> carreraService.eliminarCarrera(1L));
        verify(carreraRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerPorId() {
        Carrera carrera = new Carrera();
        carrera.setId(1L);
        carrera.setNombre("Ingeniería en Sistemas");

        when(carreraRepository.findById(1L)).thenReturn(Optional.of(carrera));

        Optional<Carrera> resultado = carreraService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Ingeniería en Sistemas", resultado.get().getNombre());
        verify(carreraRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTodas() {
        Carrera carrera1 = new Carrera();
        carrera1.setId(1L);
        carrera1.setNombre("Ingeniería en Sistemas");

        Carrera carrera2 = new Carrera();
        carrera2.setId(2L);
        carrera2.setNombre("Ingeniería Industrial");

        List<Carrera> carreras = Arrays.asList(carrera1, carrera2);

        when(carreraRepository.findAll()).thenReturn(carreras);

        List<Carrera> resultado = carreraService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Ingeniería en Sistemas", resultado.get(0).getNombre());
        assertEquals("Ingeniería Industrial", resultado.get(1).getNombre());
        verify(carreraRepository, times(1)).findAll();
    }
}
