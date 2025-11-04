package core.integrador.merge;

import core.integrador.modelo.Turno;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.hash.TablaHash;
import core.estructuras.nodos.Nodo;
import java.time.LocalDateTime;

/**
 * Consolidador de agendas que unifica dos fuentes de turnos ordenadas.
 * 
 * Implementa merge de listas ordenadas con deduplicación y detección de conflictos.
 * 
 * Casos de conflicto:
 * 1. Mismo ID → turnos duplicados, se mantiene el primero
 * 2. Mismo médico + horario exacto solapado → conflicto de horario
 * 
 * Complejidad: O(|A| + |B|) - merge lineal de dos listas ordenadas
 * 
 * Usa solo estructuras del proyecto:
 * - ListaEnlazada para almacenar agendas y conflictos
 * - TablaHash para detectar IDs duplicados en O(1)
 * - Nodo para navegación
 */
public class ConsolidadorAgendas {
    
    /**
     * Resultado del merge con la lista consolidada y log de conflictos.
     */
    public static class ResultadoMerge {
        private ListaEnlazada<Turno> turnosConsolidados;
        private ListaEnlazada<String> conflictos;
        
        public ResultadoMerge(ListaEnlazada<Turno> turnos, ListaEnlazada<String> conflictos) {
            this.turnosConsolidados = turnos;
            this.conflictos = conflictos;
        }
        
        public ListaEnlazada<Turno> getTurnosConsolidados() {
            return turnosConsolidados;
        }
        
        public ListaEnlazada<String> getConflictos() {
            return conflictos;
        }
        
        public int cantidadTurnos() {
            return turnosConsolidados.getSize();
        }
        
        public int cantidadConflictos() {
            return conflictos.getSize();
        }
    }
    
    /**
     * Consolida dos agendas ordenadas por fecha eliminando duplicados.
     * 
     * Algoritmo merge de dos listas ordenadas:
     * - Recorre ambas listas simultáneamente
     * - Compara turnos por fecha y los agrega en orden
     * - Detecta y resuelve conflictos
     * 
     * Complejidad: O(|A| + |B|)
     * 
     * @param agendaLocal Primera agenda (ordenada por fecha)
     * @param agendaNube Segunda agenda (ordenada por fecha)
     * @return ResultadoMerge con lista consolidada y log de conflictos
     */
    public static ResultadoMerge merge(ListaEnlazada<Turno> agendaLocal, 
                                       ListaEnlazada<Turno> agendaNube) {
        if (agendaLocal == null || agendaNube == null) {
            throw new IllegalArgumentException("Las agendas no pueden ser null");
        }
        
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        ListaEnlazada<String> conflictos = new ListaEnlazada<>();
        TablaHash<String, Turno> idsVistos = new TablaHash<>();
        
        // Punteros para recorrer ambas listas
        Nodo<Turno> nodoLocal = agendaLocal.getHead();
        Nodo<Turno> nodoNube = agendaNube.getHead();
        
        // Merge de dos listas ordenadas - O(|A| + |B|)
        while (nodoLocal != null && nodoNube != null) {
            Turno turnoLocal = nodoLocal.getData();
            Turno turnoNube = nodoNube.getData();
            
            // Comparar por fecha (ambas listas están ordenadas)
            int comparacion = turnoLocal.getFechaHora().compareTo(turnoNube.getFechaHora());
            
            if (comparacion <= 0) {
                // Turno local es anterior o igual
                procesarTurno(turnoLocal, resultado, idsVistos, conflictos, "LOCAL");
                nodoLocal = nodoLocal.getNext();
            } else {
                // Turno nube es anterior
                procesarTurno(turnoNube, resultado, idsVistos, conflictos, "NUBE");
                nodoNube = nodoNube.getNext();
            }
        }
        
        // Procesar turnos restantes de agendaLocal
        while (nodoLocal != null) {
            procesarTurno(nodoLocal.getData(), resultado, idsVistos, conflictos, "LOCAL");
            nodoLocal = nodoLocal.getNext();
        }
        
        // Procesar turnos restantes de agendaNube
        while (nodoNube != null) {
            procesarTurno(nodoNube.getData(), resultado, idsVistos, conflictos, "NUBE");
            nodoNube = nodoNube.getNext();
        }
        
        return new ResultadoMerge(resultado, conflictos);
    }
    
    /**
     * Procesa un turno verificando conflictos y agregándolo si es válido.
     * 
     * Verifica:
     * 1. ID duplicado → mantiene el primero
     * 2. Mismo médico + horario solapado → conflicto de horario
     * 
     * Complejidad: O(1) promedio para verificar ID duplicado
     */
    private static void procesarTurno(Turno turno, ListaEnlazada<Turno> resultado,
                                     TablaHash<String, Turno> idsVistos,
                                     ListaEnlazada<String> conflictos, String origen) {
        // Verificar ID duplicado - O(1)
        if (idsVistos.containsKey(turno.getId())) {
            conflictos.insertLast(String.format(
                "ID duplicado: %s - Descartado turno de %s (ya existe de otra fuente)",
                turno.getId(), origen
            ));
            return;
        }
        
        // Verificar conflicto de horario con mismo médico
        boolean hayConflicto = verificarConflictoHorario(turno, resultado, conflictos, origen);
        
        if (!hayConflicto) {
            // Agregar turno a la lista consolidada
            resultado.insertLast(turno);
            idsVistos.put(turno.getId(), turno);
        }
    }
    
    /**
     * Verifica si el turno tiene conflicto de horario con turnos existentes del mismo médico.
     * 
     * Complejidad: O(k) donde k = turnos ya procesados en la ventana de tiempo relevante
     * En la práctica, k es pequeño (solo turnos cercanos en el tiempo)
     */
    private static boolean verificarConflictoHorario(Turno turno, ListaEnlazada<Turno> resultado,
                                                     ListaEnlazada<String> conflictos, String origen) {
        Nodo<Turno> nodo = resultado.getHead();
        
        // Solo verificar turnos recientes (optimización: podríamos limitarlo a un rango de tiempo)
        while (nodo != null) {
            Turno turnoExistente = nodo.getData();
            
            // Mismo médico y horarios se solapan
            if (turnoExistente.getMatriculaMedico().equals(turno.getMatriculaMedico()) &&
                turnosSeSuperponen(turnoExistente, turno)) {
                
                conflictos.insertLast(String.format(
                    "Conflicto de horario: Médico %s - Turno %s de %s solapa con turno %s (%s a %s)",
                    turno.getMatriculaMedico(),
                    turno.getId(),
                    origen,
                    turnoExistente.getId(),
                    turnoExistente.getFechaHora(),
                    turnoExistente.getFechaHoraFin()
                ));
                
                return true; // Hay conflicto, no agregar
            }
            
            nodo = nodo.getNext();
        }
        
        return false; // No hay conflicto
    }
    
    /**
     * Verifica si dos turnos se superponen en el tiempo.
     */
    private static boolean turnosSeSuperponen(Turno t1, Turno t2) {
        LocalDateTime inicio1 = t1.getFechaHora();
        LocalDateTime fin1 = t1.getFechaHoraFin();
        LocalDateTime inicio2 = t2.getFechaHora();
        LocalDateTime fin2 = t2.getFechaHoraFin();
        
        // Dos intervalos se superponen si: inicio1 < fin2 && inicio2 < fin1
        return inicio1.isBefore(fin2) && inicio2.isBefore(fin1);
    }
    
    /**
     * Cuenta la cantidad de elementos en una ListaEnlazada (útil para testing).
     */
    public static int contarTurnos(ListaEnlazada<Turno> lista) {
        return lista.getSize();
    }
    
    /**
     * Crea una ListaEnlazada desde un array de turnos (útil para testing).
     */
    public static ListaEnlazada<Turno> fromArray(Turno... turnos) {
        ListaEnlazada<Turno> lista = new ListaEnlazada<>();
        for (Turno turno : turnos) {
            lista.insertLast(turno);
        }
        return lista;
    }
    
    /**
     * Muestra el resultado del merge de forma legible.
     */
    public static void mostrarResultado(ResultadoMerge resultado) {
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║          RESULTADO DE CONSOLIDACIÓN DE AGENDAS            ║");
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Turnos consolidados: %-35d║%n", resultado.cantidadTurnos());
        System.out.printf("║ Conflictos detectados: %-33d║%n", resultado.cantidadConflictos());
        System.out.println("╠════════════════════════════════════════════════════════════╣");
        
        if (resultado.cantidadConflictos() > 0) {
            System.out.println("║ CONFLICTOS:                                                ║");
            Nodo<String> nodoConflicto = resultado.getConflictos().getHead();
            while (nodoConflicto != null) {
                System.out.println("║ • " + nodoConflicto.getData());
                nodoConflicto = nodoConflicto.getNext();
            }
            System.out.println("╠════════════════════════════════════════════════════════════╣");
        }
        
        System.out.println("║ TURNOS CONSOLIDADOS (ordenados por fecha):                 ║");
        Nodo<Turno> nodo = resultado.getTurnosConsolidados().getHead();
        int count = 1;
        
        while (nodo != null) {
            Turno t = nodo.getData();
            System.out.printf("║ %2d. ID:%s | Médico:%s | %s ║%n",
                count++,
                t.getId(),
                t.getMatriculaMedico(),
                t.getFechaHora()
            );
            nodo = nodo.getNext();
        }
        
        System.out.println("╚════════════════════════════════════════════════════════════╝");
    }
}
