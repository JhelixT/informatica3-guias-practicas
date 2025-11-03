package core.integrador.clases;

import java.time.LocalDateTime;

public class Turno {
    private String id;
    private String dniPaciente;
    private String matriculaMedico;
    private LocalDateTime fechaHora;
    private int duracionMin;
    private String motivo;

    // Constructor
    public Turno() {
    }

    public Turno(String id, String dniPaciente, String matriculaMedico, 
                 LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public String getMatriculaMedico() {
        return matriculaMedico;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public int getDuracionMin() {
        return duracionMin;
    }

    public String getMotivo() {
        return motivo;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public void setMatriculaMedico(String matriculaMedico) {
        this.matriculaMedico = matriculaMedico;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setDuracionMin(int duracionMin) {
        this.duracionMin = duracionMin;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
