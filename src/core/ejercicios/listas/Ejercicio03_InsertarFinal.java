package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 3: Insertar al final
 * 
 * Agrega a la clase ListaEnlazada el método insertLast(int dato).
 * - Inserta los valores 1, 2, 3.
 * - Imprime la lista y verifica que se agregan en orden.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio03_InsertarFinal {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 3: INSERTAR AL FINAL ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        lista.insertLast(1);
        lista.insertLast(2);
        lista.insertLast(3);
        
        System.out.println("Lista (insertado al final): " + lista);
        System.out.println("Tamaño: " + lista.getSize());
    }
}
