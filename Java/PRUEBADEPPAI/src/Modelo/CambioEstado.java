package Modelo;
import java.time.LocalDateTime;
public class CambioEstado {

    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Estado estado;
    private MotivoFueraServicio motivoFueraServicio;

    public CambioEstado(LocalDateTime fechaHoraInicio, Estado estado, MotivoFueraServicio motivoFueraServicio) {
        this.fechaHoraInicio = fechaHoraInicio;
        this.estado = estado;
        this.motivoFueraServicio = motivoFueraServicio;
        this.fechaHoraFin = null; // al momento de crearse aún no tiene fin
    }

    // Indica si este es el estado actual (si no tiene fecha de fin)
    public boolean esEstadoActual() {
        return this.fechaHoraFin == null;
    }

    // Verifica si está habilitado para inspección
    public boolean esHabilitadoParaInspeccion() {
        return estado.getNombre().equalsIgnoreCase("Habilitado Para Inspección");
    }

    // Verifica si está inhabilitado por inspección
    public boolean esInhabilitadoPorInspeccion() {
        return estado.getNombre().equalsIgnoreCase("Inhabilitado Por Inspección");
    }

    // Asigna la fecha de finalización del estado
    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    // Devuelve el estado asociado
    public Estado conocerEstado() {
        return estado;
    }

    // Devuelve el motivo fuera de servicio (puede ser null si no aplica)
    public MotivoFueraServicio conocerMotivoFueraServicio() {
        return motivoFueraServicio;
    }
}

