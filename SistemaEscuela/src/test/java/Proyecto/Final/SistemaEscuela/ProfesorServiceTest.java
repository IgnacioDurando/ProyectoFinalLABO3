package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.Profesor;
import Proyecto.Final.SistemaEscuela.repositories.ProfesorRepository;
import Proyecto.Final.SistemaEscuela.services.ProfesorService;
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
class ProfesorServiceTest {

    @InjectMocks
    private ProfesorService profesorService;

    @Mock
    private ProfesorRepository profesorRepository;

    @Test
    void testGuardarProfesor() {
        Profesor profesor = new Profesor();
        profesor.setNombre("Carlos");
        profesor.setApellido("Gomez");

        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesor);

        Profesor resultado = profesorService.guardarProfesor(profesor);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Gomez", resultado.getApellido());
        verify(profesorRepository, times(1)).save(profesor);
    }

    @Test
    void testActualizarProfesor() {
        Profesor profesorExistente = new Profesor();
        profesorExistente.setId(1L);
        profesorExistente.setNombre("Carlos");
        profesorExistente.setApellido("Gomez");

        Profesor profesorActualizado = new Profesor();
        profesorActualizado.setNombre("Carlos");
        profesorActualizado.setApellido("Martínez");

        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesorExistente));
        when(profesorRepository.save(any(Profesor.class))).thenReturn(profesorExistente);

        Profesor resultado = profesorService.actualizarProfesor(1L, profesorActualizado);

        assertNotNull(resultado);
        assertEquals("Carlos", resultado.getNombre());
        assertEquals("Martínez", resultado.getApellido());
        verify(profesorRepository, times(1)).findById(1L);
        verify(profesorRepository, times(1)).save(profesorExistente);
    }

    @Test
    void testEliminarProfesor() {
        Profesor profesor = new Profesor();
        profesor.setId(1L);

        doNothing().when(profesorRepository).deleteById(1L);

        assertDoesNotThrow(() -> profesorService.eliminarProfesor(1L));

        verify(profesorRepository, times(1)).deleteById(1L);
    }



    @Test
    void testObtenerPorId() {
        Profesor profesor = new Profesor();
        profesor.setId(1L);
        profesor.setNombre("Carlos");
        profesor.setApellido("Gomez");

        when(profesorRepository.findById(1L)).thenReturn(Optional.of(profesor));

        Optional<Profesor> resultado = profesorService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Carlos", resultado.get().getNombre());
        assertEquals("Gomez", resultado.get().getApellido());
        verify(profesorRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTodosLosProfesores() {
        Profesor profesor1 = new Profesor();
        profesor1.setNombre("Carlos");
        profesor1.setApellido("Gomez");

        Profesor profesor2 = new Profesor();
        profesor2.setNombre("Ana");
        profesor2.setApellido("Fernandez");

        List<Profesor> profesores = Arrays.asList(profesor1, profesor2);

        when(profesorRepository.findAll()).thenReturn(profesores);

        List<Profesor> resultado = profesorService.obtenerTodos();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
        assertEquals("Ana", resultado.get(1).getNombre());
        verify(profesorRepository, times(1)).findAll();
    }
}
