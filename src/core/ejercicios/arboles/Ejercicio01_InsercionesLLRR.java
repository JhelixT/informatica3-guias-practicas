package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 1: Inserciones y FE paso a paso (caso LL y RR)
 * 
 * Inserte en un AVL la secuencia: 30, 20, 10, 40, 50, 60.
 * a) Dibuje el árbol tras cada inserción.
 * b) Calcule alturas y factor de equilibrio (FE) de cada nodo en cada paso.
 * c) Indique qué rotación se aplica en cada desbalance (LL o RR) y por qué.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio01_InsercionesLLRR {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 1: INSERCIONES CASOS LL Y RR ═══\n");
        
        int[] secuencia = {30, 20, 10, 40, 50, 60};
        ArbolAVL<Integer> avl = new ArbolAVL<>();
        
        for (int valor : secuencia) {
            System.out.println("Insertando: " + valor);
            avl.insert(valor);
            avl.display();
        }
        
        System.out.println("Altura final: " + avl.getHeight());
        System.out.println("Total de nodos: " + avl.countNodes());
    }
}
