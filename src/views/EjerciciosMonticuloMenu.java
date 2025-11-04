package views;

import core.ejercicios.monticulo.*;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

/**
 * Menu de ejercicios de Montículo Binario
 * 
 * @author JhelixT
 * @version 1.0
 */
public class EjerciciosMonticuloMenu {
    
    public static void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            MenuFormatter.limpiarPantalla();
            
            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║           EJERCICIOS DE MONTÍCULO BINARIO                          ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                    ║");
            System.out.println("║  📚 EJERCICIOS BASICOS                                             ║");
            System.out.println("║   1. Crear estructura básica de MinHeap                            ║");
            System.out.println("║   2. Implementar percolateUp                                       ║");
            System.out.println("║   3. Implementar percolateDown                                     ║");
            System.out.println("║   4. Mostrar el heap en forma de Arbol                             ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🔬 EJERCICIOS INTERMEDIOS                                         ║");
            System.out.println("║   5. Construcción desde un arreglo (heapify)                       ║");
            System.out.println("║   6. Implementar Heapsort                                          ║");
            System.out.println("║   7. Implementar MaxHeap                                           ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🎯 EJERCICIOS AVANZADOS                                           ║");
            System.out.println("║   8. Cola de prioridad de pacientes                                ║");
            System.out.println("║   9. Seguimiento del estado interno                                ║");
            System.out.println("║  10. Agenda de tareas con prioridad (INTEGRADOR)                   ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🚀 OPCIONES                                                       ║");
            System.out.println("║  11. Ejecutar TODOS los ejercicios                                 ║");
            System.out.println("║   0. Volver al Menu principal                                      ║");
            System.out.println("║                                                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 11);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1:
                    Ejercicio01_MinHeapBasico.ejecutar();
                    break;
                    
                case 2:
                    Ejercicio02_PercolateUp.ejecutar();
                    break;
                    
                case 3:
                    Ejercicio03_PercolateDown.ejecutar();
                    break;
                    
                case 4:
                    Ejercicio04_MostrarArbol.ejecutar();
                    break;
                    
                case 5:
                    Ejercicio05_Heapify.ejecutar();
                    break;
                    
                case 6:
                    Ejercicio06_Heapsort.ejecutar();
                    break;
                    
                case 7:
                    Ejercicio07_MaxHeap.ejecutar();
                    break;
                    
                case 8:
                    Ejercicio08_ColaPacientes.ejecutar();
                    break;
                    
                case 9:
                    Ejercicio09_SeguimientoEstado.ejecutar();
                    break;
                    
                case 10:
                    Ejercicio10_AgendaTareas.ejecutar();
                    break;
                    
                case 11:
                    ejecutarTodosLosEjercicios();
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("\n👋 Volviendo al Menu principal...\n");
                    break;
                    
                default:
                    System.out.println("\n❌ opcion no válida\n");
            }
            
            if (continuar && opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    private static void ejecutarTodosLosEjercicios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║        EJECUTANDO TODOS LOS EJERCICIOS DE MONTÍCULO               ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");
        
        String[] nombresEjercicios = {
            "Estructura básica de MinHeap",
            "Percolate Up",
            "Percolate Down",
            "Mostrar Arbol",
            "Heapify",
            "Heapsort",
            "MaxHeap",
            "Cola de prioridad de pacientes",
            "Seguimiento del estado interno",
            "Agenda de tareas (Integrador)"
        };
        
        for (int i = 0; i < nombresEjercicios.length; i++) {
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("Ejecutando Ejercicio " + (i + 1) + ": " + nombresEjercicios[i]);
            System.out.println("═══════════════════════════════════════════════════════════════════");
            
            switch (i + 1) {
                case 1: Ejercicio01_MinHeapBasico.ejecutar(); break;
                case 2: Ejercicio02_PercolateUp.ejecutar(); break;
                case 3: Ejercicio03_PercolateDown.ejecutar(); break;
                case 4: Ejercicio04_MostrarArbol.ejecutar(); break;
                case 5: Ejercicio05_Heapify.ejecutar(); break;
                case 6: Ejercicio06_Heapsort.ejecutar(); break;
                case 7: Ejercicio07_MaxHeap.ejecutar(); break;
                case 8: Ejercicio08_ColaPacientes.ejecutar(); break;
                case 9: Ejercicio09_SeguimientoEstado.ejecutar(); break;
                case 10: 
                    System.out.println("\nNota: El ejercicio 10 es interactivo, ejecutar manualmente desde el Menu");
                    break;
            }
            
            if (i < nombresEjercicios.length - 1) {
                InputValidator.pausar();
                MenuFormatter.limpiarPantalla();
            }
        }
        
        System.out.println("\n\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║          ¡TODOS LOS EJERCICIOS HAN SIDO COMPLETADOS!              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝");
    }
}
