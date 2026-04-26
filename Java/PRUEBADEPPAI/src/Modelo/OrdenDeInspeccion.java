package Modelo;
import java.time.LocalDateTime;
import java.util.List;

public class OrdenDeInspeccion {

    private String numeroOrden;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFinalizacion;
    private LocalDateTime fechaHoraCierre;
    private String observacionCierre;
    private List<MotivoConComentario> motivos;
    private Empleado empleado;
    private Estado estado;
    private EstacionSismologica estacionSismologica;

    public OrdenDeInspeccion(String numeroOrden, LocalDateTime fechaHoraInicio,
                             Empleado empleado, EstacionSismologica estacionSismologica) {
        this.numeroOrden = numeroOrden;
        this.fechaHoraInicio = fechaHoraInicio;
        this.empleado = empleado;
        this.estacionSismologica = estacionSismologica;
        this.estado = new Estado("Activa"); // Estado inicial por defecto
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public LocalDateTime getFechaHoraFinalizacion() {
        return fechaHoraFinalizacion;
    }

    public LocalDateTime getFechaHoraCierre() {
        return fechaHoraCierre;
    }

    public void setFechaHoraFinalizacion(LocalDateTime fecha) {
        this.fechaHoraFinalizacion = fecha;
    }

    public void setFechaHoraCierre(LocalDateTime fecha) {
        this.fechaHoraCierre = fecha;
    }

    public void setObservacionCierre(String observacion) {
        this.observacionCierre = observacion;
    }

    public boolean esTuEmpleado(Empleado emp) {
        return this.empleado.equals(emp);
    }

    public Empleado conocerEmpleado() {
        return empleado;
    }

    public EstacionSismologica conocerEstacionSismologica() {
        return estacionSismologica;
    }

    public Estado conocerEstado() {
        return estado;
    }

    public void actualizarEstado(Estado nuevoEstado) {
        this.estado = nuevoEstado;
    }


    public String getNombre() {
        return estacionSismologica.getNombre();
    }

    public String getObservacionCierre() { // <--- AÑADIR ESTE GETTER
        return observacionCierre;
    }

    public void actualizarSismografo(Sismografo nuevoSismografo) {
        estacionSismologica.setSismografo(nuevoSismografo);
    }

    @Override
    public String toString() {
        return "OrdenDeInspeccion{" +
                "numeroOrden='" + numeroOrden + '\'' +
                ", fechaHoraInicio=" + fechaHoraInicio +
                ", fechaHoraFinalizacion=" + fechaHoraFinalizacion +
                ", fechaHoraCierre=" + fechaHoraCierre +
                ", observacionCierre='" + observacionCierre + '\'' +
                ", empleado=" + empleado +
                ", estado=" + estado +
                ", estacionSismologica=" + estacionSismologica +
                '}';

    }
}

