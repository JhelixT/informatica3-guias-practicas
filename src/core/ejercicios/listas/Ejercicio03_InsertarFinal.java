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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 3: INSERTAR AL FINAL                   ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Insertar elementos al final de la lista\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        System.out.println("Estado inicial:");
        System.out.println("   " + lista);
        
        System.out.println("\n🔄 Insertando valores al final...");
        
        System.out.println("\n   Insertando 1...");
        lista.insertLast(1);
        System.out.println("   Estado actual: " + lista);
        
        System.out.println("\n   Insertando 2...");
        lista.insertLast(2);
        System.out.println("   Estado actual: " + lista);
        
        System.out.println("\n   Insertando 3...");
        lista.insertLast(3);
        System.out.println("   Estado actual: " + lista);
        
        // Explicación
        System.out.println("\n📖 Explicación:");
        System.out.println("   Al insertar al FINAL, cada elemento se agrega");
        System.out.println("   después del último elemento existente.");
        System.out.println("   Por eso el orden se mantiene: 1 → 2 → 3");
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        System.out.println("   Orden esperado: [1 -> 2 -> 3]");
        System.out.println("   Orden obtenido: " + lista);
        
        boolean correcto = (lista.getSize() == 3 &&
                           lista.getAt(0) == 1 &&
                           lista.getAt(1) == 2 &&
                           lista.getAt(2) == 3);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Inserción al final requiere recorrer la lista O(n)");
    }
}
