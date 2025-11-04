package core.integrador.salaespera;

import core.integrador.modelo.Paciente;
import core.integrador.pacientes.IndicePacientes;

/**
 * Gestor de operaciones sobre la sala de espera
 */
public class GestorSalaEspera {
    
    private SalaEspera sala;
    private IndicePacientes indicePacientes;
    
    public GestorSalaEspera(SalaEspera sala, IndicePacientes indicePacientes) {
        this.sala = sala;
        this.indicePacientes = indicePacientes;
    }
    
    /**
     * Agrega un paciente a la sala validando que existe
     */
    public ResultadoSala agregar(String dni) {
        if (sala.isFull()) {
            return new ResultadoSala(false, "La sala de espera está llena", null);
        }
        
        Paciente p = indicePacientes.get(dni);
        if (p == null) {
            return new ResultadoSala(false, "Paciente no encontrado", null);
        }
        
        try {
            sala.llega(dni);
            return new ResultadoSala(true, "Paciente agregado a la sala", p);
        } catch (Exception e) {
            return new ResultadoSala(false, e.getMessage(), null);
        }
    }
    
    /**
     * Atiende al siguiente paciente
     */
    public ResultadoAtencion atender() {
        if (sala.isEmpty()) {
            return new ResultadoAtencion(false, "No hay pacientes en espera", null, null);
        }
        
        String dniAtendido = sala.atiende();
        
        if (dniAtendido != null) {
            Paciente p = indicePacientes.get(dniAtendido);
            return new ResultadoAtencion(true, "Paciente atendido", dniAtendido, p);
        } else {
            return new ResultadoAtencion(false, "Error al atender paciente", null, null);
        }
    }
    
    /**
     * Obtiene información del próximo en atención
     */
    public InfoProximo obtenerProximo() {
        if (sala.isEmpty()) {
            return new InfoProximo(null, null, false);
        }
        
        String dni = sala.peek();
        Paciente p = indicePacientes.get(dni);
        return new InfoProximo(dni, p, true);
    }
    
    /**
     * Limpia completamente la sala de espera
     */
    public ResultadoLimpieza limpiar() {
        if (sala.isEmpty()) {
            return new ResultadoLimpieza(false, "La sala ya está vacía", 0);
        }
        
        int atendidos = 0;
        
        while (!sala.isEmpty()) {
            sala.atiende();
            atendidos++;
        }
        
        return new ResultadoLimpieza(true, "Sala limpiada", atendidos);
    }
    
    /**
     * Obtiene el estado actual de la sala
     */
    public EstadoSala obtenerEstado() {
        return new EstadoSala(
            sala.getCapacidad(),
            sala.size(),
            sala.isEmpty(),
            sala.isFull()
        );
    }
    
    // ===== CLASES INTERNAS =====
    
    public static class ResultadoSala {
        private boolean exito;
        private String mensaje;
        private Paciente paciente;
        
        public ResultadoSala(boolean exito, String mensaje, Paciente paciente) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.paciente = paciente;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public Paciente getPaciente() { return paciente; }
    }
    
    public static class ResultadoAtencion {
        private boolean exito;
        private String mensaje;
        private String dni;
        private Paciente paciente;
        
        public ResultadoAtencion(boolean exito, String mensaje, String dni, Paciente paciente) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.dni = dni;
            this.paciente = paciente;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public String getDni() { return dni; }
        public Paciente getPaciente() { return paciente; }
    }
    
    public static class InfoProximo {
        private String dni;
        private Paciente paciente;
        private boolean hayProximo;
        
        public InfoProximo(String dni, Paciente paciente, boolean hayProximo) {
            this.dni = dni;
            this.paciente = paciente;
            this.hayProximo = hayProximo;
        }
        
        public String getDni() { return dni; }
        public Paciente getPaciente() { return paciente; }
        public boolean hayProximo() { return hayProximo; }
    }
    
    public static class ResultadoLimpieza {
        private boolean exito;
        private String mensaje;
        private int pacientesRemovidos;
        
        public ResultadoLimpieza(boolean exito, String mensaje, int pacientesRemovidos) {
            this.exito = exito;
            this.mensaje = mensaje;
            this.pacientesRemovidos = pacientesRemovidos;
        }
        
        public boolean isExito() { return exito; }
        public String getMensaje() { return mensaje; }
        public int getPacientesRemovidos() { return pacientesRemovidos; }
    }
    
    public static class EstadoSala {
        private int capacidad;
        private int enEspera;
        private boolean vacia;
        private boolean llena;
        
        public EstadoSala(int capacidad, int enEspera, boolean vacia, boolean llena) {
            this.capacidad = capacidad;
            this.enEspera = enEspera;
            this.vacia = vacia;
            this.llena = llena;
        }
        
        public int getCapacidad() { return capacidad; }
        public int getEnEspera() { return enEspera; }
        public boolean isVacia() { return vacia; }
        public boolean isLlena() { return llena; }
    }
}
