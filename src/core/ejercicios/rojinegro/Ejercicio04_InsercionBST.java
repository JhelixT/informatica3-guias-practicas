package core.ejercicios.rojinegro;

import core.estructuras.arboles.ArbolBST;
import core.estructuras.arboles.ArbolRojinegro;

/**
 * Ejercicio 4: Inserción como ABB (sin balance) vs Rojinegro (con balance)
 * 
 * Demuestra la diferencia entre inserción BST sin balance y el auto-balanceo de Rojinegro.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio04_InsercionBST {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 4: INSERCIÓN BST vs ROJINEGRO ═══\n");
        
        int[] valores = {50, 30, 70, 20, 40, 60, 80};
        
        // Paso 1: Inserción como BST (sin balance)
        System.out.println("--- PASO 1: INSERCIÓN COMO BST (SIN BALANCE) ---\n");
        ArbolBST<Integer> bst = new ArbolBST<>();
        
        for (int v : valores) {
            bst.insert(v);
        }
        
        System.out.println("Valores insertados: 50, 30, 70, 20, 40, 60, 80");
        System.out.println("\nÁrbol BST (sin auto-balance):");
        bst.display();
        System.out.println("Altura BST: " + bst.getHeight());
        System.out.println("Inorden: " + bst.inOrderTraversal());
        
        // Paso 2: Inserción en Rojinegro (con balance automático)
        System.out.println("\n--- PASO 2: INSERCIÓN EN ROJINEGRO (CON BALANCE) ---\n");
        ArbolRojinegro<Integer> rojinegro = new ArbolRojinegro<>();
        
        for (int v : valores) {
            rojinegro.insert(v);
        }
        
        System.out.println("Mismos valores insertados: 50, 30, 70, 20, 40, 60, 80");
        System.out.println("\nÁrbol Rojinegro (auto-balanceado):");
        rojinegro.display();
        System.out.println("Altura Rojinegro: " + rojinegro.getHeight());
        System.out.println("Inorden: " + rojinegro.inOrderTraversal());
        
        // Comparación
        System.out.println("\n--- COMPARACIÓN ---");
        System.out.println("BST mantiene orden pero puede desbalancearse");
        System.out.println("Rojinegro mantiene orden Y garantiza balance O(log n)");
    }
}
