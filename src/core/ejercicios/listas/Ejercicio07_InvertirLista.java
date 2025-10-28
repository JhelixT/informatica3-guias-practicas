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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 7: INVERTIR LA LISTA                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Invertir el orden de los elementos\n");
        
        // Crear la lista inicial
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(10);
        lista.insertLast(20);
        lista.insertLast(30);
        lista.insertLast(40);
        
        System.out.println("Lista original:");
        System.out.println("   " + lista);
        
        // Invertir la lista
        System.out.println("\n🔄 Invirtiendo la lista...");
        lista.reverse();
        
        System.out.println("\nLista invertida:");
        System.out.println("   " + lista);
        
        // Verificar el orden
        System.out.println("\n📖 Verificación elemento por elemento:");
        System.out.println("   Posición 0: " + lista.getAt(0) + " (esperado: 40)");
        System.out.println("   Posición 1: " + lista.getAt(1) + " (esperado: 30)");
        System.out.println("   Posición 2: " + lista.getAt(2) + " (esperado: 20)");
        System.out.println("   Posición 3: " + lista.getAt(3) + " (esperado: 10)");
        
        // Pruebas adicionales
        System.out.println("\n📝 Pruebas adicionales:");
        
        System.out.println("\n   Invirtiendo nuevamente (debería volver al original)...");
        lista.reverse();
        System.out.println("   " + lista);
        
        System.out.println("\n   Probando con lista de 1 elemento...");
        ListaEnlazada<Integer> lista1 = new ListaEnlazada<>();
        lista1.insertLast(99);
        System.out.println("   Antes: " + lista1);
        lista1.reverse();
        System.out.println("   Después: " + lista1);
        
        System.out.println("\n   Probando con lista vacía...");
        ListaEnlazada<Integer> listaVacia = new ListaEnlazada<>();
        System.out.println("   Antes: " + listaVacia);
        listaVacia.reverse();
        System.out.println("   Después: " + listaVacia);
        
        // Verificación final
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (lista.getAt(0) == 10 &&
                           lista.getAt(1) == 20 &&
                           lista.getAt(2) == 30 &&
                           lista.getAt(3) == 40);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Invertir lista cambia punteros, no copia elementos O(n)");
    }
}
