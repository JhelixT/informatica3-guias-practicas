package core.integrador;

import core.estructuras.arboles.ArbolAVL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de AgendaMedico usando árbol AVL.
 * Ordena turnos por fechaHora con operaciones O(log n).
 */
public class AgendaMedicoTree implements AgendaMedico {
    
    /** Wrapper para hacer Turno comparable por fechaHora en el AVL */
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
    
    @Override
    public synchronized boolean agendar(Turno t) {
        if (t == null || t.getId() == null) return false;
        
        // Verificar ID duplicado
        if (buscarPorId(t.getId()) != null) return false;
        
        // Verificar solapamiento
        if (tieneSolapamiento(t)) return false;
        
        // Insertar en AVL
        arbolTurnos.insert(new TurnoWrapper(t));
        return true;
    }
    
    @Override
    public synchronized boolean cancelar(String idTurno) {
        Turno turno = buscarPorId(idTurno);
        if (turno == null) return false;
        
        arbolTurnos.delete(new TurnoWrapper(turno));
        return true;
    }
    
    @Override
    public synchronized Optional<Turno> siguiente(LocalDateTime t) {
        List<Turno> turnos = obtenerTurnosOrdenados();
        
        for (Turno turno : turnos) {
            if (turno.getFechaHora().compareTo(t) >= 0) {
                return Optional.of(turno);
            }
        }
        
        return Optional.empty();
    }
    
    /** Busca un turno por ID (O(n)) */
    private Turno buscarPorId(String id) {
        for (Turno turno : obtenerTurnosOrdenados()) {
            if (turno.getId().equals(id)) return turno;
        }
        return null;
    }
    
    /** Verifica si hay solapamiento de horarios */
    private boolean tieneSolapamiento(Turno nuevo) {
        LocalDateTime inicio = nuevo.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(nuevo.getDuracionMin());
        
        for (Turno existente : obtenerTurnosOrdenados()) {
            LocalDateTime inicioExist = existente.getFechaHora();
            LocalDateTime finExist = inicioExist.plusMinutes(existente.getDuracionMin());
            
            // Solapamiento: inicio1 < fin2 Y inicio2 < fin1
            if (inicio.isBefore(finExist) && inicioExist.isBefore(fin)) {
                return true;
            }
        }
        return false;
    }
    
    /** Obtiene todos los turnos en orden cronológico (inorden del AVL) */
    private List<Turno> obtenerTurnosOrdenados() {
        List<Turno> resultado = new ArrayList<>();
        recorridoInorden(obtenerRaiz(), resultado);
        return resultado;
    }
    
    /** Recorrido inorden recursivo */
    private void recorridoInorden(Object nodo, List<Turno> lista) {
        if (nodo == null) return;
        
        try {
            var claseNodo = nodo.getClass();
            var getLeft = claseNodo.getMethod("getLeft");
            var getRight = claseNodo.getMethod("getRight");
            var getData = claseNodo.getMethod("getData");
            
            recorridoInorden(getLeft.invoke(nodo), lista);
            
            TurnoWrapper wrapper = (TurnoWrapper) getData.invoke(nodo);
            lista.add(wrapper.turno);
            
            recorridoInorden(getRight.invoke(nodo), lista);
        } catch (Exception e) {
            // Ignorar errores de reflexión
        }
    }
    
    /** Obtiene la raíz del árbol usando reflexión */
    private Object obtenerRaiz() {
        try {
            var campo = ArbolAVL.class.getDeclaredField("root");
            campo.setAccessible(true);
            return campo.get(arbolTurnos);
        } catch (Exception e) {
            return null;
        }
    }
    
    /** Cantidad de turnos agendados */
    public synchronized int cantidadTurnos() {
        return arbolTurnos.countNodes();
    }
    
    /** Todos los turnos en orden cronológico */
    public synchronized List<Turno> todosTurnos() {
        return obtenerTurnosOrdenados();
    }
    
    /**
     * Busca el primer hueco libre de al menos durMin minutos a partir de t0
     * para un médico específico.
     * Complejidad: O(log n + k) donde k es la cantidad de turnos del médico examinados.
     * 
     * @param matriculaMedico Matrícula del médico para buscar huecos en su agenda
     * @param t0 Hora desde la cual comenzar la búsqueda
     * @param durMin Duración mínima del hueco requerido (en minutos)
     * @return Optional con la hora del primer hueco disponible, o vacío si no hay
     */
    public synchronized Optional<LocalDateTime> primerHueco(String matriculaMedico, LocalDateTime t0, int durMin) {
        if (matriculaMedico == null || t0 == null || durMin <= 0) {
            return Optional.empty();
        }
        
        // Definir horario laboral (ajusta según necesidad)
        final int HORA_INICIO_LABORAL = 8;  // 08:00
        final int HORA_FIN_LABORAL = 22;     // 22:00
        
        LocalDateTime busquedaActual = t0;
        
        // Intentar hasta 7 días (una semana)
        for (int dia = 0; dia < 7; dia++) {
            // Asegurar que estamos dentro del horario laboral
            LocalDateTime inicioLaboral = busquedaActual.withHour(HORA_INICIO_LABORAL).withMinute(0);
            LocalDateTime finLaboral = busquedaActual.withHour(HORA_FIN_LABORAL).withMinute(0);
            
            // Si la búsqueda actual es antes del inicio laboral, ajustar
            if (busquedaActual.isBefore(inicioLaboral)) {
                busquedaActual = inicioLaboral;
            }
            
            // Si ya pasó el horario laboral, ir al día siguiente
            if (busquedaActual.isAfter(finLaboral) || busquedaActual.plusMinutes(durMin).isAfter(finLaboral)) {
                busquedaActual = busquedaActual.plusDays(1).withHour(HORA_INICIO_LABORAL).withMinute(0);
                continue;
            }
            
            // Obtener solo los turnos del médico específico en este día
            List<Turno> turnosMedicoDelDia = obtenerTurnosMedicoDelDia(matriculaMedico, busquedaActual.toLocalDate());
            
            // Si no hay turnos del médico en el día, el hueco es desde busquedaActual
            if (turnosMedicoDelDia.isEmpty()) {
                return Optional.of(busquedaActual);
            }
            
            // Verificar hueco antes del primer turno del médico
            Turno primerTurno = turnosMedicoDelDia.get(0);
            if (busquedaActual.plusMinutes(durMin).isBefore(primerTurno.getFechaHora()) 
                || busquedaActual.plusMinutes(durMin).isEqual(primerTurno.getFechaHora())) {
                return Optional.of(busquedaActual);
            }
            
            // Buscar hueco entre turnos consecutivos del médico
            for (int i = 0; i < turnosMedicoDelDia.size() - 1; i++) {
                Turno actual = turnosMedicoDelDia.get(i);
                Turno siguiente = turnosMedicoDelDia.get(i + 1);
                
                LocalDateTime finActual = actual.getFechaHoraFin();
                LocalDateTime inicioSiguiente = siguiente.getFechaHora();
                
                // Si el hueco candidato es después de busquedaActual
                LocalDateTime inicioCandidato = finActual.isAfter(busquedaActual) ? finActual : busquedaActual;
                
                // Verificar si hay espacio suficiente
                if (inicioCandidato.plusMinutes(durMin).isBefore(inicioSiguiente)
                    || inicioCandidato.plusMinutes(durMin).isEqual(inicioSiguiente)) {
                    // Verificar que no se pase del horario laboral
                    if (inicioCandidato.plusMinutes(durMin).isBefore(finLaboral)
                        || inicioCandidato.plusMinutes(durMin).isEqual(finLaboral)) {
                        return Optional.of(inicioCandidato);
                    }
                }
            }
            
            // Verificar hueco después del último turno del médico
            Turno ultimoTurno = turnosMedicoDelDia.get(turnosMedicoDelDia.size() - 1);
            LocalDateTime despuesUltimo = ultimoTurno.getFechaHoraFin();
            
            if (despuesUltimo.isBefore(busquedaActual)) {
                despuesUltimo = busquedaActual;
            }
            
            if (despuesUltimo.plusMinutes(durMin).isBefore(finLaboral)
                || despuesUltimo.plusMinutes(durMin).isEqual(finLaboral)) {
                return Optional.of(despuesUltimo);
            }
            
            // No hay hueco hoy para este médico, intentar día siguiente
            busquedaActual = busquedaActual.plusDays(1).withHour(HORA_INICIO_LABORAL).withMinute(0);
        }
        
        // No se encontró hueco en una semana
        return Optional.empty();
    }
    
    /**
     * Obtiene todos los turnos de un médico específico en una fecha.
     * Complejidad: O(n) en el peor caso
     * 
     * @param matriculaMedico Matrícula del médico
     * @param fecha La fecha para filtrar turnos
     * @return Lista de turnos del médico en esa fecha, ordenados cronológicamente
     */
    private List<Turno> obtenerTurnosMedicoDelDia(String matriculaMedico, java.time.LocalDate fecha) {
        List<Turno> resultado = new ArrayList<>();
        
        for (Turno t : obtenerTurnosOrdenados()) {
            if (t.getMatriculaMedico().equals(matriculaMedico) 
                && t.getFechaHora().toLocalDate().equals(fecha)) {
                resultado.add(t);
            }
        }
        
        return resultado;
    }
    
    /**
     * Obtiene todos los turnos de un médico específico.
     * Útil para ver la agenda completa de un médico.
     * 
     * @param matriculaMedico Matrícula del médico
     * @return Lista de todos los turnos del médico, ordenados cronológicamente
     */
    public synchronized List<Turno> turnosPorMedico(String matriculaMedico) {
        List<Turno> resultado = new ArrayList<>();
        
        for (Turno t : obtenerTurnosOrdenados()) {
            if (t.getMatriculaMedico().equals(matriculaMedico)) {
                resultado.add(t);
            }
        }
        
        return resultado;
    }
}
