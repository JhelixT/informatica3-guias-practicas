package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 7: Rotación simple vs doble (un lado)
 * 
 * Rama izquierda del fix: LR → rotateLeft(p) y luego rotateRight(g); LL → solo rotateRight(g) + recoloreo.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio07_RotacionSimpleDoble {
    
    private NodoRojinegro<Integer> root;
    
    private void rotateLeft(NodoRojinegro<Integer> x) {
        NodoRojinegro<Integer> y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) y.getLeft().setParent(x);
        y.setParent(x.getParent());
        if (x.getParent() == null) root = y;
        else if (x == x.getParent().getLeft()) x.getParent().setLeft(y);
        else x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }
    
    private void rotateRight(NodoRojinegro<Integer> y) {
        NodoRojinegro<Integer> x = y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != null) x.getRight().setParent(y);
        x.setParent(y.getParent());
        if (y.getParent() == null) root = x;
        else if (y == y.getParent().getRight()) y.getParent().setRight(x);
        else y.getParent().setLeft(x);
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
        System.out.println("\n═══ EJERCICIO 7: ROTACIÓN SIMPLE VS DOBLE ═══\n");
        
        // Caso LL: rotación simple derecha
        System.out.println("Caso LL (rotación simple derecha):");
        Ejercicio07_RotacionSimpleDoble tree1 = new Ejercicio07_RotacionSimpleDoble();
        NodoRojinegro<Integer> g1 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p1 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> z1 = new NodoRojinegro<>(10, Color.ROJO);
        tree1.root = g1;
        g1.setLeft(p1);
        p1.setParent(g1);
        p1.setLeft(z1);
        z1.setParent(p1);
        
        System.out.println("Antes:");
        tree1.display(tree1.root, "", true);
        
        tree1.rotateRight(g1);
        p1.setColor(Color.NEGRO);
        g1.setColor(Color.ROJO);
        
        System.out.println("\nDespués (rotateRight + recoloreo):");
        tree1.display(tree1.root, "", true);
        
        // Caso LR: rotación doble
        System.out.println("\n\nCaso LR (rotación doble):");
        Ejercicio07_RotacionSimpleDoble tree2 = new Ejercicio07_RotacionSimpleDoble();
        NodoRojinegro<Integer> g2 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p2 = new NodoRojinegro<>(10, Color.ROJO);
        NodoRojinegro<Integer> z2 = new NodoRojinegro<>(20, Color.ROJO);
        tree2.root = g2;
        g2.setLeft(p2);
        p2.setParent(g2);
        p2.setRight(z2);
        z2.setParent(p2);
        
        System.out.println("Antes:");
        tree2.display(tree2.root, "", true);
        
        tree2.rotateLeft(p2);
        tree2.rotateRight(g2);
        z2.setColor(Color.NEGRO);
        g2.setColor(Color.ROJO);
        
        System.out.println("\nDespués (rotateLeft(p) + rotateRight(g) + recoloreo):");
        tree2.display(tree2.root, "", true);
    }
}
