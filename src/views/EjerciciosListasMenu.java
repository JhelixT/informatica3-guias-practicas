package views;

import core.ejercicios.listas.*;
import core.utils.*;

/**
 * Menu interactivo para los ejercicios de Listas Enlazadas Simples.
 * 
 * Proporciona acceso a 10 ejercicios practicos que demuestran
 * diversas operaciones fundamentales:
 * - Creacion y enlace de nodos
 * - Insercion (inicio, final, posicion)
 * - Eliminacion (por valor, duplicados)
 * - Busqueda y conteo
 * - Inversion de lista
 * - Aplicacion practica (registro de alumnos)
 * 
 * @author JhelixT
 * @version 1.0
 */
public class EjerciciosListasMenu {
    
    public void mostrarMenu() {
        while (true) {
            MenuFormatter.limpiarPantalla();
            MenuFormatter.mostrarTituloPrincipal("EJERCICIOS DE LISTAS ENLAZADAS SIMPLES");
            
            System.out.println(" Coleccion de ejercicios practicos para aprender listas enlazadas\n");
            
            MenuFormatter.mostrarSeparador();
            System.out.println("EJERCICIOS BASICOS:");
            MenuFormatter.mostrarOpcion(1, "Ejercicio 1 - Crear un nodo");
            System.out.println("     └─ Crear y enlazar nodos manualmente");
            
            MenuFormatter.mostrarOpcion(2, "Ejercicio 2 - Insertar al inicio");
            System.out.println("     └─ Agregar elementos al inicio de la lista");
            
            MenuFormatter.mostrarOpcion(3, "Ejercicio 3 - Insertar al final");
            System.out.println("     └─ Agregar elementos al final de la lista");
            
            MenuFormatter.mostrarSeparador();
            System.out.println("EJERCICIOS INTERMEDIOS:");
            MenuFormatter.mostrarOpcion(4, "Ejercicio 4 - Eliminar por valor");
            System.out.println("     └─ Eliminar la primera ocurrencia de un valor");
            
            MenuFormatter.mostrarOpcion(5, "Ejercicio 5 - Buscar un valor");
            System.out.println("     └─ Buscar si un elemento existe en la lista");
            
            MenuFormatter.mostrarOpcion(6, "Ejercicio 6 - Contar elementos");
            System.out.println("     └─ Obtener la cantidad de elementos");
            
            MenuFormatter.mostrarOpcion(7, "Ejercicio 7 - Invertir la lista");
            System.out.println("     └─ Invertir el orden de los elementos");
            
            MenuFormatter.mostrarSeparador();
            System.out.println("EJERCICIOS AVANZADOS:");
            MenuFormatter.mostrarOpcion(8, "Ejercicio 8 - Insertar en posicion");
            System.out.println("     +-- Insertar en una posicion especifica");
            
            MenuFormatter.mostrarOpcion(9, "Ejercicio 9 - Eliminar duplicados");
            System.out.println("     └─ Eliminar valores repetidos");
            
            MenuFormatter.mostrarOpcion(10, "Ejercicio 10 - Registro de alumnos");
            System.out.println("     +-- Aplicacion practica completa");
            
            MenuFormatter.mostrarSeparador();
            MenuFormatter.mostrarOpcion(11, "▶️  Ejecutar TODOS los ejercicios");
            MenuFormatter.mostrarOpcion(0, "Volver al menu principal");
            
            int opcion = InputValidator.leerEnteroEnRango("\nSeleccione un ejercicio: ", 0, 11);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1 -> ejecutarEjercicio("Ejercicio 1", Ejercicio01_CrearNodo::ejecutar);
                case 2 -> ejecutarEjercicio("Ejercicio 2", Ejercicio02_InsertarInicio::ejecutar);
                case 3 -> ejecutarEjercicio("Ejercicio 3", Ejercicio03_InsertarFinal::ejecutar);
                case 4 -> ejecutarEjercicio("Ejercicio 4", Ejercicio04_EliminarPorValor::ejecutar);
                case 5 -> ejecutarEjercicio("Ejercicio 5", Ejercicio05_BuscarValor::ejecutar);
                case 6 -> ejecutarEjercicio("Ejercicio 6", Ejercicio06_ContarElementos::ejecutar);
                case 7 -> ejecutarEjercicio("Ejercicio 7", Ejercicio07_InvertirLista::ejecutar);
                case 8 -> ejecutarEjercicio("Ejercicio 8", Ejercicio08_InsertarPosicion::ejecutar);
                case 9 -> ejecutarEjercicio("Ejercicio 9", Ejercicio09_EliminarDuplicados::ejecutar);
                case 10 -> ejecutarEjercicio("Ejercicio 10", Ejercicio10_RegistroAlumnos::ejecutar);
                case 11 -> ejecutarTodosLosEjercicios();
                case 0 -> {
                    return;
                }
            }
            
            if (opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    /**
     * Ejecuta un ejercicio individual con manejo de errores.
     */
    private void ejecutarEjercicio(String nombre, Runnable ejercicio) {
        try {
            ejercicio.run();
        } catch (Exception e) {
            MenuFormatter.mostrarMensajeError("\n❌ Error al ejecutar " + nombre + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Ejecuta todos los ejercicios en secuencia.
     */
    private void ejecutarTodosLosEjercicios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║     EJECUTANDO TODOS LOS EJERCICIOS EN SECUENCIA         ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        if (!InputValidator.confirmar("¿Desea ejecutar los 10 ejercicios?")) {
            MenuFormatter.mostrarMensajeInfo("Operacion cancelada");
            return;
        }
        
        int ejerciciosCompletados = 0;
        int ejerciciosConError = 0;
        
        // Ejercicio 1
        try {
            Ejercicio01_CrearNodo.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 1: " + e.getMessage());
        }
        
        // Ejercicio 2
        try {
            Ejercicio02_InsertarInicio.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 2: " + e.getMessage());
        }
        
        // Ejercicio 3
        try {
            Ejercicio03_InsertarFinal.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 3: " + e.getMessage());
        }
        
        // Ejercicio 4
        try {
            Ejercicio04_EliminarPorValor.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 4: " + e.getMessage());
        }
        
        // Ejercicio 5
        try {
            Ejercicio05_BuscarValor.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 5: " + e.getMessage());
        }
        
        // Ejercicio 6
        try {
            Ejercicio06_ContarElementos.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 6: " + e.getMessage());
        }
        
        // Ejercicio 7
        try {
            Ejercicio07_InvertirLista.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 7: " + e.getMessage());
        }
        
        // Ejercicio 8
        try {
            Ejercicio08_InsertarPosicion.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 8: " + e.getMessage());
        }
        
        // Ejercicio 9
        try {
            Ejercicio09_EliminarDuplicados.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
            InputValidator.pausar();
            MenuFormatter.limpiarPantalla();
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 9: " + e.getMessage());
        }
        
        // Ejercicio 10
        try {
            Ejercicio10_RegistroAlumnos.ejecutar();
            ejerciciosCompletados++;
            System.out.println("\n" + "═".repeat(60));
        } catch (Exception e) {
            ejerciciosConError++;
            MenuFormatter.mostrarMensajeError("Error en Ejercicio 10: " + e.getMessage());
        }
        
        // Resumen final
        System.out.println("\n\n");
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║              RESUMEN DE EJECUCION                         ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("║  Ejercicios completados: %-30d║%n", ejerciciosCompletados);
        System.out.printf("║  Ejercicios con error:   %-30d║%n", ejerciciosConError);
        System.out.printf("║  Total de ejercicios:    %-30d║%n", 10);
        System.out.println("╚════════════════════════════════════════════════════════════╝");
        
        if (ejerciciosConError == 0) {
            MenuFormatter.mostrarMensajeExito("\n🎉 ¡TODOS LOS EJERCICIOS COMPLETADOS EXITOSAMENTE!");
        } else {
            MenuFormatter.mostrarMensajeAdvertencia("\n⚠️  Algunos ejercicios presentaron errores");
        }
    }
}
