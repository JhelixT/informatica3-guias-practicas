package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 6: Recoloreo por tío rojo
 * 
 * Tío rojo: padre y tío → negros, abuelo → rojo; continuar desde el abuelo.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio06_RecoloreoTioRojo {
    
    private NodoRojinegro<Integer> root;
    
    public void recoloreoTioRojo(NodoRojinegro<Integer> z) {
        NodoRojinegro<Integer> p = z.getParent();
        NodoRojinegro<Integer> g = z.getGrandparent();
        NodoRojinegro<Integer> tio = z.getUncle();
        
        // Recolorear: padre y tío → negros, abuelo → rojo
        p.setColor(Color.NEGRO);
        tio.setColor(Color.NEGRO);
        g.setColor(Color.ROJO);
        
        // Asegurar que la raíz sea negra
        if (g == root) {
            g.setColor(Color.NEGRO);
        }
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
        System.out.println("\n═══ EJERCICIO 6: RECOLOREO POR TÍO ROJO ═══\n");
        
        Ejercicio06_RecoloreoTioRojo tree = new Ejercicio06_RecoloreoTioRojo();
        
        // Crear árbol con tío rojo
        NodoRojinegro<Integer> g = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> tio = new NodoRojinegro<>(40, Color.ROJO);
        NodoRojinegro<Integer> z = new NodoRojinegro<>(10, Color.ROJO);
        
        tree.root = g;
        g.setLeft(p);
        g.setRight(tio);
        p.setParent(g);
        tio.setParent(g);
        p.setLeft(z);
        z.setParent(p);
        
        System.out.println("Antes del recoloreo:");
        tree.display(tree.root, "", true);
        
        tree.recoloreoTioRojo(z);
        
        System.out.println("\nDespués del recoloreo:");
        tree.display(tree.root, "", true);
    }
}
