package Proyecto.Final.SistemaEscuela.dtos;

import java.util.List;

public class AlumnoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
    private String carrera;
    private List<String> asignaturas;

    // Constructor


    public AlumnoDTO() {
    }

    public AlumnoDTO(Long id, String nombre, String apellido, String dni, String carrera) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.carrera = carrera;
    }

    public AlumnoDTO(Long id, String nombre, String apellido, String dni, String carrera, List<String> asignaturas) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.carrera = carrera;
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

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public List<String> getAsignaturas() {
        return asignaturas;
    }

    public void setAsignaturas(List<String> asignaturas) {
        this.asignaturas = asignaturas;
    }
}
