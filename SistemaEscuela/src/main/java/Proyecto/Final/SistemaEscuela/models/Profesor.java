package Proyecto.Final.SistemaEscuela.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Profesor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;

    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Materia> materias;

    // Constructor default
    public Profesor() {}

    // Constructor
    public Profesor(String nombre, String apellido, List<Materia> materias) {
        this.nombre = nombre;
        this.apellido = apellido;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<Materia> getMaterias() {
        return materias;
    }

    public void setMaterias(List<Materia> materias) {
        this.materias = materias;
    }


    @Override
    public String toString() {
        return "Profesor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", materias=" + materias +
                '}';
    }
}
