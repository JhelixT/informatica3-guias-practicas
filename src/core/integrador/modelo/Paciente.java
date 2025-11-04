package core.integrador.modelo;

public class Paciente {
    private String dni;
    private String nombre;

    public Paciente(String dni, String nombre) {
        this.dni = dni;
        this.nombre = nombre;
    }

    public String getDni() { return dni; }
    public String getNombre() { return nombre; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Paciente)) return false;
        Paciente p = (Paciente) o;
        return dni.equals(p.dni);
    }

    @Override
    public int hashCode() {
        return dni.hashCode();
    }
}
