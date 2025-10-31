package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 4: Eliminar por valor
 * 
 * Implementa el método remove(int valor) que elimine el primer nodo que contenga ese valor.
 * - Prueba con la lista [10 -> 20 -> 30 -> 40] eliminando el 30.
 * - Verifica el resultado: [10 -> 20 -> 40].
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio04_EliminarPorValor {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 4: ELIMINAR POR VALOR ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(10);
        lista.insertLast(20);
        lista.insertLast(30);
        lista.insertLast(40);
        
        System.out.println("Lista inicial: " + lista);
        
        lista.remove(30);
        System.out.println("Después de eliminar 30: " + lista);
        
        System.out.println("Tamaño: " + lista.getSize());
    }
}
