package views;

import core.ejercicios.arboles.*;
import core.utils.InputValidator;
import core.utils.MenuFormatter;

/**
 * Menu de ejercicios de Arbol AVL
 * 
 * @author JhelixT
 * @version 1.0
 */
public class EjerciciosArbolesAVLMenu {
    
    public static void mostrar() {
        boolean continuar = true;
        
        while (continuar) {
            MenuFormatter.limpiarPantalla();
            
            System.out.println("╔════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                  EJERCICIOS DE Arbol AVL                           ║");
            System.out.println("╠════════════════════════════════════════════════════════════════════╣");
            System.out.println("║                                                                    ║");
            System.out.println("║  📚 EJERCICIOS BASICOS                                             ║");
            System.out.println("║  1. Inserciones y FE paso a paso (LL y RR)                         ║");
            System.out.println("║  2. Inserciones con rotacion doble (LR y RL)                       ║");
            System.out.println("║  3. Secuencia ordenada y 'efecto peinar'                           ║");
            System.out.println("║  4. ELIMINACION con rebalanceo                                     ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🔬 EJERCICIOS INTERMEDIOS                                         ║");
            System.out.println("║  5. Comprobador de AVL                                             ║");
            System.out.println("║  6. Factor de equilibrio completo                                  ║");
            System.out.println("║  7. Implementacion: rotacion izquierda                             ║");
            System.out.println("║  8. Implementacion: rotacion doble LR                              ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🎯 EJERCICIOS AVANZADOS                                           ║");
            System.out.println("║  9. Secuencias estresantes y pruebas unitarias                     ║");
            System.out.println("║                                                                    ║");
            System.out.println("║  🚀 OPCIONES                                                       ║");
            System.out.println("║ 10. Ejecutar TODOS los ejercicios                                  ║");
            System.out.println("║  0. Volver al Menu principal                                       ║");
            System.out.println("║                                                                    ║");
            System.out.println("╚════════════════════════════════════════════════════════════════════╝");
            
            int opcion = InputValidator.leerEnteroEnRango("Seleccione una opcion: ", 0, 10);
            
            MenuFormatter.limpiarPantalla();
            
            switch (opcion) {
                case 1:
                    Ejercicio01_InsercionesLLRR.ejecutar();
                    break;
                    
                case 2:
                    Ejercicio02_RotacionDoble.ejecutar();
                    break;
                    
                case 3:
                    Ejercicio03_SecuenciaOrdenada.ejecutar();
                    break;
                    
                case 4:
                    Ejercicio04_EliminacionRebalanceo.ejecutar();
                    break;
                    
                case 5:
                    Ejercicio05_ComprobadorAVL.ejecutar();
                    break;
                    
                case 6:
                    Ejercicio06_FactorEquilibrioCompleto.ejecutar();
                    break;
                    
                case 7:
                    Ejercicio07_ImplementacionRotacionIzquierda.ejecutar();
                    break;
                    
                case 8:
                    Ejercicio08_RotacionDobleLR.ejecutar();
                    break;
                    
                case 9:
                    Ejercicio10_PruebasUnitarias.ejecutar();
                    break;
                    
                case 10:
                    ejecutarTodosLosEjercicios();
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("\n👋 Volviendo al Menu principal...\n");
                    break;
                    
                default:
                    System.out.println("\n❌ opcion no valida\n");
            }
            
            if (continuar && opcion != 0) {
                InputValidator.pausar();
            }
        }
    }
    
    private static void ejecutarTodosLosEjercicios() {
        System.out.println("\n╔════════════════════════════════════════════════════════════════════╗");
        System.out.println("║            EJECUTANDO TODOS LOS EJERCICIOS DE AVL                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("⏳ Este proceso puede tomar varios segundos...\n");
        
        String[] nombresEjercicios = {
            "Inserciones y FE (LL y RR)",
            "rotacion doble (LR y RL)",
            "Secuencia ordenada",
            "ELIMINACION con rebalanceo",
            "Comprobador de AVL",
            "Factor de equilibrio completo",
            "rotacion izquierda",
            "rotacion doble LR",
            "Pruebas unitarias"
        };
        
        for (int i = 0; i < nombresEjercicios.length; i++) {
            System.out.println("═══════════════════════════════════════════════════════════════════");
            System.out.println("Ejecutando Ejercicio " + (i + 1) + ": " + nombresEjercicios[i]);
            System.out.println("═══════════════════════════════════════════════════════════════════");
            
            switch (i + 1) {
                case 1: Ejercicio01_InsercionesLLRR.ejecutar(); break;
                case 2: Ejercicio02_RotacionDoble.ejecutar(); break;
                case 3: Ejercicio03_SecuenciaOrdenada.ejecutar(); break;
                case 4: Ejercicio04_EliminacionRebalanceo.ejecutar(); break;
                case 5: Ejercicio05_ComprobadorAVL.ejecutar(); break;
                case 6: Ejercicio06_FactorEquilibrioCompleto.ejecutar(); break;
                case 7: Ejercicio07_ImplementacionRotacionIzquierda.ejecutar(); break;
                case 8: Ejercicio08_RotacionDobleLR.ejecutar(); break;
                case 9: Ejercicio10_PruebasUnitarias.ejecutar(); break;
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
