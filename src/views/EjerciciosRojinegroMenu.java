package views;

import core.ejercicios.rojinegro.*;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

/**
 * Menú de ejercicios de Árbol Rojinegro
 * 
 * @author JhelixT
 * @version 1.0
 */
public class EjerciciosRojinegroMenu {
    
    public static void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            MenuFormatter.limpiarPantalla();
            
            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║              EJERCICIOS DE ÁRBOL ROJINEGRO                         ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                    ║");
            System.out.println("║  📚 EJERCICIOS BÁSICOS                                             ║");
            System.out.println("║  1. Nodo y NIL sentinel                                            ║");
            System.out.println("║  2. Rotación izquierda                                             ║");
            System.out.println("║  3. Rotación derecha                                               ║");
            System.out.println("║  4. Inserción como ABB (sin balance)                               ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🔬 EJERCICIOS INTERMEDIOS                                         ║");
            System.out.println("║  5. Clasificador de caso para fixInsert                            ║");
            System.out.println("║  6. Recoloreo por tío rojo                                         ║");
            System.out.println("║  7. Rotación simple vs doble                                       ║");
            System.out.println("║  8. Successor y predecessor                                        ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🎯 EJERCICIOS AVANZADOS                                           ║");
            System.out.println("║  9. Consulta por rango [a,b]                                       ║");
            System.out.println("║ 10. Verificadores de invariantes                                   ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🚀 OPCIONES                                                       ║");
            System.out.println("║ 11. Ejecutar TODOS los ejercicios                                  ║");
            System.out.println("║  0. Volver al menú principal                                       ║");
            System.out.println("║                                                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opción: ", 0, 11);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1:
                    Ejercicio01_NodoYNIL.ejecutar();
                    break;
                    
                case 2:
                    Ejercicio02_RotacionIzquierda.ejecutar();
                    break;
                    
                case 3:
                    Ejercicio03_RotacionDerecha.ejecutar();
                    break;
                    
                case 4:
                    Ejercicio04_InsercionBST.ejecutar();
                    break;
                    
                case 5:
                    Ejercicio05_ClasificadorCaso.ejecutar();
                    break;
                    
                case 6:
                    Ejercicio06_RecoloreoTioRojo.ejecutar();
                    break;
                    
                case 7:
                    Ejercicio07_RotacionSimpleDoble.ejecutar();
                    break;
                    
                case 8:
                    Ejercicio08_SuccessorPredecessor.ejecutar();
                    break;
                    
                case 9:
                    Ejercicio09_ConsultaRango.ejecutar();
                    break;
                    
                case 10:
                    Ejercicio10_VerificadoresInvariantes.ejecutar();
                    break;
                    
                case 11:
                    ejecutarTodosLosEjercicios();
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("\n👋 Volviendo al menú principal...\n");
                    break;
                    
                default:
                    System.out.println("\n❌ Opción no válida\n");
            }
            
            if (continuar && opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    private static void ejecutarTodosLosEjercicios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║         EJECUTANDO TODOS LOS EJERCICIOS DE ROJINEGRO              ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");
        
        String[] nombresEjercicios = {
            "Nodo y NIL sentinel",
            "Rotación izquierda",
            "Rotación derecha",
            "Inserción como ABB",
            "Clasificador de caso",
            "Recoloreo por tío rojo",
            "Rotación simple vs doble",
            "Successor y predecessor",
            "Consulta por rango",
            "Verificadores de invariantes"
        };
        
        for (int i = 0; i < nombresEjercicios.length; i++) {
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("Ejecutando Ejercicio " + (i + 1) + ": " + nombresEjercicios[i]);
            System.out.println("═══════════════════════════════════════════════════════════════════");
            
            switch (i + 1) {
                case 1: Ejercicio01_NodoYNIL.ejecutar(); break;
                case 2: Ejercicio02_RotacionIzquierda.ejecutar(); break;
                case 3: Ejercicio03_RotacionDerecha.ejecutar(); break;
                case 4: Ejercicio04_InsercionBST.ejecutar(); break;
                case 5: Ejercicio05_ClasificadorCaso.ejecutar(); break;
                case 6: Ejercicio06_RecoloreoTioRojo.ejecutar(); break;
                case 7: Ejercicio07_RotacionSimpleDoble.ejecutar(); break;
                case 8: Ejercicio08_SuccessorPredecessor.ejecutar(); break;
                case 9: Ejercicio09_ConsultaRango.ejecutar(); break;
                case 10: Ejercicio10_VerificadoresInvariantes.ejecutar(); break;
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
