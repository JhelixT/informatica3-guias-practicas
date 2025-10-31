package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 8: Insertar en posición
 * 
 * Crea un método insertAt(int pos, int valor) que inserte un nodo en la posición indicada (0 = inicio).
 * - Ejemplo: en [1 -> 2 -> 4], al insertar 3 en la posición 2, debe quedar [1 -> 2 -> 3 -> 4].
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio08_InsertarPosicion {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 8: INSERTAR EN POSICIÓN ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(1);
        lista.insertLast(2);
        lista.insertLast(4);
        
        System.out.println("Lista inicial: " + lista);
        
        lista.insertAt(3, 2);
        System.out.println("Insertando 3 en posición 2: " + lista);
        
        System.out.println("Tamaño: " + lista.getSize());
    }
}
