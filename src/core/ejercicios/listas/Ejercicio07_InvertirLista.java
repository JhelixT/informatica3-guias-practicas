package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 7: Invertir la lista
 * 
 * Escribe un método reverse() que invierta el orden de los nodos en la lista.
 * - Ejemplo: [10 -> 20 -> 30 -> 40] debe transformarse en [40 -> 30 -> 20 -> 10].
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio07_InvertirLista {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 7: INVERTIR LA LISTA ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(10);
        lista.insertLast(20);
        lista.insertLast(30);
        lista.insertLast(40);
        
        System.out.println("Lista original: " + lista);
        
        lista.reverse();
        System.out.println("Lista invertida: " + lista);
    }
}
