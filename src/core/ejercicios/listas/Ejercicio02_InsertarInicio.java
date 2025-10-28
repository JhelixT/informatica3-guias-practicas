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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 2: INSERTAR AL INICIO                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Insertar elementos al inicio de la lista\n");
        
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        System.out.println("Estado inicial:");
        System.out.println("   " + lista);
        
        System.out.println("\n🔄 Insertando valores al inicio...");
        
        System.out.println("\n   Insertando 10...");
        lista.insertFirst(10);
        System.out.println("   Estado actual: " + lista);
        
        System.out.println("\n   Insertando 20...");
        lista.insertFirst(20);
        System.out.println("   Estado actual: " + lista);
        
        System.out.println("\n   Insertando 30...");
        lista.insertFirst(30);
        System.out.println("   Estado actual: " + lista);
        
        // Explicación
        System.out.println("\n📖 Explicación:");
        System.out.println("   Al insertar al INICIO, el último elemento insertado");
        System.out.println("   queda en la primera posición.");
        System.out.println("   Por eso el orden es: 30 → 20 → 10");
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        System.out.println("   Orden esperado: [30 -> 20 -> 10]");
        System.out.println("   Orden obtenido: " + lista);
        
        boolean correcto = (lista.getSize() == 3 &&
                           lista.getAt(0) == 30 &&
                           lista.getAt(1) == 20 &&
                           lista.getAt(2) == 10);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Inserción al inicio tiene complejidad O(1)");
    }
}
