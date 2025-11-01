package core.ejercicios.rojinegro;

import core.estructuras.arboles.ArbolRojinegro;
import java.util.List;

/**
 * Ejercicio 8: successor y predecessor
 * 
 * Implementar successor y predecessor usando el recorrido inorden de ArbolRojinegro.
 * Successor = siguiente valor en inorden, Predecessor = valor anterior en inorden.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio08_SuccessorPredecessor {
    
    private static class ArbolConSuccessor extends ArbolRojinegro<Integer> {
        
        public Integer successor(int key) {
            if (!search(key)) return null;
            
            List<Integer> inorden = inOrderTraversal();
            int index = inorden.indexOf(key);
            
            return (index != -1 && index < inorden.size() - 1) 
                ? inorden.get(index + 1) 
                : null;
        }
        
        public Integer predecessor(int key) {
            if (!search(key)) return null;
            
            List<Integer> inorden = inOrderTraversal();
            int index = inorden.indexOf(key);
            
            return (index > 0) 
                ? inorden.get(index - 1) 
                : null;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 8: SUCCESSOR Y PREDECESSOR ═══\n");
        
        ArbolConSuccessor arbol = new ArbolConSuccessor();
        int[] valores = {10, 5, 15, 3, 7, 12, 20};
        
        for (int v : valores) {
            arbol.insert(v);
        }
        
        System.out.println("Árbol (inorden): " + arbol.inOrderTraversal());
        arbol.display();
        
        System.out.println("\n--- Successor (siguiente en inorden) ---");
        System.out.println("Successor de 3: " + arbol.successor(3));
        System.out.println("Successor de 10: " + arbol.successor(10));
        System.out.println("Successor de 20: " + arbol.successor(20));
        
        System.out.println("\n--- Predecessor (anterior en inorden) ---");
        System.out.println("Predecessor de 3: " + arbol.predecessor(3));
        System.out.println("Predecessor de 10: " + arbol.predecessor(10));
        System.out.println("Predecessor de 20: " + arbol.predecessor(20));
        
        System.out.println("\n✓ Successor/Predecessor calculados usando recorrido inorden");
    }
}
