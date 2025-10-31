package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolBST;
import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 3: Secuencia ordenada y "efecto peinar"
 * 
 * Inserte 5, 10, 15, 20, 25, 30, 35 en un ABB y luego en un AVL.
 * a) Explique por qué un ABB puro se desbalancea con datos crecientes.
 * b) Detalle las rotaciones que hacen que el AVL mantenga altura O(log n).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio03_SecuenciaOrdenada {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 3: SECUENCIA ORDENADA ═══\n");
        
        int[] secuencia = {5, 10, 15, 20, 25, 30, 35};
        
        System.out.println("ABB con secuencia ordenada:");
        ArbolBST<Integer> abb = new ArbolBST<>();
        for (int valor : secuencia) {
            abb.insert(valor);
        }
        abb.display();
        System.out.println("Altura ABB: " + abb.getHeight());
        
        System.out.println("\nAVL con secuencia ordenada:");
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        for (int valor : secuencia) {
            avl.insert(valor);
        }
        avl.display();
        System.out.println("Altura AVL: " + avl.getHeight());
    }
}
