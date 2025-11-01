package core.ejercicios.rojinegro;

import core.estructuras.arboles.ArbolRojinegro;

/**
 * Ejercicio 10: Verificadores de invariantes
 * 
 * Verificadores: raizNegra, sinRojoRojo y alturaNegra.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio10_VerificadoresInvariantes {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 10: VERIFICADORES DE INVARIANTES ═══\n");
        
        ArbolRojinegro<Integer> arbol = new ArbolRojinegro<>();
        
        System.out.println("Insertando valores: 10, 20, 30, 40, 50");
        arbol.insert(10);
        arbol.insert(20);
        arbol.insert(30);
        arbol.insert(40);
        arbol.insert(50);
        
        System.out.println("\nÁrbol: " + arbol);
        arbol.display();
        
        System.out.println("\n--- Verificación de invariantes ---");
        System.out.println("¿Es árbol rojinegro válido? " + arbol.isValidRedBlackTree());
        System.out.println("Altura negra: " + arbol.getBlackHeight());
        System.out.println("Nodos rojos: " + arbol.countRedNodes());
        System.out.println("Nodos negros: " + arbol.countBlackNodes());
        
        System.out.println("\n--- Más inserciones ---");
        arbol.insert(25);
        arbol.insert(35);
        arbol.insert(5);
        
        System.out.println("\nÁrbol actualizado: " + arbol);
        System.out.println("¿Es árbol rojinegro válido? " + arbol.isValidRedBlackTree());
        System.out.println("Altura negra: " + arbol.getBlackHeight());
    }
}
