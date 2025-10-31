package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 9: Eliminar duplicados
 * 
 * Implementa un método removeDuplicates() que recorra la lista y elimine los nodos repetidos.
 * - Ejemplo: [1 -> 2 -> 2 -> 3 -> 1] debe quedar [1 -> 2 -> 3].
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio09_EliminarDuplicados {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 9: ELIMINAR DUPLICADOS ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(1);
        lista.insertLast(2);
        lista.insertLast(2);
        lista.insertLast(3);
        lista.insertLast(1);
        
        System.out.println("Lista original: " + lista);
        
        lista.removeDuplicates();
        System.out.println("Sin duplicados: " + lista);
        
        System.out.println("Tamaño: " + lista.getSize());
    }
}
