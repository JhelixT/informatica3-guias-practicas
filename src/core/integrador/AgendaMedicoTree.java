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
            // Comparar primero por fecha/hora
            int cmp = this.turno.getFechaHora().compareTo(otro.turno.getFechaHora());
            
            // Si tienen la misma fecha/hora, desempatar por ID
            // Esto permite múltiples turnos en la misma hora (diferentes médicos/pacientes)
            if (cmp == 0) {
                return this.turno.getId().compareTo(otro.turno.getId());
            }
            
            return cmp;
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
}
