package core.integrador.quirofano;

import core.integrador.modelo.SolicitudCirugia;
import java.time.LocalDateTime;

/**
 * Gestor de operaciones sobre quirófanos
 */
public class GestorQuirofanos {
    
    private PlanificadorQuirofano planificador;
    
    public GestorQuirofanos(PlanificadorQuirofano planificador) {
        this.planificador = planificador;
    }
    
    /**
     * Procesa una solicitud de cirugía
     */
    public ResultadoCirugia procesar(SolicitudCirugia solicitud) {
        if (solicitud == null) {
            return new ResultadoCirugia(false, "Solicitud no puede ser null", null);
        }
        
        try {
            planificador.procesar(solicitud);
            return new ResultadoCirugia(true, "Cirugía programada y asignada", solicitud);
        } catch (Exception e) {
            return new ResultadoCirugia(false, "Error al procesar: " + e.getMessage(), null);
        }
    }
    
    /**
     * Avanza el tiempo del sistema
     */
    public ResultadoAvance avanzarTiempo(LocalDateTime nuevoTiempo) {
        if (nuevoTiempo == null) {
            return new ResultadoAvance(false, "Fecha no puede ser null");
        }
        
        try {
            if (planificador instanceof PlanificadorQuirofanoImpl) {
                ((PlanificadorQuirofanoImpl) planificador).avanzarTiempo(nuevoTiempo);
            }
            return new ResultadoAvance(true, "Tiempo actualizado");
        } catch (Exception e) {
            return new ResultadoAvance(false, "Error al avanzar tiempo: " + e.getMessage());
        }
    }
    
    /**
     * Registra un médico en el sistema
     */
    public ResultadoRegistro registrarMedico(String matricula, String nombre) {
        if (matricula == null || matricula.trim().isEmpty()) {
            return new ResultadoRegistro(false, "Matrícula no puede ser vacía");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            return new ResultadoRegistro(false, "Nombre no puede ser vacío");
        }
        
        try {
            planificador.registrarMedico(matricula, nombre);
            return new ResultadoRegistro(true, "Médico registrado");
        } catch (Exception e) {
            return new ResultadoRegistro(false, "Error al registrar: " + e.getMessage());
        }
    }
    
    // ===== CLASES INTERNAS =====
    
    public static class ResultadoCirugia {
        private boolean exito;
        private String mensaje;
        private SolicitudCirugia solicitud;
        
        public ResultadoCirugia(boolean exito, String mensaje, SolicitudCirugia solicitud) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.solicitud = solicitud;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public SolicitudCirugia getSolicitud() { return solicitud; }
    }
    
    public static class ResultadoAvance {
        private boolean exito;
        private String mensaje;
        
        public ResultadoAvance(boolean exito, String mensaje) {
            this.exito = exito;
            this.mensaje = mensaje;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
    }
    
    public static class ResultadoRegistro {
        private boolean exito;
        private String mensaje;
        
        public ResultadoRegistro(boolean exito, String mensaje) {
            this.exito = exito;
            this.mensaje = mensaje;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
    }
}
