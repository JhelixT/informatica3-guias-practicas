package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 2: Rotación izquierda
 * 
 * Implementar rotateLeft(x) actualizando parent/left/right y la raíz si corresponde.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio02_RotacionIzquierda {
    
    private NodoRojinegro<Integer> root;
    
    public void rotateLeft(NodoRojinegro<Integer> x) {
        NodoRojinegro<Integer> y = x.getRight();
        
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }
        
        y.setParent(x.getParent());
        
        if (x.getParent() == null) {
            root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        
        y.setLeft(x);
        x.setParent(y);
    }
    
    private void display(NodoRojinegro<Integer> node, String prefix, boolean isRight) {
        if (node != null) {
            System.out.println(prefix + (isRight ? "└── " : "┌── ") + node);
            if (node.getLeft() != null || node.getRight() != null) {
                if (node.getRight() != null) {
                    display(node.getRight(), prefix + (isRight ? "    " : "│   "), true);
                }
                if (node.getLeft() != null) {
                    display(node.getLeft(), prefix + (isRight ? "    " : "│   "), false);
                }
            }
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 2: ROTACIÓN IZQUIERDA ═══\n");
        
        Ejercicio02_RotacionIzquierda tree = new Ejercicio02_RotacionIzquierda();
        
        // Crear árbol: 10 -> 20 -> 30
        NodoRojinegro<Integer> n10 = new NodoRojinegro<>(10, Color.NEGRO);
        NodoRojinegro<Integer> n20 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> n30 = new NodoRojinegro<>(30, Color.ROJO);
        
        tree.root = n10;
        n10.setRight(n20);
        n20.setParent(n10);
        n20.setRight(n30);
        n30.setParent(n20);
        
        System.out.println("Antes de rotateLeft(10):");
        tree.display(tree.root, "", true);
        
        tree.rotateLeft(n10);
        
        System.out.println("\nDespués de rotateLeft(10):");
        tree.display(tree.root, "", true);
        System.out.println("\nRaíz actual: " + tree.root.getData());
    }
}
