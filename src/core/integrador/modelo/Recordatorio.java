package core.integrador.modelo;

import java.time.LocalDateTime;

/**
 * Recordatorio programado para paciente.
 * Comparable por fecha (para heap).
 */
public class Recordatorio implements Comparable<Recordatorio> {
    
    private String id;
    private LocalDateTime fecha;
    private String dniPaciente;
    private String mensaje;
    
    public Recordatorio(String id, LocalDateTime fecha, String dniPaciente, String mensaje) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID no puede ser null o vacío");
        }
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha no puede ser null");
        }
        if (dniPaciente == null || dniPaciente.trim().isEmpty()) {
            throw new IllegalArgumentException("DNI del paciente no puede ser null o vacío");
        }
        
        this.id = id;
        this.fecha = fecha;
        this.dniPaciente = dniPaciente;
        this.mensaje = mensaje != null ? mensaje : "";
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    public String getDniPaciente() {
        return dniPaciente;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    // Setter para fecha (usado en reprogramar)
    public void setFecha(LocalDateTime fecha) {
        if (fecha == null) {
            throw new IllegalArgumentException("Fecha no puede ser null");
        }
        this.fecha = fecha;
    }
    
    @Override
    public int compareTo(Recordatorio otro) {
        return this.fecha.compareTo(otro.fecha);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Recordatorio that = (Recordatorio) obj;
        return id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    @Override
    public String toString() {
        return "Recordatorio{" +
                "id='" + id + '\'' +
                ", fecha=" + fecha +
                ", dniPaciente='" + dniPaciente + '\'' +
                ", mensaje='" + mensaje + '\'' +
                '}';
    }
}
