package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 6: Contar elementos
 * 
 * Implementa el método getSize() que devuelva la cantidad de nodos en la lista.
 * - Para [1 -> 2 -> 3 -> 4 -> 5], el resultado debe ser 5.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio06_ContarElementos {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 6: CONTAR ELEMENTOS ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        for (int i = 1; i <= 5; i++) {
            lista.insertLast(i);
        }
        
        System.out.println("Lista: " + lista);
        System.out.println("Cantidad de elementos: " + lista.getSize());
    }
}
