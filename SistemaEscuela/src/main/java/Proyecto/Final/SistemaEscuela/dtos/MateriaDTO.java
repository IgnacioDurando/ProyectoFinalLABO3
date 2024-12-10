package Proyecto.Final.SistemaEscuela.dtos;

import java.util.List;

public class MateriaDTO {

    private Long id;
    private String nombre;
    private int anio;
    private int cuatrimestre;
    private List<String> correlatividades;
    private String nombreProfesor;

    // Constructor
    public MateriaDTO(Long id, String nombre, int anio, int cuatrimestre, List<String> correlatividades, String nombreProfesor) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.cuatrimestre = cuatrimestre;
        this.correlatividades = correlatividades;
        this.nombreProfesor = nombreProfesor;
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

    public List<String> getCorrelatividades() {

        return correlatividades;
    }

    public void setCorrelatividades(List<String> correlatividades) {

        this.correlatividades = correlatividades;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }
}
