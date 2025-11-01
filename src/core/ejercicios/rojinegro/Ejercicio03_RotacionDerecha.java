package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 3: Rotación derecha
 * 
 * Implementar rotateRight(y) (simétrico a rotateLeft).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio03_RotacionDerecha {
    
    private NodoRojinegro<Integer> root;
    
    public void rotateRight(NodoRojinegro<Integer> y) {
        NodoRojinegro<Integer> x = y.getLeft();
        
        y.setLeft(x.getRight());
        if (x.getRight() != null) {
            x.getRight().setParent(y);
        }
        
        x.setParent(y.getParent());
        
        if (y.getParent() == null) {
            root = x;
        } else if (y == y.getParent().getRight()) {
            y.getParent().setRight(x);
        } else {
            y.getParent().setLeft(x);
        }
        
        x.setRight(y);
        y.setParent(x);
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
        System.out.println("\n═══ EJERCICIO 3: ROTACIÓN DERECHA ═══\n");
        
        Ejercicio03_RotacionDerecha tree = new Ejercicio03_RotacionDerecha();
        
        // Crear árbol: 30 <- 20 <- 10
        NodoRojinegro<Integer> n30 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> n20 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> n10 = new NodoRojinegro<>(10, Color.ROJO);
        
        tree.root = n30;
        n30.setLeft(n20);
        n20.setParent(n30);
        n20.setLeft(n10);
        n10.setParent(n20);
        
        System.out.println("Antes de rotateRight(30):");
        tree.display(tree.root, "", true);
        
        tree.rotateRight(n30);
        
        System.out.println("\nDespués de rotateRight(30):");
        tree.display(tree.root, "", true);
        System.out.println("\nRaíz actual: " + tree.root.getData());
    }
}
