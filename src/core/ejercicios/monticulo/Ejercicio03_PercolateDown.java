package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 3: Implementar percolateDown
 * 
 * Extiende MonticuloBinario para mostrar el estado antes y después de cada eliminación.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio03_PercolateDown {
    
    static class MinHeapConTrazas extends MonticuloBinario<Integer> {
        
        @Override
        public Integer poll() {
            if (isEmpty()) throw new IllegalStateException("Heap vacío");
            
            Integer min = peek();
            System.out.println("\n→ Eliminando raíz: " + min);
            System.out.println("  Antes:   " + getElements());
            
            super.poll();
            
            System.out.println("  Después: " + getElements());
            
            return min;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 3: PERCOLATE DOWN ═══\n");
        
        MinHeapConTrazas heap = new MinHeapConTrazas();
        int[] valores = {20, 5, 15, 3, 11};
        
        System.out.println("Insertando: 20, 5, 15, 3, 11");
        for (int v : valores) {
            heap.add(v);
        }
        
        System.out.println("\nExtrayendo 3 elementos:");
        for (int i = 0; i < 3; i++) {
            heap.poll();
        }
    }
}
