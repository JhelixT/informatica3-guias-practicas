package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 4: Inserción como ABB (sin balance)
 * 
 * Insertar como BST y devolver el nodo rojo creado con left/right/parent = null; sin balance ni recoloreo.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio04_InsercionBST {
    
    private NodoRojinegro<Integer> root;
    
    public NodoRojinegro<Integer> insertBST(int key) {
        NodoRojinegro<Integer> newNode = new NodoRojinegro<>(key, Color.ROJO);
        
        if (root == null) {
            root = newNode;
            return newNode;
        }
        
        NodoRojinegro<Integer> current = root;
        NodoRojinegro<Integer> parent = null;
        
        while (current != null) {
            parent = current;
            if (key < current.getData()) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        
        newNode.setParent(parent);
        
        if (key < parent.getData()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        
        return newNode;
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
        System.out.println("\n═══ EJERCICIO 4: INSERCIÓN COMO ABB ═══\n");
        
        Ejercicio04_InsercionBST tree = new Ejercicio04_InsercionBST();
        
        NodoRojinegro<Integer> n1 = tree.insertBST(50);
        System.out.println("Insertado: " + n1);
        
        NodoRojinegro<Integer> n2 = tree.insertBST(30);
        System.out.println("Insertado: " + n2);
        
        NodoRojinegro<Integer> n3 = tree.insertBST(70);
        System.out.println("Insertado: " + n3);
        
        System.out.println("\nÁrbol resultante (sin balance):");
        tree.display(tree.root, "", true);
    }
}
