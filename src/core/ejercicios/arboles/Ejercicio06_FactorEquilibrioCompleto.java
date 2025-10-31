package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 6: Factor de equilibrio completo
 * 
 * Inserte 10, 100, 20, 80, 40, 70 y:
 * a) Liste para el árbol final (valor, altura, FE) de todos los nodos.
 * b) Marque los nodos críticos donde surgieron FE = ±2 durante el proceso.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio06_FactorEquilibrioCompleto {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 6: FACTOR DE EQUILIBRIO COMPLETO ═══\n");
        
        int[] secuencia = {10, 100, 20, 80, 40, 70};
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        
        for (int valor : secuencia) {
            avl.insert(valor);
        }
        
        System.out.println("Árbol final (muestra valor, altura h:, factor equilibrio b:):");
        avl.display();
        
        System.out.println("\nNodos: " + avl.countNodes());
        System.out.println("Altura: " + avl.getHeight());
    }
}
