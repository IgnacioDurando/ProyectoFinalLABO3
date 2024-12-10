package Proyecto.Final.SistemaEscuela;

import Proyecto.Final.SistemaEscuela.models.Materia;
import Proyecto.Final.SistemaEscuela.repositories.MateriaRepository;
import Proyecto.Final.SistemaEscuela.services.MateriaService;
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
class MateriaServiceTest {

    @InjectMocks
    private MateriaService materiaService;

    @Mock
    private MateriaRepository materiaRepository;

    @Test
    void testGuardarMateria() {
        Materia materia = new Materia();
        materia.setNombre("Programación");
        when(materiaRepository.save(any(Materia.class))).thenReturn(materia);

        Materia resultado = materiaService.guardarMateria(materia);

        assertNotNull(resultado);
        assertEquals("Programación", resultado.getNombre());
        verify(materiaRepository, times(1)).save(materia);
    }

    @Test
    void testActualizarMateria() {
        Materia materiaExistente = new Materia();
        materiaExistente.setId(1L);
        materiaExistente.setNombre("Programación");

        Materia materiaActualizada = new Materia();
        materiaActualizada.setNombre("Estructura de Datos");

        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materiaExistente));
        when(materiaRepository.save(any(Materia.class))).thenReturn(materiaExistente);

        Materia resultado = materiaService.actualizarMateria(1L, materiaActualizada);

        assertNotNull(resultado);
        assertEquals("Estructura de Datos", resultado.getNombre());
        verify(materiaRepository, times(1)).findById(1L);
        verify(materiaRepository, times(1)).save(materiaExistente);
    }

    @Test
    void testEliminarMateria() {
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setNombre("Programación");

        when(materiaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(materiaRepository).deleteById(1L);

        assertDoesNotThrow(() -> materiaService.eliminarMateria(1L));
        verify(materiaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testObtenerPorId() {
        Materia materia = new Materia();
        materia.setId(1L);
        materia.setNombre("Programación");

        when(materiaRepository.findById(1L)).thenReturn(Optional.of(materia));

        Optional<Materia> resultado = materiaService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Programación", resultado.get().getNombre());
        verify(materiaRepository, times(1)).findById(1L);
    }

    @Test
    void testObtenerTodasLasMaterias() {
        Materia materia1 = new Materia();
        materia1.setNombre("Programación");

        Materia materia2 = new Materia();
        materia2.setNombre("Estructura de Datos");

        List<Materia> listaMaterias = Arrays.asList(materia1, materia2);

        when(materiaRepository.findAll()).thenReturn(listaMaterias);

        List<Materia> resultado = materiaService.obtenerTodas();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Programación", resultado.get(0).getNombre());
        assertEquals("Estructura de Datos", resultado.get(1).getNombre());
        verify(materiaRepository, times(1)).findAll();
    }
}
