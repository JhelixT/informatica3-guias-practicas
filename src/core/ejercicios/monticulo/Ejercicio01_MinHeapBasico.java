package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;
import core.estructuras.monticulo.MonticuloBinario.TipoMonticulo;

/**
 * Ejercicio 1: Crear estructura básica de MinHeap
 * 
 * Demostración de operaciones básicas: add, poll, peek, isEmpty.
 * Utiliza la implementación de MonticuloBinario.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio01_MinHeapBasico {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 1: MIN-HEAP BÁSICO ═══\n");
        
        MonticuloBinario<Integer> heap = new MonticuloBinario<>(TipoMonticulo.MIN_HEAP);
        int[] valores = {20, 5, 15, 3, 11};
        
        System.out.println("Insertando: 20, 5, 15, 3, 11");
        for (int v : valores) {
            heap.add(v);
        }
        
        System.out.println("Mínimo actual: " + heap.peek());
        System.out.println("Tamaño: " + heap.size());
        
        System.out.println("\nExtracción en orden:");
        while (!heap.isEmpty()) {
            System.out.println("  → " + heap.poll());
        }
    }
}
