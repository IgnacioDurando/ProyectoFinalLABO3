package Proyecto.Final.SistemaEscuela.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String dni;

    @ManyToOne
    @JoinColumn(name = "carrera_id") // Relación con Carrera
    private Carrera carrera;
    @OneToMany(mappedBy = "alumno", cascade = CascadeType.ALL)
    @JsonManagedReference
    @JsonIgnore
    private List<Asignatura> asignaturas;

    // Constructor default
    public Alumno() {}

    // Constructor
    public Alumno(String nombre, String apellido, String dni, List<Asignatura> asignaturas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.asignaturas = asignaturas;
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

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public List<Asignatura> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<Asignatura> asignaturas) {
        this.asignaturas = asignaturas;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", dni='" + dni + '\'' +
                ", asignaturas=" + asignaturas +
                '}';
    }
}
