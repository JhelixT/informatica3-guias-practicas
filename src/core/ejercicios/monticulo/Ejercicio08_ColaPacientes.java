package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;

/**
 * Ejercicio 8: Cola de prioridad de pacientes
 * 
 * Sistema de atención médica usando MonticuloBinario.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio08_ColaPacientes {
    
    static class Paciente implements Comparable<Paciente> {
        String nombre;
        int prioridad; // 1 = alta, 2 = media, 3 = baja
        
        public Paciente(String nombre, int prioridad) {
            this.nombre = nombre;
            this.prioridad = prioridad;
        }
        
        @Override
        public int compareTo(Paciente otro) {
            return Integer.compare(this.prioridad, otro.prioridad);
        }
        
        @Override
        public String toString() {
            String nivel = switch (prioridad) {
                case 1 -> "Alta";
                case 2 -> "Media";
                case 3 -> "Baja";
                default -> "Desconocida";
            };
            return nombre + " (Prioridad " + nivel + ")";
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 8: COLA DE PACIENTES ═══\n");
        
        MonticuloBinario<Paciente> cola = new MonticuloBinario<>();
        
        Paciente[] pacientes = {
            new Paciente("Juan", 2),
            new Paciente("María", 1),
            new Paciente("Pedro", 3),
            new Paciente("Ana", 1),
            new Paciente("Luis", 2)
        };
        
        System.out.println("Ingreso de pacientes:");
        for (Paciente p : pacientes) {
            System.out.println("  → " + p);
            cola.add(p);
        }
        
        System.out.println("\nOrden de atención (por prioridad):");
        int orden = 1;
        while (!cola.isEmpty()) {
            System.out.println("  " + orden++ + ". " + cola.poll());
        }
    }
}
