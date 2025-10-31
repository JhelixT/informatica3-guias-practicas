package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 7: Implementación guiada - rotación izquierda
 * 
 * Complete el código de una rotación simple a izquierda y úselo en insertar.
 * a) Muestre antes/después sobre un subárbol donde ocurra caso RR.
 * b) Actualice correctamente las alturas involucradas.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio07_ImplementacionRotacionIzquierda {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 7: ROTACIÓN IZQUIERDA (CASO RR) ═══\n");
        
        // Secuencia que causa caso RR
        int[] secuencia = {10, 20, 30};
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        
        for (int valor : secuencia) {
            System.out.println("Insertando: " + valor);
            avl.insert(valor);
            avl.display();
        }
    }
}
