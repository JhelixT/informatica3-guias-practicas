package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 4: Mostrar el heap en forma de árbol
 * 
 * Utiliza el método display() de MonticuloBinario para visualización.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio04_MostrarArbol {
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 4: MOSTRAR ÁRBOL ═══\n");
        
        MonticuloBinario<Integer> heap = new MonticuloBinario<>();
        int[] valores = {20, 5, 15, 3, 11};
        
        for (int v : valores) {
            System.out.println("→ Insertando " + v + ":");
            heap.add(v);
            heap.display();
            System.out.println();
        }
    }
}
