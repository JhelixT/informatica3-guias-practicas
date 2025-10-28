package core.ejercicios.listas;

import core.estructuras.listas.ListaEnlazada;

/**
 * Ejercicio 9: Eliminar duplicados
 * 
 * Implementa un método removeDuplicates() que recorra la lista y elimine los nodos repetidos.
 * - Ejemplo: [1 -> 2 -> 2 -> 3 -> 1] debe quedar [1 -> 2 -> 3].
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio09_EliminarDuplicados {
    
    public static void ejecutar() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 9: ELIMINAR DUPLICADOS                 ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Eliminar elementos duplicados de la lista\n");
        
        // Caso 1: Lista con duplicados
        System.out.println("📝 Caso 1: Lista con varios duplicados");
        ListaEnlazada<Integer> lista1 = new ListaEnlazada<>();
        lista1.insertLast(1);
        lista1.insertLast(2);
        lista1.insertLast(2);
        lista1.insertLast(3);
        lista1.insertLast(1);
        
        System.out.println("   Lista original: " + lista1);
        System.out.println("   Tamaño: " + lista1.getSize());
        
        lista1.removeDuplicates();
        
        System.out.println("   Lista sin duplicados: " + lista1);
        System.out.println("   Tamaño: " + lista1.getSize());
        System.out.println("   Esperado: [1 -> 2 -> 3]");
        
        // Caso 2: Lista con todos duplicados
        System.out.println("\n📝 Caso 2: Lista con todos duplicados");
        ListaEnlazada<Integer> lista2 = new ListaEnlazada<>();
        lista2.insertLast(5);
        lista2.insertLast(5);
        lista2.insertLast(5);
        lista2.insertLast(5);
        
        System.out.println("   Lista original: " + lista2);
        System.out.println("   Tamaño: " + lista2.getSize());
        
        lista2.removeDuplicates();
        
        System.out.println("   Lista sin duplicados: " + lista2);
        System.out.println("   Tamaño: " + lista2.getSize());
        System.out.println("   Esperado: [5]");
        
        // Caso 3: Lista sin duplicados
        System.out.println("\n📝 Caso 3: Lista sin duplicados");
        ListaEnlazada<Integer> lista3 = new ListaEnlazada<>();
        lista3.insertLast(10);
        lista3.insertLast(20);
        lista3.insertLast(30);
        
        System.out.println("   Lista original: " + lista3);
        System.out.println("   Tamaño: " + lista3.getSize());
        
        lista3.removeDuplicates();
        
        System.out.println("   Lista sin duplicados: " + lista3);
        System.out.println("   Tamaño: " + lista3.getSize());
        System.out.println("   Esperado: [10 -> 20 -> 30]");
        
        // Caso 4: Lista vacía
        System.out.println("\n📝 Caso 4: Lista vacía");
        ListaEnlazada<Integer> lista4 = new ListaEnlazada<>();
        
        System.out.println("   Lista original: " + lista4);
        lista4.removeDuplicates();
        System.out.println("   Lista sin duplicados: " + lista4);
        
        // Caso 5: Lista compleja
        System.out.println("\n📝 Caso 5: Lista compleja con múltiples duplicados");
        ListaEnlazada<Integer> lista5 = new ListaEnlazada<>();
        int[] valores = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5};
        for (int v : valores) {
            lista5.insertLast(v);
        }
        
        System.out.println("   Lista original: " + lista5);
        System.out.println("   Tamaño: " + lista5.getSize());
        
        lista5.removeDuplicates();
        
        System.out.println("   Lista sin duplicados: " + lista5);
        System.out.println("   Tamaño: " + lista5.getSize());
        System.out.println("   Esperado: [3 -> 1 -> 4 -> 5 -> 9 -> 2 -> 6]");
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (lista1.getSize() == 3 &&
                           lista2.getSize() == 1 &&
                           lista3.getSize() == 3 &&
                           lista5.getSize() == 7);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Eliminar duplicados requiere O(n²) o usar estructuras auxiliares");
    }
}
