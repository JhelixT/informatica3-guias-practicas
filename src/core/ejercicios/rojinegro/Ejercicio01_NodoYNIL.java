package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 1: Nodo y NIL sentinel
 * 
 * Definir RBNode con NIL negro único; inicializar root = NIL y todos los punteros vacíos apuntan a NIL.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio01_NodoYNIL {
    
    // NIL sentinel: nodo negro único compartido
    private static final NodoRojinegro<Integer> NIL = new NodoRojinegro<>(null, Color.NEGRO);
    private NodoRojinegro<Integer> root;
    
    public Ejercicio01_NodoYNIL() {
        this.root = NIL;
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 1: NODO Y NIL SENTINEL ═══\n");
        
        Ejercicio01_NodoYNIL tree = new Ejercicio01_NodoYNIL();
        
        System.out.println("Árbol inicializado con root = NIL");
        System.out.println("NIL es negro: " + NIL.isBlack());
        System.out.println("Root es NIL: " + (tree.root == NIL));
        
        // Crear un nodo y asignar NIL a sus hijos
        NodoRojinegro<Integer> node = new NodoRojinegro<>(10, Color.NEGRO);
        node.setLeft(NIL);
        node.setRight(NIL);
        node.setParent(NIL);
        
        System.out.println("\nNodo creado: " + node.getData() + " (color: " + node.getColor() + ")");
        System.out.println("Left es NIL: " + (node.getLeft() == NIL));
        System.out.println("Right es NIL: " + (node.getRight() == NIL));
        System.out.println("Parent es NIL: " + (node.getParent() == NIL));
    }
}
