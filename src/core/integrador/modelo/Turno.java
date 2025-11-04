package core.integrador.modelo;

import java.time.LocalDateTime;

public class Turno implements Comparable<Turno> {
    private String id;
    private String dniPaciente;
    private String matriculaMedico;
    private LocalDateTime fechaHora;
    private int duracionMin;
    private String motivo;

    public Turno(String id, String dniPaciente, String matriculaMedico, 
                 LocalDateTime fechaHora, int duracionMin, String motivo) {
        this.id = id;
        this.dniPaciente = dniPaciente;
        this.matriculaMedico = matriculaMedico;
        this.fechaHora = fechaHora;
        this.duracionMin = duracionMin;
        this.motivo = motivo;
    }

    public String getId() { return id; }
    public String getDniPaciente() { return dniPaciente; }
    public String getMatriculaMedico() { return matriculaMedico; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public int getDuracionMin() { return duracionMin; }
    public String getMotivo() { return motivo; }

    public LocalDateTime getFechaHoraFin() {
        return fechaHora.plusMinutes(duracionMin);
    }

    public boolean seSuperpone(Turno otro) {
        return this.fechaHora.isBefore(otro.getFechaHoraFin()) 
            && otro.fechaHora.isBefore(this.getFechaHoraFin());
    }

    @Override
    public int compareTo(Turno otro) {
        return this.fechaHora.compareTo(otro.fechaHora);
    }
}
