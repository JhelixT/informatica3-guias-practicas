package core.integrador.agenda;

import core.integrador.modelo.Turno;
import core.estructuras.arboles.ArbolAVL;
import core.estructuras.listas.ListaEnlazada;
import core.estructuras.nodos.Nodo;
import java.time.LocalDateTime;
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
    
    /** Busca un turno por su ID recorriendo todos los turnos */
    private Turno buscarPorId(String id) {
        ListaEnlazada<Turno> turnos = obtenerTurnosOrdenados();
        Nodo<Turno> nodo = turnos.getHead();
        
        while (nodo != null) {
            if (nodo.getData().getId().equals(id)) {
                return nodo.getData();
            }
            nodo = nodo.getNext();
        }
        return null;
    }
    
    /** Verifica si un turno nuevo se solapa con algún turno existente */
    private boolean tieneSolapamiento(Turno nuevo) {
        LocalDateTime inicio = nuevo.getFechaHora();
        LocalDateTime fin = inicio.plusMinutes(nuevo.getDuracionMin());
        
        ListaEnlazada<Turno> turnos = obtenerTurnosOrdenados();
        Nodo<Turno> nodo = turnos.getHead();
        
        while (nodo != null) {
            Turno existente = nodo.getData();
            LocalDateTime inicioExist = existente.getFechaHora();
            LocalDateTime finExist = inicioExist.plusMinutes(existente.getDuracionMin());
            
            if (inicio.isBefore(finExist) && inicioExist.isBefore(fin)) {
                return true;
            }
            nodo = nodo.getNext();
        }
        return false;
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
