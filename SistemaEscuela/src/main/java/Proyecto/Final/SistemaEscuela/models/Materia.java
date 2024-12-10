package Proyecto.Final.SistemaEscuela.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Materia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private int anio;
    private int cuatrimestre;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    @JsonBackReference
    private Profesor profesor;

    @ElementCollection
    private List<Materia> correlatividades; // IDs de materias correlativas

    // Constructor default
    public Materia() {}

    // Constructor
    public Materia(String nombre, int anio, int cuatrimestre, Profesor profesor, List<Materia> correlatividades) {
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.profesor = profesor;
        this.correlatividades = correlatividades;
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

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public int getCuatrimestre() {
        return cuatrimestre;
    }

    public void setCuatrimestre(int cuatrimestre) {
        this.cuatrimestre = cuatrimestre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public List<Materia> getCorrelatividades() {
        return correlatividades;
    }

    public void setCorrelatividades(List<Materia> correlatividades) {
        this.correlatividades = correlatividades;
    }

    @Override
    public String toString() {
        return "Materia{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anio=" + anio +
                ", cuatrimestre=" + cuatrimestre +
                ", profesor=" + profesor +
                ", correlatividades=" + correlatividades +
                '}';
    }
}
