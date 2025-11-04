package core.integrador.recordatorios;

import core.integrador.modelo.Recordatorio;
import core.integrador.pacientes.IndicePacientes;
import core.integrador.modelo.Paciente;
import java.time.LocalDateTime;

/**
 * Gestor de operaciones sobre recordatorios
 */
public class GestorRecordatorios {
    
    private PlanificadorRecordatorios planificador;
    private IndicePacientes indicePacientes;
    private int contadorIds;
    
    public GestorRecordatorios(PlanificadorRecordatorios planificador, IndicePacientes indicePacientes) {
        this.planificador = planificador;
        this.indicePacientes = indicePacientes;
        this.contadorIds = 1;
    }
    
    /**
     * Genera un nuevo ID único para recordatorios
     */
    private String generarNuevoId() {
        return "REC" + String.format("%05d", contadorIds++);
    }
    
    /**
     * Programa un nuevo recordatorio
     */
    public ResultadoRecordatorio programar(String dni, LocalDateTime fecha, String mensaje) {
        Paciente p = indicePacientes.get(dni);
        if (p == null) {
            return new ResultadoRecordatorio(false, "Paciente no encontrado", null, null);
        }
        
        if (fecha == null) {
            return new ResultadoRecordatorio(false, "Fecha no puede ser null", null, null);
        }
        
        try {
            String id = generarNuevoId();
            Recordatorio r = new Recordatorio(id, fecha, dni, mensaje);
            planificador.programar(r);
            return new ResultadoRecordatorio(true, "Recordatorio programado", r, p);
        } catch (Exception e) {
            return new ResultadoRecordatorio(false, "Error al programar: " + e.getMessage(), null, null);
        }
    }
    
    /**
     * Extrae el recordatorio más próximo
     */
    public ResultadoExtraccion extraer() {
        if (planificador.isEmpty()) {
            return new ResultadoExtraccion(false, "No hay recordatorios pendientes", null, null);
        }
        
        try {
            Recordatorio r = planificador.proximo();
            Paciente p = indicePacientes.get(r.getDniPaciente());
            return new ResultadoExtraccion(true, "Recordatorio extraído", r, p);
        } catch (Exception e) {
            return new ResultadoExtraccion(false, "Error al extraer: " + e.getMessage(), null, null);
        }
    }
    
    /**
     * Reprograma un recordatorio existente
     */
    public ResultadoReprogramacion reprogramar(String id, LocalDateTime nuevaFecha) {
        if (planificador.isEmpty()) {
            return new ResultadoReprogramacion(false, "No hay recordatorios pendientes", null, null, null);
        }
        
        if (nuevaFecha == null) {
            return new ResultadoReprogramacion(false, "Nueva fecha no puede ser null", null, null, null);
        }
        
        Recordatorio r = planificador.getRecordatorio(id);
        if (r == null) {
            return new ResultadoReprogramacion(false, "Recordatorio no encontrado con ID: " + id, null, null, null);
        }
        
        Paciente p = indicePacientes.get(r.getDniPaciente());
        LocalDateTime fechaAnterior = r.getFecha();
        
        try {
            planificador.reprogramar(id, nuevaFecha);
            return new ResultadoReprogramacion(true, "Recordatorio reprogramado", r, p, fechaAnterior);
        } catch (IllegalArgumentException e) {
            return new ResultadoReprogramacion(false, e.getMessage(), null, null, null);
        } catch (Exception e) {
            return new ResultadoReprogramacion(false, "Error al reprogramar: " + e.getMessage(), null, null, null);
        }
    }
    
    /**
     * Obtiene el próximo recordatorio sin extraerlo
     */
    public InfoProximo obtenerProximo() {
        if (planificador.isEmpty()) {
            return new InfoProximo(null, null, false);
        }
        
        Recordatorio r = planificador.peek();
        Paciente p = indicePacientes.get(r.getDniPaciente());
        return new InfoProximo(r, p, true);
    }
    
    /**
     * Obtiene estadísticas de los recordatorios
     */
    public EstadisticasRecordatorios obtenerEstadisticas() {
        return new EstadisticasRecordatorios(
            planificador.size(),
            planificador.isEmpty()
        );
    }
    
    // ===== CLASES INTERNAS =====
    
    public static class ResultadoRecordatorio {
        private boolean exito;
        private String mensaje;
        private Recordatorio recordatorio;
        private Paciente paciente;
        
        public ResultadoRecordatorio(boolean exito, String mensaje, Recordatorio recordatorio, Paciente paciente) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.recordatorio = recordatorio;
            this.paciente = paciente;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Recordatorio getRecordatorio() { return recordatorio; }
        public Paciente getPaciente() { return paciente; }
    }
    
    public static class ResultadoExtraccion {
        private boolean exito;
        private String mensaje;
        private Recordatorio recordatorio;
        private Paciente paciente;
        
        public ResultadoExtraccion(boolean exito, String mensaje, Recordatorio recordatorio, Paciente paciente) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.recordatorio = recordatorio;
            this.paciente = paciente;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Recordatorio getRecordatorio() { return recordatorio; }
        public Paciente getPaciente() { return paciente; }
    }
    
    public static class ResultadoReprogramacion {
        private boolean exito;
        private String mensaje;
        private Recordatorio recordatorioNuevo;
        private Paciente paciente;
        private LocalDateTime fechaAnterior;
        
        public ResultadoReprogramacion(boolean exito, String mensaje, Recordatorio recordatorioNuevo, 
                                       Paciente paciente, LocalDateTime fechaAnterior) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.recordatorioNuevo = recordatorioNuevo;
            this.paciente = paciente;
            this.fechaAnterior = fechaAnterior;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Recordatorio getRecordatorioNuevo() { return recordatorioNuevo; }
        public Paciente getPaciente() { return paciente; }
        public LocalDateTime getFechaAnterior() { return fechaAnterior; }
    }
    
    public static class InfoProximo {
        private Recordatorio recordatorio;
        private Paciente paciente;
        private boolean hayProximo;
        
        public InfoProximo(Recordatorio recordatorio, Paciente paciente, boolean hayProximo) {
            this.recordatorio = recordatorio;
            this.paciente = paciente;
            this.hayProximo = hayProximo;
        }
        
        public Recordatorio getRecordatorio() { return recordatorio; }
        public Paciente getPaciente() { return paciente; }
        public boolean hayProximo() { return hayProximo; }
    }
    
    public static class EstadisticasRecordatorios {
        private int totalRecordatorios;
        private boolean vacia;
        
        public EstadisticasRecordatorios(int totalRecordatorios, boolean vacia) {
            this.totalRecordatorios = totalRecordatorios;
            this.vacia = vacia;
        }
        
        public int getTotalRecordatorios() { return totalRecordatorios; }
        public boolean isVacia() { return vacia; }
    }
}
