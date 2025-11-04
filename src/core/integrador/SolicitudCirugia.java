package core.integrador;

import java.time.LocalDateTime;

/** Representa una solicitud de cirugía con médico, duración y deadline */
public class SolicitudCirugia {
    private String id;
    private String matricula;
    private int durMin;
    private LocalDateTime deadline;

    public SolicitudCirugia(String id, String matricula, int durMin, LocalDateTime deadline) {
        this.id = id;
        this.matricula = matricula;
        this.durMin = durMin;
        this.deadline = deadline;
    }

    public String getId() { return id; }
    public String getMatricula() { return matricula; }
    public int getDurMin() { return durMin; }
    public LocalDateTime getDeadline() { return deadline; }

    @Override
    public String toString() {
        return String.format("Cirugía[id=%s, médico=%s, %dmin, deadline=%s]",
                id, matricula, durMin, deadline);
    }
}
