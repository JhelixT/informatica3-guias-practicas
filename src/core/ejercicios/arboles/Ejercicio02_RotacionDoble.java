package core.ejercicios.arboles;

import core.estructuras.arboles.ArbolAVL;

/**
 * Ejercicio 2: Inserciones con rotación doble (caso LR y RL)
 * 
 * Inserte la secuencia: 30, 10, 20, 40, 35, 37.
 * a) Muestre el estado del árbol en cada paso.
 * b) Identifique los desbalances (FE = ±2).
 * c) Especifique cuándo corresponde rotación doble (LR o RL) y ejecútela.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio02_RotacionDoble {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 2: ROTACIONES DOBLES LR Y RL ═══\n");
        
        int[] secuencia = {30, 10, 20, 40, 35, 37};
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
