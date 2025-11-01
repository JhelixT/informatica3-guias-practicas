package core.ejercicios.rojinegro;

import core.estructuras.arboles.ArbolRojinegro;
import core.estructuras.arboles.NodoRojinegro;

/**
 * Ejercicio 8: successor y predecessor
 * 
 * Implementar successor y predecessor de un nodo BST.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio08_SuccessorPredecessor {
    
    private static class ArbolConSuccessor extends ArbolRojinegro<Integer> {
        
        private NodoRojinegro<Integer> searchNode(int key) {
            NodoRojinegro<Integer> current = getRoot();
            while (current != null) {
                if (key == current.getData()) return current;
                else if (key < current.getData()) current = current.getLeft();
                else current = current.getRight();
            }
            return null;
        }
        
        private NodoRojinegro<Integer> getRoot() {
            // Hack para acceder a root (normalmente sería protected)
            if (isEmpty()) return null;
            return searchNodePublic(findMin());
        }
        
        private NodoRojinegro<Integer> searchNodePublic(Integer data) {
            return searchNode(data);
        }
        
        public Integer successor(int key) {
            NodoRojinegro<Integer> node = searchNode(key);
            if (node == null) return null;
            
            // Caso 1: tiene hijo derecho -> mínimo del subárbol derecho
            if (node.getRight() != null) {
                NodoRojinegro<Integer> current = node.getRight();
                while (current.getLeft() != null) {
                    current = current.getLeft();
                }
                return current.getData();
            }
            
            // Caso 2: no tiene hijo derecho -> buscar ancestro
            NodoRojinegro<Integer> parent = node.getParent();
            while (parent != null && node == parent.getRight()) {
                node = parent;
                parent = parent.getParent();
            }
            return parent != null ? parent.getData() : null;
        }
        
        public Integer predecessor(int key) {
            NodoRojinegro<Integer> node = searchNode(key);
            if (node == null) return null;
            
            // Caso 1: tiene hijo izquierdo -> máximo del subárbol izquierdo
            if (node.getLeft() != null) {
                NodoRojinegro<Integer> current = node.getLeft();
                while (current.getRight() != null) {
                    current = current.getRight();
                }
                return current.getData();
            }
            
            // Caso 2: no tiene hijo izquierdo -> buscar ancestro
            NodoRojinegro<Integer> parent = node.getParent();
            while (parent != null && node == parent.getLeft()) {
                node = parent;
                parent = parent.getParent();
            }
            return parent != null ? parent.getData() : null;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 8: SUCCESSOR Y PREDECESSOR ═══\n");
        
        ArbolConSuccessor arbol = new ArbolConSuccessor();
        arbol.insert(10);
        arbol.insert(5);
        arbol.insert(15);
        
        System.out.println("Árbol: " + arbol.inOrderTraversal());
        
        System.out.println("\nSuccessor de 5: " + arbol.successor(5));
        System.out.println("Predecessor de 5: " + arbol.predecessor(5));
        
        System.out.println("\nSuccessor de 10: " + arbol.successor(10));
        System.out.println("Predecessor de 10: " + arbol.predecessor(10));
        
        System.out.println("\nSuccessor de 15: " + arbol.successor(15));
        System.out.println("Predecessor de 15: " + arbol.predecessor(15));
    }
}
