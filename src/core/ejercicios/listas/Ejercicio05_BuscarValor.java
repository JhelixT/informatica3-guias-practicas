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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 5: BUSCAR UN VALOR                     ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Buscar si un valor existe en la lista\n");
        
        // Crear la lista inicial
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(5);
        lista.insertLast(15);
        lista.insertLast(25);
        lista.insertLast(35);
        
        System.out.println("Lista de prueba:");
        System.out.println("   " + lista);
        
        // Búsqueda 1: Elemento que existe
        System.out.println("\n🔍 Búsqueda 1: Buscando el valor 25...");
        boolean encontrado1 = lista.contains(25);
        int posicion1 = lista.search(25);
        
        System.out.println("   ¿Encontrado?: " + (encontrado1 ? "SÍ ✓" : "NO ✗"));
        if (encontrado1) {
            System.out.println("   Posición: " + posicion1);
        }
        
        // Búsqueda 2: Elemento que no existe
        System.out.println("\n🔍 Búsqueda 2: Buscando el valor 100...");
        boolean encontrado2 = lista.contains(100);
        int posicion2 = lista.search(100);
        
        System.out.println("   ¿Encontrado?: " + (encontrado2 ? "SÍ ✗" : "NO ✓"));
        System.out.println("   Posición: " + posicion2 + " (indica no encontrado)");
        
        // Búsquedas adicionales
        System.out.println("\n📝 Búsquedas adicionales:");
        
        System.out.println("\n   Buscando el 5 (primer elemento)...");
        System.out.println("   ¿Encontrado?: " + (lista.contains(5) ? "SÍ ✓" : "NO ✗"));
        System.out.println("   Posición: " + lista.search(5));
        
        System.out.println("\n   Buscando el 35 (último elemento)...");
        System.out.println("   ¿Encontrado?: " + (lista.contains(35) ? "SÍ ✓" : "NO ✗"));
        System.out.println("   Posición: " + lista.search(35));
        
        System.out.println("\n   Buscando el 15 (elemento intermedio)...");
        System.out.println("   ¿Encontrado?: " + (lista.contains(15) ? "SÍ ✓" : "NO ✗"));
        System.out.println("   Posición: " + lista.search(15));
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (encontrado1 == true && 
                           encontrado2 == false &&
                           posicion1 == 2 &&
                           posicion2 == -1);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: La búsqueda lineal tiene complejidad O(n)");
    }
}
