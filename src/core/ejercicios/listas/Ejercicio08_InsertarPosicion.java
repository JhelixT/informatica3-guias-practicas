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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 8: INSERTAR EN POSICIÓN               ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Insertar elementos en posiciones específicas\n");
        
        // Crear lista inicial
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(1);
        lista.insertLast(2);
        lista.insertLast(4);
        
        System.out.println("Lista inicial:");
        System.out.println("   " + lista);
        System.out.println("   Posiciones: [0]=1, [1]=2, [2]=4");
        
        // Insertar en posición 2
        System.out.println("\n🔄 Insertando 3 en la posición 2...");
        lista.insertAt(3, 2);
        
        System.out.println("\nLista después de insertar:");
        System.out.println("   " + lista);
        System.out.println("   Posiciones: [0]=1, [1]=2, [2]=3, [3]=4");
        
        // Más ejemplos de inserción
        System.out.println("\n📝 Pruebas adicionales:");
        
        System.out.println("\n   Insertando 0 en la posición 0 (inicio)...");
        lista.insertAt(0, 0);
        System.out.println("   " + lista);
        
        System.out.println("\n   Insertando 5 en la posición " + lista.getSize() + " (final)...");
        lista.insertAt(5, lista.getSize());
        System.out.println("   " + lista);
        
        System.out.println("\n   Insertando 99 en posición intermedia (3)...");
        lista.insertAt(99, 3);
        System.out.println("   " + lista);
        
        // Probar inserción inválida
        System.out.println("\n   Intentando insertar en posición inválida (100)...");
        try {
            lista.insertAt(777, 100);
            System.out.println("   ✗ No se detectó el error");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("   ✓ Error detectado correctamente: " + e.getMessage());
        }
        
        // Mostrar estado final con índices
        System.out.println("\n📋 Estado final detallado:");
        for (int i = 0; i < lista.getSize(); i++) {
            System.out.println("   Posición [" + i + "]: " + lista.getAt(i));
        }
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (lista.getSize() == 7 &&
                           lista.getAt(0) == 0 &&
                           lista.getAt(1) == 1 &&
                           lista.getAt(2) == 2 &&
                           lista.getAt(3) == 99 &&
                           lista.getAt(4) == 3 &&
                           lista.getAt(5) == 4 &&
                           lista.getAt(6) == 5);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Inserción en posición arbitraria requiere O(n)");
    }
}
