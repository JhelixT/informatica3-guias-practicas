package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;
import core.estructuras.monticulo.MonticuloBinario.TipoMonticulo;

/**
 * Ejercicio 7: Implementar MaxHeap
 * 
 * Montículo que mantiene el mayor valor en la raíz.
 * Utiliza MonticuloBinario con tipo MAX_HEAP.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio07_MaxHeap {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 7: MAX-HEAP ═══\n");
        
        MonticuloBinario<Integer> heap = new MonticuloBinario<>(TipoMonticulo.MAX_HEAP);
        int[] valores = {10, 3, 15, 8, 6, 12};
        
        System.out.print("Insertando: ");
        for (int v : valores) {
            System.out.print(v + " ");
            heap.add(v);
        }
        
        System.out.println("\n\nEstado del Max-Heap:");
        heap.display();
        
        System.out.println("\nExtracción de mayor a menor:");
        while (!heap.isEmpty()) {
            System.out.println("  → " + heap.poll());
        }
    }
}
