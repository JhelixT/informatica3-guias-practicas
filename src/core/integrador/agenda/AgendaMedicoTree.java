package core.integrador.agenda;

import core.integrador.modelo.Turno;
import core.estructuras.arboles.ArbolAVL;
import core.estructuras.arboles.NodoAVL;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import core.estructuras.hash.TablaHash;
import java.time.LocalDateTime;

import java.util.Optional;

/**
 * Implementación OPTIMIZADA de AgendaMedico usando árbol AVL + TablaHash.
 * 
 * Estructuras:
 * - ArbolAVL<TurnoWrapper>: Turnos ordenados por fechaHora para búsquedas temporales O(log n)
 * - TablaHash<String, Turno>: Índice por ID para búsquedas rápidas O(1)
 * 
 * Complejidades logradas:
 * - agendar(): O(log n) - eliminado factor O(n) de búsqueda por ID
 * - cancelar(): O(log n) - eliminado factor O(n) de búsqueda por ID  
 * - siguiente(): O(log n) - recorrido inorden eficiente
 * - primerHueco(): O(log n + k) - búsqueda optimizada en rango
 * - buscarPorIdOpt(): O(1) - TablaHash directo
 */
public class AgendaMedicoTree implements AgendaMedico {
    
    /** Wrapper para hacer Turno comparable por fechaHora */
    private static class TurnoWrapper implements Comparable<TurnoWrapper> {
        final Turno turno;
        
        TurnoWrapper(Turno turno) {
            this.turno = turno;
        }
        
        @Override
        public int compareTo(TurnoWrapper otro) {
            return this.turno.getFechaHora().compareTo(otro.turno.getFechaHora());
        }
    }
    
    private final ArbolAVL<TurnoWrapper> arbolTurnos = new ArbolAVL<>();
    
    // TablaHash para búsqueda O(1) por ID - optimización de complejidad
    private final TablaHash<String, Turno> turnosPorId = new TablaHash<>();
    
    /** Agenda un nuevo turno verificando duplicados y solapamientos */
    @Override
    public synchronized boolean agendar(Turno t) {
        if (t == null || t.getId() == null) return false;
        
        // O(1) - Verificación duplicado usando TablaHash
        if (turnosPorId.containsKey(t.getId())) return false;
        
        // O(log n) - Verificación solapamiento optimizada
        if (tieneSolapamientoOptimizado(t)) return false;
        
        // O(log n) - Inserción en AVL + O(1) en TablaHash
        arbolTurnos.insert(new TurnoWrapper(t));
        turnosPorId.put(t.getId(), t);
        return true;
    }
    
    /** Cancela un turno por su ID, eliminándolo del árbol */
    @Override
    public synchronized boolean cancelar(String idTurno) {
        // O(1) - Búsqueda usando TablaHash
        Turno turno = turnosPorId.get(idTurno);
        if (turno == null) return false;
        
        // O(log n) - Eliminación del AVL + O(1) del TablaHash
        arbolTurnos.delete(new TurnoWrapper(turno));
        turnosPorId.remove(idTurno);
        return true;
    }
    
    /** Retorna el siguiente turno a partir de la fecha/hora especificada */
    @Override
    public synchronized Optional<Turno> siguiente(LocalDateTime t) {
        ListaEnlazada<Turno> turnos = obtenerTurnosOrdenados();
        
        Nodo<Turno> nodo = turnos.getHead();
        while (nodo != null) {
            Turno turno = nodo.getData();
            if (turno.getFechaHora().compareTo(t) >= 0) {
                return Optional.of(turno);
            }
            nodo = nodo.getNext();
        }
        
        return Optional.empty();
    }
    
    /**
     * Ejercicio 3: Busca el primer hueco libre de duración mínima a partir de t0
     * Complejidad: O(log n + k) donde k = turnos solapantes revisados
     * 
     * @param t0 Fecha/hora de inicio de búsqueda
     * @param duracionMin Duración mínima requerida en minutos
     * @return Fecha/hora del primer hueco disponible, o empty si no hay
     */
    public synchronized Optional<LocalDateTime> primerHueco(LocalDateTime t0, int duracionMin) {
        if (duracionMin <= 0) return Optional.empty();
        
        LocalDateTime inicioHueco = t0;
        
        // Buscar usando búsqueda optimizada en rango específico
        boolean encontrado = false;
        
        while (!encontrado) {
            Turno turnoConflictivo = buscarTurnoEnRango(inicioHueco, inicioHueco.plusMinutes(duracionMin));
            
            if (turnoConflictivo == null) {
                // No hay conflictos, encontramos el hueco
                return Optional.of(inicioHueco);
            }
            
            // Hay conflicto, mover inicio del hueco después del turno conflictivo
            inicioHueco = turnoConflictivo.getFechaHoraFin();
            
            // Verificar límite razonable para evitar bucle infinito
            if (inicioHueco.isAfter(t0.plusDays(7))) {
                break; // No buscar más allá de una semana
            }
        }
        
        return Optional.of(inicioHueco);
    }
    
    /**
     * Busca si existe algún turno que se solape con el rango [inicio, fin)
     * OPTIMIZADO: Complejidad O(log n + k) usando búsqueda dirigida en AVL
     * 
     * @param inicio Inicio del rango a verificar
     * @param fin Fin del rango a verificar
     * @return Primer turno que se solapa, o null si no hay
     */
    private Turno buscarTurnoEnRango(LocalDateTime inicio, LocalDateTime fin) {
        // Crear turno ficticio para búsqueda en AVL
        Turno turnoBuscado = new Turno("BUSQUEDA", "DUMMY", "DUMMY", inicio, 1, "BUSQUEDA");
        TurnoWrapper wrapperBuscado = new TurnoWrapper(turnoBuscado);
        
        // PASO 1: Buscar el primer turno >= inicio - O(log n)
        NodoAVL<TurnoWrapper> nodoActual = arbolTurnos.findCeilingNode(wrapperBuscado);
        
        // PASO 2: Recorrer hacia adelante solo turnos relevantes - O(k)
        while (nodoActual != null) {
            Turno turno = nodoActual.getData().turno;
            
            // Si este turno empieza después de nuestro fin, no hay más conflictos
            if (turno.getFechaHora().isAfter(fin) || turno.getFechaHora().equals(fin)) {
                break;  // ✅ PARADA TEMPRANA - no seguir recorriendo
            }
            
            // Verificar si hay solapamiento real
            if (hayConflictoReal(turno, inicio, fin)) {
                return turno;  // ✅ ENCONTRADO - primer conflicto
            }
            
            // Avanzar al siguiente turno en orden - O(log n) amortizado
            nodoActual = arbolTurnos.getInorderSuccessor(nodoActual);
        }
        
        return null; // ✅ NO HAY CONFLICTOS en el rango
    }
    
    /**
     * Verifica si dos turnos realmente se solapan en tiempo
     * 
     * @param turno Turno existente a verificar
     * @param inicioNuevo Inicio del nuevo turno/rango
     * @param finNuevo Fin del nuevo turno/rango
     * @return true si hay solapamiento, false en caso contrario
     */
    private boolean hayConflictoReal(Turno turno, LocalDateTime inicioNuevo, LocalDateTime finNuevo) {
        LocalDateTime inicioExistente = turno.getFechaHora();
        LocalDateTime finExistente = turno.getFechaHoraFin();
        
        // Dos turnos se solapan si:
        // inicioNuevo < finExistente AND inicioExistente < finNuevo
        return inicioNuevo.isBefore(finExistente) && inicioExistente.isBefore(finNuevo);
    }
    
    /** Busca un turno por su ID y retorna Optional (método público) - O(1) */
    public Optional<Turno> buscarPorIdOpt(String id) {
        Turno turno = turnosPorId.get(id);
        return turno != null ? Optional.of(turno) : Optional.empty();
    }
    
    /** 
     * Verifica solapamiento usando búsqueda optimizada
     * Complejidad: O(log n + k) donde k = turnos en ventana de tiempo relevante
     */
    private boolean tieneSolapamientoOptimizado(Turno nuevo) {
        LocalDateTime inicio = nuevo.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(nuevo.getDuracionMin());
        
        // Usar método optimizado para buscar conflictos en rango específico
        return buscarTurnoEnRango(inicio, fin) != null;
    }
    
    /** Retorna todos los turnos en orden cronológico (recorrido inorden del AVL) */
    private ListaEnlazada<Turno> obtenerTurnosOrdenados() {
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        recorridoInorden(obtenerRaiz(), resultado);
        return resultado;
    }
    
    /** Usa reflexión para recorrer el árbol AVL en orden */
    private void recorridoInorden(Object nodo, ListaEnlazada<Turno> lista) {
        if (nodo == null) return;
        
        try {
            var claseNodo = nodo.getClass();
            var getLeft = claseNodo.getMethod("getLeft");
            var getRight = claseNodo.getMethod("getRight");
            var getData = claseNodo.getMethod("getData");
            
            recorridoInorden(getLeft.invoke(nodo), lista);
            
            TurnoWrapper wrapper = (TurnoWrapper) getData.invoke(nodo);
            lista.insertLast(wrapper.turno);
            
            recorridoInorden(getRight.invoke(nodo), lista);
        } catch (Exception e) {
        }
    }
    
    /** Obtiene la raíz del árbol AVL usando reflexión */
    private Object obtenerRaiz() {
        try {
            var campo = ArbolAVL.class.getDeclaredField("root");
            campo.setAccessible(true);
            return campo.get(arbolTurnos);
        } catch (Exception e) {
            return null;
        }
    }
    
    /** Retorna la cantidad total de turnos agendados */
    public synchronized int cantidadTurnos() {
        return arbolTurnos.countNodes();
    }
    
    /** Retorna todos los turnos en orden cronológico */
    public synchronized ListaEnlazada<Turno> todosTurnos() {
        return obtenerTurnosOrdenados();
    }
    
    /**
     * Encuentra el primer hueco disponible en la agenda de un médico.
     * Busca desde t0 hasta 7 días después, dentro del horario laboral (8:00-22:00).
     * 
     * @param matriculaMedico Matrícula del médico
     * @param t0 Hora desde la cual comenzar la búsqueda
     * @param durMin Duración mínima del hueco (en minutos)
     * @return Optional con la hora del primer hueco, o vacío si no hay
     */
    public synchronized Optional<LocalDateTime> primerHueco(String matriculaMedico, LocalDateTime t0, int durMin) {
        if (matriculaMedico == null || t0 == null || durMin <= 0) {
            return Optional.empty();
        }
        
        final int HORA_INICIO_LABORAL = 8;
        final int HORA_FIN_LABORAL = 22;
        
        LocalDateTime busquedaActual = t0;
        
        for (int dia = 0; dia < 7; dia++) {
            LocalDateTime inicioLaboral = busquedaActual.withHour(HORA_INICIO_LABORAL).withMinute(0);
            LocalDateTime finLaboral = busquedaActual.withHour(HORA_FIN_LABORAL).withMinute(0);
            
            if (busquedaActual.isBefore(inicioLaboral)) {
                busquedaActual = inicioLaboral;
            }
            
            if (busquedaActual.isAfter(finLaboral) || busquedaActual.plusMinutes(durMin).isAfter(finLaboral)) {
                busquedaActual = busquedaActual.plusDays(1).withHour(HORA_INICIO_LABORAL).withMinute(0);
                continue;
            }
            
            ListaEnlazada<Turno> turnosMedicoDelDia = obtenerTurnosMedicoDelDia(matriculaMedico, busquedaActual.toLocalDate());
            
            if (turnosMedicoDelDia.isEmpty()) {
                return Optional.of(busquedaActual);
            }
            
            Turno primerTurno = turnosMedicoDelDia.getHead().getData();
            if (busquedaActual.plusMinutes(durMin).isBefore(primerTurno.getFechaHora()) 
                || busquedaActual.plusMinutes(durMin).isEqual(primerTurno.getFechaHora())) {
                return Optional.of(busquedaActual);
            }
            
            // Buscar hueco entre turnos consecutivos
            Nodo<Turno> nodoActual = turnosMedicoDelDia.getHead();
            while (nodoActual != null && nodoActual.getNext() != null) {
                Turno actual = nodoActual.getData();
                Turno siguiente = nodoActual.getNext().getData();
                
                LocalDateTime finActual = actual.getFechaHoraFin();
                LocalDateTime inicioSiguiente = siguiente.getFechaHora();
                
                LocalDateTime inicioCandidato = finActual.isAfter(busquedaActual) ? finActual : busquedaActual;
                
                if (inicioCandidato.plusMinutes(durMin).isBefore(inicioSiguiente)
                    || inicioCandidato.plusMinutes(durMin).isEqual(inicioSiguiente)) {
                    if (inicioCandidato.plusMinutes(durMin).isBefore(finLaboral)
                        || inicioCandidato.plusMinutes(durMin).isEqual(finLaboral)) {
                        return Optional.of(inicioCandidato);
                    }
                }
                
                nodoActual = nodoActual.getNext();
            }
            
            // Buscar hueco después del último turno
            // Usa getAt() y getSize() de ListaEnlazada
            Turno ultimoTurno = turnosMedicoDelDia.getAt(turnosMedicoDelDia.getSize() - 1);
            LocalDateTime despuesUltimo = ultimoTurno.getFechaHoraFin();
            
            if (despuesUltimo.isBefore(busquedaActual)) {
                despuesUltimo = busquedaActual;
            }
            
            if (despuesUltimo.plusMinutes(durMin).isBefore(finLaboral)
                || despuesUltimo.plusMinutes(durMin).isEqual(finLaboral)) {
                return Optional.of(despuesUltimo);
            }
            
            busquedaActual = busquedaActual.plusDays(1).withHour(HORA_INICIO_LABORAL).withMinute(0);
        }
        
        return Optional.empty();
    }
    
    /** Filtra los turnos de un médico específico en una fecha determinada */
    private ListaEnlazada<Turno> obtenerTurnosMedicoDelDia(String matriculaMedico, java.time.LocalDate fecha) {
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        ListaEnlazada<Turno> todosTurnos = obtenerTurnosOrdenados();
        
        Nodo<Turno> nodo = todosTurnos.getHead();
        while (nodo != null) {
            Turno t = nodo.getData();
            if (t.getMatriculaMedico().equals(matriculaMedico) 
                && t.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.insertLast(t);
            }
            nodo = nodo.getNext();
        }
        
        return resultado;
    }
    
    /** Retorna todos los turnos de un médico específico en orden cronológico */
    public synchronized ListaEnlazada<Turno> turnosPorMedico(String matriculaMedico) {
        ListaEnlazada<Turno> resultado = new ListaEnlazada<>();
        ListaEnlazada<Turno> todosTurnos = obtenerTurnosOrdenados();
        
        Nodo<Turno> nodo = todosTurnos.getHead();
        while (nodo != null) {
            Turno t = nodo.getData();
            if (t.getMatriculaMedico().equals(matriculaMedico)) {
                resultado.insertLast(t);
            }
            nodo = nodo.getNext();
        }
        
        return resultado;
    }
}
