package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 6: Contar elementos
 * 
 * Implementa el método getSize() que devuelva la cantidad de nodos en la lista.
 * - Para [1 -> 2 -> 3 -> 4 -> 5], el resultado debe ser 5.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio06_ContarElementos {
    
    public static void ejecutar() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 6: CONTAR ELEMENTOS                    ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Contar la cantidad de elementos en la lista\n");
        
        // Crear lista vacía
        ListaEnlazada<Integer> lista = new ListaEnlazada<>();
        
        System.out.println("Lista inicial (vacía):");
        System.out.println("   " + lista);
        System.out.println("   Cantidad de elementos: " + lista.getSize());
        
        // Agregar elementos uno por uno
        System.out.println("\n🔄 Agregando elementos...");
        
        for (int i = 1; i <= 5; i++) {
            lista.insertLast(i);
            System.out.println("\n   Agregado: " + i);
            System.out.println("   Lista: " + lista);
            System.out.println("   Cantidad: " + lista.getSize() + " elementos");
        }
        
        // Eliminar elementos
        System.out.println("\n🗑️  Eliminando elementos...");
        
        lista.removeFirst();
        System.out.println("\n   Eliminado primer elemento");
        System.out.println("   Lista: " + lista);
        System.out.println("   Cantidad: " + lista.getSize() + " elementos");
        
        lista.removeLast();
        System.out.println("\n   Eliminado último elemento");
        System.out.println("   Lista: " + lista);
        System.out.println("   Cantidad: " + lista.getSize() + " elementos");
        
        // Limpiar lista
        System.out.println("\n🧹 Limpiando toda la lista...");
        lista.clear();
        System.out.println("   Lista: " + lista);
        System.out.println("   Cantidad: " + lista.getSize() + " elementos");
        
        // Crear lista de prueba final
        System.out.println("\n📋 Prueba final:");
        ListaEnlazada<Integer> listaFinal = new ListaEnlazada<>();
        for (int i = 1; i <= 5; i++) {
            listaFinal.insertLast(i);
        }
        
        System.out.println("   Lista: " + listaFinal);
        System.out.println("   Cantidad esperada: 5");
        System.out.println("   Cantidad obtenida: " + listaFinal.getSize());
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (listaFinal.getSize() == 5 &&
                           lista.getSize() == 0);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Mantener un contador evita recorrer la lista O(1) vs O(n)");
    }
}
