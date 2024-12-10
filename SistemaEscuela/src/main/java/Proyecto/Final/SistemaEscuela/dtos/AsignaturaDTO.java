package Proyecto.Final.SistemaEscuela.dtos;

public class AsignaturaDTO {

    private Long id;
    private String nombre;
    private String estado;
    private AlumnoDTO alumno;

    // Constructor, getters y setters


    public AsignaturaDTO() {
    }

    public AsignaturaDTO(Long id, String nombre, String estado, AlumnoDTO alumno) {
        this.id = id;
        this.nombre = nombre;
        this.estado = estado;
        this.alumno = alumno;
    }

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public AlumnoDTO getAlumno() {
        return alumno;
    }

    public void setAlumno(AlumnoDTO alumno) {
        this.alumno = alumno;
    }

    @Override
    public String toString() {
        return "AsignaturaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado='" + estado + '\'' +
                ", alumno=" + alumno +
                '}';
    }
}
