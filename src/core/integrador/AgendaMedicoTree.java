package core.integrador;

import core.estructuras.arboles.ArbolAVL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementación de AgendaMedico usando árbol AVL ordenado por fechaHora.
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
    
    /** Agenda un nuevo turno verificando duplicados y solapamientos */
    @Override
    public synchronized boolean agendar(Turno t) {
        if (t == null || t.getId() == null) return false;
        
        if (buscarPorId(t.getId()) != null) return false;
        if (tieneSolapamiento(t)) return false;
        
        arbolTurnos.insert(new TurnoWrapper(t));
        return true;
    }
    
    /** Cancela un turno por su ID, eliminándolo del árbol */
    @Override
    public synchronized boolean cancelar(String idTurno) {
        Turno turno = buscarPorId(idTurno);
        if (turno == null) return false;
        
        arbolTurnos.delete(new TurnoWrapper(turno));
        return true;
    }
    
    /** Retorna el siguiente turno a partir de la fecha/hora especificada */
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
    
    /** Busca un turno por su ID recorriendo todos los turnos */
    private Turno buscarPorId(String id) {
        for (Turno turno : obtenerTurnosOrdenados()) {
            if (turno.getId().equals(id)) return turno;
        }
        return null;
    }
    
    /** Verifica si un turno nuevo se solapa con algún turno existente */
    private boolean tieneSolapamiento(Turno nuevo) {
        LocalDateTime inicio = nuevo.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(nuevo.getDuracionMin());
        
        for (Turno existente : obtenerTurnosOrdenados()) {
            LocalDateTime inicioExist = existente.getFechaHora();
            LocalDateTime finExist = inicioExist.plusMinutes(existente.getDuracionMin());
            
            if (inicio.isBefore(finExist) && inicioExist.isBefore(fin)) {
                return true;
            }
        }
        return false;
    }
    
    /** Retorna todos los turnos en orden cronológico (recorrido inorden del AVL) */
    private List<Turno> obtenerTurnosOrdenados() {
        List<Turno> resultado = new ArrayList<>();
        recorridoInorden(obtenerRaiz(), resultado);
        return resultado;
    }
    
    /** Usa reflexión para recorrer el árbol AVL en orden */
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
    public synchronized List<Turno> todosTurnos() {
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
            
            List<Turno> turnosMedicoDelDia = obtenerTurnosMedicoDelDia(matriculaMedico, busquedaActual.toLocalDate());
            
            if (turnosMedicoDelDia.isEmpty()) {
                return Optional.of(busquedaActual);
            }
            
            Turno primerTurno = turnosMedicoDelDia.get(0);
            if (busquedaActual.plusMinutes(durMin).isBefore(primerTurno.getFechaHora()) 
                || busquedaActual.plusMinutes(durMin).isEqual(primerTurno.getFechaHora())) {
                return Optional.of(busquedaActual);
            }
            
            for (int i = 0; i < turnosMedicoDelDia.size() - 1; i++) {
                Turno actual = turnosMedicoDelDia.get(i);
                Turno siguiente = turnosMedicoDelDia.get(i + 1);
                
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
            }
            
            Turno ultimoTurno = turnosMedicoDelDia.get(turnosMedicoDelDia.size() - 1);
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
    
    /** Retorna todos los turnos de un médico específico en orden cronológico */
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
