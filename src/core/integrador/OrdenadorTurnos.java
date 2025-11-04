package core.integrador;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Random;

/**
 * Ejercicio 8: Reportes operativos con múltiples ordenamientos
 * 
 * Implementa tres algoritmos de ordenamiento con características distintas:
 * 1. Insertion Sort - Estable, O(n²), bueno para listas pequeñas/casi ordenadas
 * 2. Shell Sort - In-place, O(n^(3/2)), usa gap sequence estándar
 * 3. Quick Sort - O(n log n) promedio, pivote final (Lomuto)
 * 
 * Genera reportes ordenados por:
 * - Hora (Insertion Sort - estable, mantiene orden relativo)
 * - Duración (Shell Sort - gap sequence)
 * - Apellido de paciente (Quick Sort - pivote final)
 * 
 * Incluye herramientas para:
 * - Medir tiempos de ejecución con datasets sintéticos (1k, 10k, 50k turnos)
 * - Comparar rendimiento entre algoritmos
 * - Generar datos de prueba aleatorios
 */
public class OrdenadorTurnos {
    
    // ========== INSERTION SORT (ESTABLE) ==========
    
    /**
     * Insertion Sort - Algoritmo estable para ordenar por hora
     * 
     * Características:
     * - Estable: mantiene orden relativo de elementos iguales
     * - In-place: O(1) espacio adicional
     * - Adaptativo: O(n) si ya está ordenado
     * - Complejidad: O(n²) peor caso
     * 
     * Ideal para ordenar por hora porque:
     * - Mantiene estabilidad (turnos con misma hora conservan orden original)
     * - Eficiente para listas pequeñas o casi ordenadas
     * - Simple de implementar y debuggear
     * 
     * @param turnos Array de turnos a ordenar
     * @param comparador Criterio de comparación
     */
    public static void insertionSort(Turno[] turnos, Comparator<Turno> comparador) {
        if (turnos == null || turnos.length <= 1) return;
        
        for (int i = 1; i < turnos.length; i++) {
            Turno key = turnos[i];
            int j = i - 1;
            
            // Desplazar elementos mayores hacia la derecha
            while (j >= 0 && comparador.compare(turnos[j], key) > 0) {
                turnos[j + 1] = turnos[j];
                j--;
            }
            
            turnos[j + 1] = key;
        }
    }
    
    /**
     * Ordena turnos por hora usando Insertion Sort (estable)
     */
    public static void ordenarPorHora(Turno[] turnos) {
        insertionSort(turnos, Comparator.comparing(Turno::getFechaHora));
    }
    
    // ========== SHELL SORT (GAP SEQUENCE ESTÁNDAR) ==========
    
    /**
     * Shell Sort - Algoritmo eficiente para ordenar por duración
     * 
     * Características:
     * - Gap sequence: h = 3*h + 1 (1, 4, 13, 40, 121, 364, ...)
     * - In-place: O(1) espacio adicional
     * - No estable: puede cambiar orden relativo
     * - Complejidad: O(n^(3/2)) con esta gap sequence
     * 
     * Ideal para ordenar por duración porque:
     * - Más rápido que Insertion Sort en datasets medianos
     * - No requiere estabilidad (duración es criterio único)
     * - Gap sequence optimizada para reducir comparaciones
     * 
     * @param turnos Array de turnos a ordenar
     * @param comparador Criterio de comparación
     */
    public static void shellSort(Turno[] turnos, Comparator<Turno> comparador) {
        if (turnos == null || turnos.length <= 1) return;
        
        int n = turnos.length;
        
        // Calcular gap inicial usando secuencia de Knuth: h = 3*h + 1
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1; // 1, 4, 13, 40, 121, 364, 1093, ...
        }
        
        // Ordenar con gaps decrecientes
        while (h >= 1) {
            // h-sort el array (Insertion Sort con gap h)
            for (int i = h; i < n; i++) {
                Turno key = turnos[i];
                int j = i;
                
                // Desplazar elementos h posiciones hacia la derecha
                while (j >= h && comparador.compare(turnos[j - h], key) > 0) {
                    turnos[j] = turnos[j - h];
                    j -= h;
                }
                
                turnos[j] = key;
            }
            
            h = h / 3; // Reducir gap
        }
    }
    
    /**
     * Ordena turnos por duración usando Shell Sort
     */
    public static void ordenarPorDuracion(Turno[] turnos) {
        shellSort(turnos, Comparator.comparingInt(Turno::getDuracionMin));
    }
    
    // ========== QUICK SORT (LOMUTO - PIVOTE FINAL) ==========
    
    /**
     * Quick Sort - Algoritmo divide y vencerás para ordenar por apellido
     * 
     * Características:
     * - Pivote: último elemento (Lomuto partition)
     * - In-place: O(log n) espacio adicional (recursión)
     * - No estable: puede cambiar orden relativo
     * - Complejidad: O(n log n) promedio, O(n²) peor caso
     * 
     * Ideal para ordenar por apellido porque:
     * - Muy eficiente en promedio para datasets grandes
     * - Apellidos tienen buena distribución (pivote aleatorio efectivo)
     * - Cache-friendly (acceso secuencial en partición)
     * 
     * @param turnos Array de turnos a ordenar
     * @param comparador Criterio de comparación
     */
    public static void quickSort(Turno[] turnos, Comparator<Turno> comparador) {
        if (turnos == null || turnos.length <= 1) return;
        quickSortRecursivo(turnos, 0, turnos.length - 1, comparador);
    }
    
    private static void quickSortRecursivo(Turno[] turnos, int low, int high, 
                                           Comparator<Turno> comparador) {
        if (low < high) {
            int pivotIndex = particionLomuto(turnos, low, high, comparador);
            quickSortRecursivo(turnos, low, pivotIndex - 1, comparador);
            quickSortRecursivo(turnos, pivotIndex + 1, high, comparador);
        }
    }
    
    /**
     * Partición de Lomuto - Pivote en posición final
     * 
     * Esquema:
     * - Pivote = A[high] (último elemento)
     * - i rastrea la frontera de elementos menores
     * - j explora elementos no procesados
     * - Elementos <= pivote van a la izquierda de i
     * 
     * Invariante: [low..i] <= pivote, [i+1..j-1] > pivote, [j..high-1] no procesado
     */
    private static int particionLomuto(Turno[] turnos, int low, int high, 
                                       Comparator<Turno> comparador) {
        Turno pivot = turnos[high]; // Pivote final
        int i = low - 1; // Índice del último elemento menor al pivote
        
        for (int j = low; j < high; j++) {
            if (comparador.compare(turnos[j], pivot) <= 0) {
                i++;
                swap(turnos, i, j);
            }
        }
        
        // Colocar pivote en su posición final
        swap(turnos, i + 1, high);
        return i + 1;
    }
    
    private static void swap(Turno[] turnos, int i, int j) {
        Turno temp = turnos[i];
        turnos[i] = turnos[j];
        turnos[j] = temp;
    }
    
    /**
     * Ordena turnos por apellido del paciente usando Quick Sort
     * Nota: Requiere un mapa DNI -> Paciente para obtener apellidos
     */
    public static void ordenarPorApellidoPaciente(Turno[] turnos, 
                                                   MapaPacientes mapaPacientes) {
        quickSort(turnos, (t1, t2) -> {
            String apellido1 = extraerApellido(
                mapaPacientes.get(t1.getDniPaciente()).getNombre()
            );
            String apellido2 = extraerApellido(
                mapaPacientes.get(t2.getDniPaciente()).getNombre()
            );
            return apellido1.compareTo(apellido2);
        });
    }
    
    /**
     * Extrae el apellido de un nombre completo
     * Asume formato "Nombre Apellido" o "Apellido, Nombre"
     */
    private static String extraerApellido(String nombreCompleto) {
        if (nombreCompleto.contains(",")) {
            return nombreCompleto.split(",")[0].trim();
        }
        String[] partes = nombreCompleto.trim().split("\\s+");
        return partes[partes.length - 1]; // Último token = apellido
    }
    
    // ========== HERRAMIENTAS DE MEDICIÓN Y TESTING ==========
    
    /**
     * Resultado de una medición de rendimiento
     */
    public static class ResultadoMedicion {
        private String algoritmo;
        private int tamanioDataset;
        private long tiempoNanosegundos;
        private long tiempoMilisegundos;
        
        public ResultadoMedicion(String algoritmo, int tamanio, long nanos) {
            this.algoritmo = algoritmo;
            this.tamanioDataset = tamanio;
            this.tiempoNanosegundos = nanos;
            this.tiempoMilisegundos = nanos / 1_000_000;
        }
        
        public String getAlgoritmo() { return algoritmo; }
        public int getTamanioDataset() { return tamanioDataset; }
        public long getTiempoNanosegundos() { return tiempoNanosegundos; }
        public long getTiempoMilisegundos() { return tiempoMilisegundos; }
        
        @Override
        public String toString() {
            return String.format("%s [n=%d]: %d ms (%.2f µs/elemento)", 
                algoritmo, tamanioDataset, tiempoMilisegundos,
                tiempoNanosegundos / 1000.0 / tamanioDataset);
        }
    }
    
    /**
     * Mide el tiempo de ejecución de un algoritmo
     */
    public static ResultadoMedicion medirTiempo(String algoritmo, Turno[] turnos, 
                                                 Runnable ordenamiento) {
        long inicio = System.nanoTime();
        ordenamiento.run();
        long fin = System.nanoTime();
        return new ResultadoMedicion(algoritmo, turnos.length, fin - inicio);
    }
    
    /**
     * Genera dataset sintético de turnos aleatorios
     * 
     * @param cantidad Número de turnos a generar (1k, 10k, 50k)
     * @param seed Semilla para reproducibilidad
     * @return Array de turnos con datos sintéticos
     */
    public static Turno[] generarDatasetSintetico(int cantidad, long seed) {
        Random random = new Random(seed);
        Turno[] turnos = new Turno[cantidad];
        
        LocalDateTime baseDate = LocalDateTime.of(2024, 1, 1, 8, 0);
        String[] motivos = {"Consulta", "Control", "Urgencia", "Estudio", "Vacunación"};
        
        for (int i = 0; i < cantidad; i++) {
            String id = "T" + (i + 1);
            String dni = String.format("%08d", random.nextInt(100_000_000));
            String matricula = "M" + random.nextInt(10_000);
            
            // Fecha aleatoria en el rango de 30 días
            LocalDateTime fecha = baseDate.plusMinutes(random.nextInt(30 * 24 * 60));
            
            // Duración aleatoria: 15, 30, 45, 60 minutos
            int duracion = (random.nextInt(4) + 1) * 15;
            
            String motivo = motivos[random.nextInt(motivos.length)];
            
            turnos[i] = new Turno(id, dni, matricula, fecha, duracion, motivo);
        }
        
        return turnos;
    }
    
    /**
     * Genera mapa de pacientes sintético
     */
    public static MapaPacientes generarPacientesSinteticos(Turno[] turnos, long seed) {
        Random random = new Random(seed);
        MapaPacientes mapa = new IndicePacientes();
        
        String[] nombres = {"Juan", "María", "Carlos", "Ana", "Luis", "Laura"};
        String[] apellidos = {"García", "Rodríguez", "Martínez", "López", "González", "Pérez"};
        
        for (Turno turno : turnos) {
            String dni = turno.getDniPaciente();
            if (mapa.get(dni) == null) {
                String nombre = nombres[random.nextInt(nombres.length)];
                String apellido = apellidos[random.nextInt(apellidos.length)];
                mapa.put(dni, new Paciente(dni, nombre + " " + apellido));
            }
        }
        
        return mapa;
    }
    
    /**
     * Copia un array de turnos (para medir múltiples algoritmos con mismo input)
     */
    public static Turno[] copiarArray(Turno[] original) {
        Turno[] copia = new Turno[original.length];
        System.arraycopy(original, 0, copia, 0, original.length);
        return copia;
    }
    
    /**
     * Compara rendimiento de los tres algoritmos con diferentes tamaños
     */
    public static void compararAlgoritmos() {
        int[] tamanios = {1000, 10_000, 50_000};
        long seed = 42;
        
        System.out.println("=== COMPARACIÓN DE ALGORITMOS DE ORDENAMIENTO ===\n");
        
        for (int n : tamanios) {
            System.out.println("Dataset: " + n + " turnos");
            System.out.println("-".repeat(60));
            
            // Generar dataset
            Turno[] datasetOriginal = generarDatasetSintetico(n, seed);
            MapaPacientes pacientes = generarPacientesSinteticos(datasetOriginal, seed);
            
            // 1. Insertion Sort (por hora)
            Turno[] dataset1 = copiarArray(datasetOriginal);
            ResultadoMedicion r1 = medirTiempo("Insertion Sort (hora)", dataset1, 
                () -> ordenarPorHora(dataset1));
            System.out.println(r1);
            
            // 2. Shell Sort (por duración)
            Turno[] dataset2 = copiarArray(datasetOriginal);
            ResultadoMedicion r2 = medirTiempo("Shell Sort (duración)", dataset2, 
                () -> ordenarPorDuracion(dataset2));
            System.out.println(r2);
            
            // 3. Quick Sort (por apellido)
            Turno[] dataset3 = copiarArray(datasetOriginal);
            ResultadoMedicion r3 = medirTiempo("Quick Sort (apellido)", dataset3, 
                () -> ordenarPorApellidoPaciente(dataset3, pacientes));
            System.out.println(r3);
            
            System.out.println();
        }
        
        System.out.println("\n=== CARACTERÍSTICAS DE LOS ALGORITMOS ===");
        System.out.println("Insertion Sort:");
        System.out.println("  - Complejidad: O(n²) peor caso, O(n) mejor caso");
        System.out.println("  - Estable: SÍ (mantiene orden relativo)");
        System.out.println("  - Espacio: O(1) in-place");
        System.out.println("  - Uso: Listas pequeñas, casi ordenadas");
        
        System.out.println("\nShell Sort:");
        System.out.println("  - Complejidad: O(n^(3/2)) con gap sequence estándar");
        System.out.println("  - Estable: NO");
        System.out.println("  - Espacio: O(1) in-place");
        System.out.println("  - Uso: Datasets medianos, sin necesidad de estabilidad");
        
        System.out.println("\nQuick Sort (Lomuto):");
        System.out.println("  - Complejidad: O(n log n) promedio, O(n²) peor caso");
        System.out.println("  - Estable: NO");
        System.out.println("  - Espacio: O(log n) recursión");
        System.out.println("  - Uso: Datasets grandes, buena distribución de datos");
    }
    
    /**
     * Genera y muestra reportes de ejemplo
     */
    public static void generarReportesEjemplo() {
        System.out.println("=== REPORTES OPERATIVOS - EJEMPLO ===\n");
        
        // Dataset pequeño para visualización
        Turno[] turnos = generarDatasetSintetico(10, 123);
        MapaPacientes pacientes = generarPacientesSinteticos(turnos, 123);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        
        // Reporte 1: Por hora
        System.out.println("📅 REPORTE POR HORA (Insertion Sort - Estable)");
        System.out.println("-".repeat(70));
        Turno[] porHora = copiarArray(turnos);
        ordenarPorHora(porHora);
        for (Turno t : porHora) {
            System.out.printf("%-8s | %s | %3d min | %s%n", 
                t.getId(), 
                t.getFechaHora().format(formatter),
                t.getDuracionMin(),
                pacientes.get(t.getDniPaciente()).getNombre());
        }
        
        // Reporte 2: Por duración
        System.out.println("\n⏱️  REPORTE POR DURACIÓN (Shell Sort)");
        System.out.println("-".repeat(70));
        Turno[] porDuracion = copiarArray(turnos);
        ordenarPorDuracion(porDuracion);
        for (Turno t : porDuracion) {
            System.out.printf("%-8s | %3d min | %s | %s%n", 
                t.getId(),
                t.getDuracionMin(),
                t.getFechaHora().format(formatter),
                pacientes.get(t.getDniPaciente()).getNombre());
        }
        
        // Reporte 3: Por apellido
        System.out.println("\n👤 REPORTE POR APELLIDO (Quick Sort - Lomuto)");
        System.out.println("-".repeat(70));
        Turno[] porApellido = copiarArray(turnos);
        ordenarPorApellidoPaciente(porApellido, pacientes);
        for (Turno t : porApellido) {
            String nombreCompleto = pacientes.get(t.getDniPaciente()).getNombre();
            System.out.printf("%-20s | %-8s | %s | %3d min%n", 
                nombreCompleto,
                t.getId(),
                t.getFechaHora().format(formatter),
                t.getDuracionMin());
        }
    }
    
    // ========== MAIN PARA TESTING ==========
    
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════════════════╗");
        System.out.println("║  EJERCICIO 8: REPORTES OPERATIVOS CON ORDENAMIENTOS      ║");
        System.out.println("╚═══════════════════════════════════════════════════════════╝\n");
        
        // Mostrar reportes de ejemplo
        generarReportesEjemplo();
        
        System.out.println("\n" + "=".repeat(70) + "\n");
        
        // Comparar algoritmos con datasets grandes
        compararAlgoritmos();
    }
}
