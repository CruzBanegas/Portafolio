package Modelo;
import java.time.LocalDate;
public class EstacionSismologica {

    private String codigoEstacion;
    private String nombre;
    private double latitud;
    private double longitud;
    private String documentoCertificacionAdq;
    private String nroCertificacionAdquisicion;
    private LocalDate fechaSolicitudCertificacion;
    private Sismografo sismografo;

    public EstacionSismologica(String codigoEstacion, String nombre, double latitud, double longitud) {
        this.codigoEstacion = codigoEstacion;
        this.nombre = nombre;
        this.latitud = latitud;
        this.longitud = longitud;
        this.documentoCertificacionAdq = documentoCertificacionAdq;
        this.nroCertificacionAdquisicion = nroCertificacionAdquisicion;
        this.fechaSolicitudCertificacion = fechaSolicitudCertificacion;
        this.sismografo = sismografo;
    }

    public String getCodigoEstacion() {
        return codigoEstacion;
    }

    public String getNombre() {
        return nombre;
    }

    public Sismografo obtenerSismografo() {
        return sismografo;
    }

    // Métodos adicionales útiles (no en el diagrama pero recomendables)

    public double getLatitud() {
        return latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public String getDocumentoCertificacionAdq() {
        return documentoCertificacionAdq;
    }

    public String getNroCertificacionAdquisicion() {
        return nroCertificacionAdquisicion;
    }

    public LocalDate getFechaSolicitudCertificacion() {
        return fechaSolicitudCertificacion;
    }

    public void setSismografo(Sismografo nuevoSismografo) {
        this.sismografo = nuevoSismografo;
    }
}
