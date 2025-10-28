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
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 4: ELIMINAR POR VALOR                  ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Eliminar la primera ocurrencia de un valor\n");
        
        // Crear la lista inicial
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        lista.insertLast(10);
        lista.insertLast(20);
        lista.insertLast(30);
        lista.insertLast(40);
        
        System.out.println("Lista inicial:");
        System.out.println("   " + lista);
        
        // Eliminar el valor 30
        System.out.println("\n🗑️  Eliminando el valor 30...");
        boolean eliminado = lista.remove(30);
        
        if (eliminado) {
            System.out.println("   ✓ Elemento eliminado exitosamente");
        } else {
            System.out.println("   ✗ No se pudo eliminar el elemento");
        }
        
        System.out.println("\nLista después de eliminar:");
        System.out.println("   " + lista);
        
        // Pruebas adicionales
        System.out.println("\n📝 Pruebas adicionales:");
        
        System.out.println("\n   Intentando eliminar el 50 (no existe)...");
        boolean eliminado2 = lista.remove(50);
        System.out.println("   Resultado: " + (eliminado2 ? "Eliminado" : "No encontrado ✓"));
        System.out.println("   Lista actual: " + lista);
        
        System.out.println("\n   Eliminando el 10 (primer elemento)...");
        lista.remove(10);
        System.out.println("   Lista actual: " + lista);
        
        System.out.println("\n   Eliminando el 40 (último elemento)...");
        lista.remove(40);
        System.out.println("   Lista actual: " + lista);
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        System.out.println("   Lista esperada final: [20]");
        System.out.println("   Lista obtenida: " + lista);
        
        boolean correcto = (lista.getSize() == 1 && lista.getAt(0) == 20);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Eliminación por valor requiere buscar el elemento O(n)");
    }
}
