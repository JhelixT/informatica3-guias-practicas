package core.ejercicios.listas;

import core.estructuras.nodos.Nodo;

/**
 * Ejercicio 1: Crear un nodo
 * 
 * Escribe una clase Nodo que almacene un número entero y un puntero al siguiente nodo.
 * - Implementa un programa que cree tres nodos y los enlace manualmente.
 * - Imprime la lista completa.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio01_CrearNodo {
    
    public static void ejecutar() {
        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          EJERCICIO 1: CREAR UN NODO                       ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        
        System.out.println("📌 Objetivo: Crear tres nodos y enlazarlos manualmente\n");
        
        // Crear tres nodos
        System.out.println("Creando nodos...");
        Nodo<Integer> nodo1 = new Nodo<>(10);
        Nodo<Integer> nodo2 = new Nodo<>(20);
        Nodo<Integer> nodo3 = new Nodo<>(30);
        
        System.out.println("✓ Nodo 1 creado con valor: " + nodo1.getData());
        System.out.println("✓ Nodo 2 creado con valor: " + nodo2.getData());
        System.out.println("✓ Nodo 3 creado con valor: " + nodo3.getData());
        
        // Enlazar los nodos manualmente
        System.out.println("\nEnlazando nodos...");
        nodo1.setNext(nodo2);
        nodo2.setNext(nodo3);
        nodo3.setNext(null);
        
        System.out.println("✓ Nodo 1 → Nodo 2");
        System.out.println("✓ Nodo 2 → Nodo 3");
        System.out.println("✓ Nodo 3 → null");
        
        // Imprimir la lista completa
        System.out.println("\n📋 Lista enlazada completa:");
        System.out.print("   ");
        Nodo<Integer> current = nodo1;
        while (current != null) {
            System.out.print("[" + current.getData() + "]");
            if (current.getNext() != null) {
                System.out.print(" → ");
            }
            current = current.getNext();
        }
        System.out.println(" → null");
        
        // Verificación
        System.out.println("\n🔍 Verificación:");
        boolean correcto = (nodo1.getData() == 10 && 
                           nodo2.getData() == 20 && 
                           nodo3.getData() == 30 &&
                           nodo1.getNext() == nodo2 &&
                           nodo2.getNext() == nodo3 &&
                           nodo3.getNext() == null);
        
        if (correcto) {
            System.out.println("   ✅ EJERCICIO COMPLETADO CORRECTAMENTE");
        } else {
            System.out.println("   ❌ ERROR EN EL EJERCICIO");
        }
        
        System.out.println("\n💡 Concepto aprendido: Estructura básica de nodos y enlaces");
    }
}
