package core.ejercicios.listas;

import core.estructuras.listas.ejercicios.Alumno;
import core.estructuras.listas.ejercicios.RegistroAlumnos;

/**
 * Ejercicio 10: Aplicación práctica - Registro de alumnos
 * 
 * Crea una lista enlazada que almacene alumnos con nombre y legajo.
 * - Métodos: agregarAlumno(nombre, legajo), buscarAlumno(legajo), eliminarAlumno(legajo).
 * - Simula un registro de tres alumnos y prueba las operaciones.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio10_RegistroAlumnos {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 10: REGISTRO DE ALUMNOS ═══\n");
        
        RegistroAlumnos registro = new RegistroAlumnos();
        
        registro.agregarAlumno("Juan Pérez", 1001);
        registro.agregarAlumno("María García", 1002);
        registro.agregarAlumno("Carlos López", 1003);
        
        System.out.println("Registro completo:");
        registro.mostrarAlumnos();
        
        System.out.println("\nBuscando legajo 1002:");
        Alumno encontrado = registro.buscarAlumno(1002);
        System.out.println(encontrado != null ? encontrado : "No encontrado");
        
        registro.eliminarAlumno(1002);
        
        System.out.println("\nDespués de eliminar 1002:");
        registro.mostrarAlumnos();
        
        System.out.println("\nTotal: " + registro.getCantidadAlumnos() + " alumnos");
    }
}
