package Proyecto.Final.SistemaEscuela.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class Asignatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private EstadoAsignatura estado; // ENUM: Cursando, Aprobada, Regularidad Perdida

    private String nombre;
    @ManyToOne
    @JoinColumn(name = "alumno_id")
    @JsonBackReference
    private Alumno alumno;

    @ManyToOne
    @JoinColumn(name = "materia_id")
    private Materia materia;

    // Constructor por defecto
    public Asignatura() {}

    // Constructor con parámetros
    public Asignatura(EstadoAsignatura estado, Alumno alumno, Materia materia) {
        this.estado = estado;
        this.alumno = alumno;
        this.materia = materia;
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

    public EstadoAsignatura getEstado() {

        return estado;
    }

    public void setEstado(EstadoAsignatura estado) {

        this.estado = estado;
    }

    public Alumno getAlumno() {

        return alumno;
    }

    public void setAlumno(Alumno alumno) {

        this.alumno = alumno;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Asignatura{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", alumno=" + alumno +
                '}';
    }
}
