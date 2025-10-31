package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 5: Buscar un valor
 * 
 * Crea un método buscar(int valor) que recorra la lista y devuelva true si encuentra el nodo.
 * - Prueba con la lista [5 -> 15 -> 25 -> 35].
 * - Busca el 25 (debe devolver true) y el 100 (debe devolver false).
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio05_BuscarValor {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 5: BUSCAR UN VALOR ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(5);
        lista.insertLast(15);
        lista.insertLast(25);
        lista.insertLast(35);
        
        System.out.println("Lista: " + lista);
        System.out.println("¿Contiene 25?: " + lista.contains(25));
        System.out.println("¿Contiene 100?: " + lista.contains(100));
        System.out.println("Posición de 25: " + lista.search(25));
    }
}
