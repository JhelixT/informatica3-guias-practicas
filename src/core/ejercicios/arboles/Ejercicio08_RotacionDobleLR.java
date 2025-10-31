package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 8: Implementación guiada - rotación doble izquierda-derecha (LR)
 * 
 * Programe rotacionDobleIzquierdaDerecha(n) usando dos rotaciones simples.
 * a) Justifique por qué LR ≡ (rotación simple izquierda en hijo) + (rotación simple derecha en n).
 * b) Valide con el caso del ejercicio 2.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio08_RotacionDobleLR {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 8: ROTACIÓN DOBLE LR ═══\n");
        
        // Secuencia que causa caso LR
        int[] secuencia = {30, 10, 20};
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        
        for (int valor : secuencia) {
            System.out.println("Insertando: " + valor);
            avl.insert(valor);
            avl.display();
        }
    }
}
