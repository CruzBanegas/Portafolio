package Modelo;
import java.util.ArrayList;
import java.util.List;
public class Sismografo {

    private String identificador;
    private Estado estado;
    private EstacionSismologica estacion;
    private List<CambioEstado> cambiosEstado;

    public Sismografo(String identificador, Estado estado, EstacionSismologica estacion) {
        this.identificador = identificador;
        this.estado = estado;
        this.estacion = estacion;
        this.cambiosEstado = new ArrayList<>();
    }

    public String getIdentificadorSismografo() {
        return identificador;
    }

    public Estado obtenerEstadoActual() {
        return estado;
    }

    public void actualizarSituacion(Estado nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public void crearCambioEstado(CambioEstado cambio) {
        cambiosEstado.add(cambio);
    }

    public List<CambioEstado> conocerCambioEstado() {
        return cambiosEstado;
    }

    public EstacionSismologica conocerEstacionSismologica() {
        return estacion;
    }

    public void setEstado(Estado nuevoEstado) {

    }
}

