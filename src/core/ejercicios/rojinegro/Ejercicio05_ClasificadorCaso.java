package core.ejercicios.rojinegro;

import core.estructuras.arboles.NodoRojinegro;
import core.estructuras.arboles.NodoRojinegro.Color;

/**
 * Ejercicio 5: Clasificador de caso para fixInsert
 * 
 * Dado z, p, g, clasificar: TIO_ROJO, LL, RR, LR o RL para decidir recoloreo o rotación.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio05_ClasificadorCaso {
    
    public enum CasoInsert {
        TIO_ROJO, LL, RR, LR, RL
    }
    
    public static CasoInsert clasificar(NodoRojinegro<Integer> z) {
        NodoRojinegro<Integer> p = z.getParent();
        NodoRojinegro<Integer> g = z.getGrandparent();
        NodoRojinegro<Integer> tio = z.getUncle();
        
        // Caso 1: Tío rojo
        if (tio != null && tio.isRed()) {
            return CasoInsert.TIO_ROJO;
        }
        
        // Casos de rotación
        if (p == g.getLeft()) {
            if (z == p.getLeft()) {
                return CasoInsert.LL;
            } else {
                return CasoInsert.LR;
            }
        } else {
            if (z == p.getRight()) {
                return CasoInsert.RR;
            } else {
                return CasoInsert.RL;
            }
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 5: CLASIFICADOR DE CASO ═══\n");
        
        // Caso LL
        NodoRojinegro<Integer> g1 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p1 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> z1 = new NodoRojinegro<>(10, Color.ROJO);
        g1.setLeft(p1);
        p1.setParent(g1);
        p1.setLeft(z1);
        z1.setParent(p1);
        System.out.println("Caso LL (g=30, p=20, z=10): " + clasificar(z1));
        
        // Caso LR
        NodoRojinegro<Integer> g2 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p2 = new NodoRojinegro<>(10, Color.ROJO);
        NodoRojinegro<Integer> z2 = new NodoRojinegro<>(20, Color.ROJO);
        g2.setLeft(p2);
        p2.setParent(g2);
        p2.setRight(z2);
        z2.setParent(p2);
        System.out.println("Caso LR (g=30, p=10, z=20): " + clasificar(z2));
        
        // Caso RR
        NodoRojinegro<Integer> g3 = new NodoRojinegro<>(10, Color.NEGRO);
        NodoRojinegro<Integer> p3 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> z3 = new NodoRojinegro<>(30, Color.ROJO);
        g3.setRight(p3);
        p3.setParent(g3);
        p3.setRight(z3);
        z3.setParent(p3);
        System.out.println("Caso RR (g=10, p=20, z=30): " + clasificar(z3));
        
        // Caso RL
        NodoRojinegro<Integer> g4 = new NodoRojinegro<>(10, Color.NEGRO);
        NodoRojinegro<Integer> p4 = new NodoRojinegro<>(30, Color.ROJO);
        NodoRojinegro<Integer> z4 = new NodoRojinegro<>(20, Color.ROJO);
        g4.setRight(p4);
        p4.setParent(g4);
        p4.setLeft(z4);
        z4.setParent(p4);
        System.out.println("Caso RL (g=10, p=30, z=20): " + clasificar(z4));
        
        // Caso TIO_ROJO
        NodoRojinegro<Integer> g5 = new NodoRojinegro<>(30, Color.NEGRO);
        NodoRojinegro<Integer> p5 = new NodoRojinegro<>(20, Color.ROJO);
        NodoRojinegro<Integer> tio = new NodoRojinegro<>(40, Color.ROJO);
        NodoRojinegro<Integer> z5 = new NodoRojinegro<>(10, Color.ROJO);
        g5.setLeft(p5);
        g5.setRight(tio);
        p5.setParent(g5);
        tio.setParent(g5);
        p5.setLeft(z5);
        z5.setParent(p5);
        System.out.println("Caso TIO_ROJO (g=30, p=20, tio=40, z=10): " + clasificar(z5));
    }
}
