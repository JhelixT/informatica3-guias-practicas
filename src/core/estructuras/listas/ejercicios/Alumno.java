package core.estructuras.listas.ejercicios;

/**
 * Clase que representa un alumno con nombre y legajo.
 * Usado en el Ejercicio 10 de Listas Enlazadas.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Alumno {
    private String nombre;
    private int legajo;
    
    /**
     * Constructor del alumno.
     * 
     * @param nombre El nombre del alumno
     * @param legajo El número de legajo del alumno
     */
    public Alumno(String nombre, int legajo) {
        this.nombre = nombre;
        this.legajo = legajo;
    }
    
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getLegajo() {
        return legajo;
    }
    
    public void setLegajo(int legajo) {
        this.legajo = legajo;
    }
    
    @Override
    public String toString() {
        return String.format("Alumno{legajo=%d, nombre='%s'}", legajo, nombre);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Alumno alumno = (Alumno) obj;
        return legajo == alumno.legajo;
    }
    
    @Override
    public int hashCode() {
        return Integer.hashCode(legajo);
    }
}
