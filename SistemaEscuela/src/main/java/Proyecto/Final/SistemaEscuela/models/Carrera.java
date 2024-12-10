package Proyecto.Final.SistemaEscuela.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @ManyToMany
    @JoinTable(
            name = "carrera_materia", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "carrera_id"), // Columna de la tabla `Carrera`
            inverseJoinColumns = @JoinColumn(name = "materia_id") // Columna de la tabla `Materia`
    )
    private List<Materia> materias;


    // Constructor vacío
    public Carrera() {}

    // Constructor con parámetros
    public Carrera(String nombre, List<Materia> materias) {
        this.nombre = nombre;
        this.materias = materias;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", materias=" + materias +
                '}';
    }
}
