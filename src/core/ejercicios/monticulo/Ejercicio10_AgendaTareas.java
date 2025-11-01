package core.ejercicios.monticulo;

import core.estructuras.monticulo.MonticuloBinario;
import java.util.Scanner;

/**
 * Ejercicio 10: Agenda de tareas con prioridad
 * 
 * Sistema completo de gestión de tareas usando MonticuloBinario.
 * 
 * @author JhelixT
 * @version 1.0
 */
public class Ejercicio10_AgendaTareas {
    
    static class Tarea implements Comparable<Tarea> {
        String descripcion;
        int prioridad; // menor número = más urgente
        
        public Tarea(String descripcion, int prioridad) {
            this.descripcion = descripcion;
            this.prioridad = prioridad;
        }
        
        @Override
        public int compareTo(Tarea otra) {
            return Integer.compare(this.prioridad, otra.prioridad);
        }
        
        @Override
        public String toString() {
            return "[P" + prioridad + "] " + descripcion;
        }
    }
    
    static class AgendaTareas {
        private MonticuloBinario<Tarea> heap;
        
        public AgendaTareas() {
            heap = new MonticuloBinario<>();
        }
        
        public void agregar(Tarea tarea) {
            heap.add(tarea);
        }
        
        public Tarea verProxima() {
            return heap.isEmpty() ? null : heap.peek();
        }
        
        public Tarea completar() {
            return heap.isEmpty() ? null : heap.poll();
        }
        
        public void mostrarTodas() {
            if (heap.isEmpty()) {
                System.out.println("No hay tareas pendientes");
                return;
            }
            
            // Crear copia para mostrar sin modificar original
            MonticuloBinario<Tarea> copia = new MonticuloBinario<>();
            for (Tarea t : heap.getElements()) {
                copia.add(t);
            }
            
            System.out.println("\nTareas pendientes (en orden de prioridad):");
            int num = 1;
            while (!copia.isEmpty()) {
                System.out.println("  " + num++ + ". " + copia.poll());
            }
        }
        
        public void mostrarHeap() {
            if (heap.isEmpty()) {
                System.out.println("Heap vacío");
                return;
            }
            
            System.out.println("\nEstructura del heap:");
            heap.display();
        }
        
        public boolean isEmpty() {
            return heap.isEmpty();
        }
    }
    
    public static void ejecutar() {
        System.out.println("\n═══ EJERCICIO 10: AGENDA DE TAREAS ═══\n");
        
        AgendaTareas agenda = new AgendaTareas();
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        
        while (continuar) {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Agregar tarea");
            System.out.println("2. Ver próxima tarea urgente");
            System.out.println("3. Completar tarea más urgente");
            System.out.println("4. Mostrar todas las tareas");
            System.out.println("5. Mostrar heap visual");
            System.out.println("0. Salir");
            System.out.print("Opción: ");
            
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir newline
            
            switch (opcion) {
                case 1:
                    System.out.print("Descripción de la tarea: ");
                    String desc = scanner.nextLine();
                    System.out.print("Prioridad (1=urgente, 5=baja): ");
                    int prio = scanner.nextInt();
                    agenda.agregar(new Tarea(desc, prio));
                    System.out.println("✓ Tarea agregada");
                    agenda.mostrarHeap();
                    break;
                    
                case 2:
                    Tarea proxima = agenda.verProxima();
                    if (proxima != null) {
                        System.out.println("Próxima tarea: " + proxima);
                    } else {
                        System.out.println("No hay tareas pendientes");
                    }
                    break;
                    
                case 3:
                    Tarea completada = agenda.completar();
                    if (completada != null) {
                        System.out.println("✓ Completada: " + completada);
                        agenda.mostrarHeap();
                    } else {
                        System.out.println("No hay tareas pendientes");
                    }
                    break;
                    
                case 4:
                    agenda.mostrarTodas();
                    break;
                    
                case 5:
                    agenda.mostrarHeap();
                    break;
                    
                case 0:
                    continuar = false;
                    System.out.println("Saliendo de la agenda...");
                    break;
                    
                default:
                    System.out.println("Opción no válida");
            }
        }
    }
}
