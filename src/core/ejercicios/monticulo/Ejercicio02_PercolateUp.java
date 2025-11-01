package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 2: Implementar percolateUp
 * 
 * Extiende MonticuloBinario para mostrar cada intercambio durante la inserción.
 * Sobreescribe el método add para añadir trazas educativas.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio02_PercolateUp {
    
    static class MinHeapConTrazas extends MonticuloBinario<Integer> {
        
        public MinHeapConTrazas() {
            super();
        }
        
        @Override
        public void add(Integer valor) {
            int posicionInicial = size() + 1;
            
            System.out.println("\n→ Insertando " + valor + " en posición " + posicionInicial);
            super.add(valor);
            
            System.out.println("  Estado del heap: " + getElements());
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 2: PERCOLATE UP ═══\n");
        System.out.println("Demostrando el proceso de percolate up durante inserciones");
        
        MinHeapConTrazas heap = new MinHeapConTrazas();
        int[] valores = {20, 5, 15, 3};
        
        for (int v : valores) {
            heap.add(v);
        }
        
        System.out.println("\n✓ Heap final: " + heap.getElements());
    }
}
