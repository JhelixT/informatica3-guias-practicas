package core.estructuras.listas.ejercicios;

import core.estructuras.listas.ListaEnlazada;

/**
 * Sistema de registro de alumnos usando Lista Enlazada.
 * Ejercicio 10: Aplicación práctica de listas enlazadas.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class RegistroAlumnos {
    private ListaEnlazada<Alumno> alumnos;
    
    /**
     * Constructor que inicializa el registro vacío.
     */
    public RegistroAlumnos() {
        this.alumnos = new ListaEnlazada<>();
    }
    
    /**
     * Agrega un nuevo alumno al registro.
     * 
     * @param nombre El nombre del alumno
     * @param legajo El número de legajo del alumno
     * @return true si se agregó correctamente, false si el legajo ya existe
     */
    public boolean agregarAlumno(String nombre, int legajo) {
        // Verificar si el legajo ya existe
        if (buscarAlumno(legajo) != null) {
            return false;
        }
        
        Alumno nuevoAlumno = new Alumno(nombre, legajo);
        alumnos.insertLast(nuevoAlumno);
        return true;
    }
    
    /**
     * Busca un alumno por su número de legajo.
     * 
     * @param legajo El número de legajo a buscar
     * @return El alumno encontrado, o null si no existe
     */
    public Alumno buscarAlumno(int legajo) {
        for (int i = 0; i < alumnos.getSize(); i++) {
            Alumno alumno = alumnos.getAt(i);
            if (alumno.getLegajo() == legajo) {
                return alumno;
            }
        }
        return null;
    }
    
    /**
     * Elimina un alumno del registro por su legajo.
     * 
     * @param legajo El número de legajo del alumno a eliminar
     * @return true si se eliminó correctamente, false si no se encontró
     */
    public boolean eliminarAlumno(int legajo) {
        Alumno alumnoAEliminar = buscarAlumno(legajo);
        if (alumnoAEliminar == null) {
            return false;
        }
        
        return alumnos.remove(alumnoAEliminar);
    }
    
    /**
     * Modifica el nombre de un alumno.
     * 
     * @param legajo El legajo del alumno a modificar
     * @param nuevoNombre El nuevo nombre
     * @return true si se modificó correctamente, false si no se encontró
     */
    public boolean modificarAlumno(int legajo, String nuevoNombre) {
        Alumno alumno = buscarAlumno(legajo);
        if (alumno == null) {
            return false;
        }
        
        alumno.setNombre(nuevoNombre);
        return true;
    }
    
    /**
     * Obtiene la cantidad de alumnos en el registro.
     * 
     * @return La cantidad de alumnos
     */
    public int getCantidadAlumnos() {
        return alumnos.getSize();
    }
    
    /**
     * Verifica si el registro está vacío.
     * 
     * @return true si no hay alumnos, false en caso contrario
     */
    public boolean estaVacio() {
        return alumnos.isEmpty();
    }
    
    /**
     * Muestra todos los alumnos del registro.
     */
    public void mostrarAlumnos() {
        if (alumnos.isEmpty()) {
            System.out.println("No hay alumnos registrados.");
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║         LISTADO DE ALUMNOS REGISTRADOS           ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        
        for (int i = 0; i < alumnos.getSize(); i++) {
            Alumno alumno = alumnos.getAt(i);
            System.out.printf("║ Legajo: %-6d | Nombre: %-25s║%n", 
                              alumno.getLegajo(), alumno.getNombre());
        }
        
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.printf("Total de alumnos: %d%n", alumnos.getSize());
    }
    
    /**
     * Limpia todos los alumnos del registro.
     */
    public void limpiarRegistro() {
        alumnos.clear();
    }
    
    @Override
    public String toString() {
        return String.format("Registro de Alumnos [%d alumnos]", alumnos.getSize());
    }
}
