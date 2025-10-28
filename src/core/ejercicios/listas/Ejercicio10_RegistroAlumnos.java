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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║       EJERCICIO 10: REGISTRO DE ALUMNOS (APLICACIÓN)     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Implementar un sistema de registro de alumnos\n");
        
        // Crear el registro
        RegistroAlumnos registro = new RegistroAlumnos();
        
        System.out.println("Estado inicial:");
        System.out.println("   " + registro);
        
        // Agregar alumnos
        System.out.println("\n➕ AGREGANDO ALUMNOS:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        System.out.println("\n   Agregando: Juan Pérez (Legajo: 1001)");
        boolean agregado1 = registro.agregarAlumno("Juan Pérez", 1001);
        System.out.println("   Resultado: " + (agregado1 ? "✓ Agregado" : "✗ Error"));
        
        System.out.println("\n   Agregando: María García (Legajo: 1002)");
        boolean agregado2 = registro.agregarAlumno("María García", 1002);
        System.out.println("   Resultado: " + (agregado2 ? "✓ Agregado" : "✗ Error"));
        
        System.out.println("\n   Agregando: Carlos López (Legajo: 1003)");
        boolean agregado3 = registro.agregarAlumno("Carlos López", 1003);
        System.out.println("   Resultado: " + (agregado3 ? "✓ Agregado" : "✗ Error"));
        
        System.out.println("\n   Intentando agregar legajo duplicado (1002)...");
        boolean agregado4 = registro.agregarAlumno("Pedro Martínez", 1002);
        System.out.println("   Resultado: " + (agregado4 ? "✗ No debió agregarse" : "✓ Duplicado rechazado"));
        
        // Mostrar todos los alumnos
        System.out.println("\n📋 LISTADO COMPLETO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        registro.mostrarAlumnos();
        
        // Buscar alumnos
        System.out.println("\n🔍 BÚSQUEDA DE ALUMNOS:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        System.out.println("\n   Buscando legajo 1002...");
        Alumno encontrado1 = registro.buscarAlumno(1002);
        if (encontrado1 != null) {
            System.out.println("   ✓ Encontrado: " + encontrado1);
        } else {
            System.out.println("   ✗ No encontrado");
        }
        
        System.out.println("\n   Buscando legajo 9999 (no existe)...");
        Alumno encontrado2 = registro.buscarAlumno(9999);
        if (encontrado2 != null) {
            System.out.println("   ✗ No debió encontrarse");
        } else {
            System.out.println("   ✓ No encontrado (correcto)");
        }
        
        // Modificar alumno
        System.out.println("\n✏️  MODIFICACIÓN DE ALUMNO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        System.out.println("\n   Modificando nombre del alumno con legajo 1001...");
        System.out.println("   Nombre anterior: " + registro.buscarAlumno(1001).getNombre());
        boolean modificado = registro.modificarAlumno(1001, "Juan Pablo Pérez");
        System.out.println("   Resultado: " + (modificado ? "✓ Modificado" : "✗ Error"));
        System.out.println("   Nombre nuevo: " + registro.buscarAlumno(1001).getNombre());
        
        // Eliminar alumno
        System.out.println("\n🗑️  ELIMINACIÓN DE ALUMNO:");
        System.out.println("─────────────────────────────────────────────────────────────");
        
        System.out.println("\n   Eliminando alumno con legajo 1002...");
        boolean eliminado1 = registro.eliminarAlumno(1002);
        System.out.println("   Resultado: " + (eliminado1 ? "✓ Eliminado" : "✗ Error"));
        
        System.out.println("\n   Intentando eliminar legajo inexistente (9999)...");
        boolean eliminado2 = registro.eliminarAlumno(9999);
        System.out.println("   Resultado: " + (eliminado2 ? "✗ No debió eliminarse" : "✓ No encontrado (correcto)"));
        
        // Mostrar estado final
        System.out.println("\n📋 LISTADO FINAL:");
        System.out.println("─────────────────────────────────────────────────────────────");
        registro.mostrarAlumnos();
        
        // Estadísticas
        System.out.println("\n📊 ESTADÍSTICAS:");
        System.out.println("─────────────────────────────────────────────────────────────");
        System.out.println("   Total de alumnos registrados: " + registro.getCantidadAlumnos());
        System.out.println("   Registro vacío: " + (registro.estaVacio() ? "Sí" : "No"));
        
        // Verificación
        System.out.println("\n🔍 VERIFICACIÓN:");
        System.out.println("─────────────────────────────────────────────────────────────");
        boolean correcto = (registro.getCantidadAlumnos() == 2 &&
                           registro.buscarAlumno(1001) != null &&
                           registro.buscarAlumno(1002) == null &&
                           registro.buscarAlumno(1003) != null);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Las listas enlazadas son ideales para sistemas CRUD");
    }
}
