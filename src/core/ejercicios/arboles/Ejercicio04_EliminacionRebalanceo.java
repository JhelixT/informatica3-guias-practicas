package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 4: Eliminación con rebalanceo
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio04_EliminacionRebalanceo {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 4: ELIMINACIÓN CON REBALANCEO ═══\n");
        
        int[] valores = {50, 30, 70, 20, 40, 60, 80, 65, 75};
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        
        for (int valor : valores) {
            avl.insert(valor);
        }
        
        System.out.println("Árbol inicial:");
        avl.display();
        
        System.out.println("\nEliminando 20:");
        avl.delete(20);
        avl.display();
        
        System.out.println("\nEliminando 70:");
        avl.delete(70);
        avl.display();
        
        System.out.println("Nodos finales: " + avl.countNodes());
    }
}
