package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 9: Seguimiento del estado interno
 * 
 * Extiende MonticuloBinario para mostrar el estado después de cada operación.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio09_SeguimientoEstado {
    
    static class MinHeapConSeguimiento extends MonticuloBinario<Integer> {
        
        @Override
        public void add(Integer valor) {
            super.add(valor);
            System.out.println("Después de add(" + valor + "):");
            System.out.println("  Array: " + getElements());
            System.out.println();
        }
        
        @Override
        public Integer poll() {
            if (isEmpty()) return null;
            Integer min = peek();
            super.poll();
            System.out.println("Después de poll() → eliminó " + min + ":");
            System.out.println("  Array: " + getElements());
            System.out.println();
            return min;
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 9: SEGUIMIENTO DE ESTADO ═══\n");
        
        MinHeapConSeguimiento heap = new MinHeapConSeguimiento();
        
        heap.add(10);
        heap.add(5);
        heap.add(15);
        heap.add(3);
        
        heap.poll();
        heap.poll();
        
        heap.add(7);
        heap.add(1);
    }
}
