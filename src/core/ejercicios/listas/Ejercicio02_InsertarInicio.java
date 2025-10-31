package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 2: Insertar al inicio
 * 
 * Implementa el método insertFirst(int dato) en la clase ListaEnlazada.
 * - Prueba insertando los valores: 10, 20, 30.
 * - Imprime la lista para verificar que el orden sea correcto.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio02_InsertarInicio {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 2: INSERTAR AL INICIO ═══\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        lista.insertFirst(10);
        lista.insertFirst(20);
        lista.insertFirst(30);
        
        System.out.println("Lista (insertado al inicio): " + lista);
        System.out.println("Tamaño: " + lista.getSize());
    }
}
