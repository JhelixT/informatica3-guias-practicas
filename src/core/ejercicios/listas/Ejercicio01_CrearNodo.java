package core.ejercicios.listas;

import core.estructuras.nodos.Nodo;

/**
 * Ejercicio 1: Crear un nodo
 * 
 * Escribe una clase Nodo que almacene un número entero y un puntero al siguiente nodo.
 * - Implementa un programa que cree tres nodos y los enlace manualmente.
 * - Imprime la lista completa.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio01_CrearNodo {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 1: CREAR UN NODO ═══\n");
        
        Nodo<Integer> nodo1 = new Nodo<>(10);
        Nodo<Integer> nodo2 = new Nodo<>(20);
        Nodo<Integer> nodo3 = new Nodo<>(30);
        
        nodo1.setNext(nodo2);
        nodo2.setNext(nodo3);
        
        System.out.print("Lista: ");
        Nodo<Integer> current = nodo1;
        while (current != null) {
            System.out.print(current.getData());
            if (current.getNext() != null) System.out.print(" → ");
            current = current.getNext();
        }
        System.out.println();
    }
}
