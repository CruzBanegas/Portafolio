package Modelo;

import java.time.LocalDateTime;

public class Sesion {
    private LocalDateTime fechaIniLogueo;
    private LocalDateTime fechaFinLogueo;
    private Usuario usuario;

    public Sesion(LocalDateTime fechaIniLogueo, Usuario usuario) {
        this.fechaIniLogueo = fechaIniLogueo;
        this.usuario = usuario;
    }

    public LocalDateTime getFechaIniLogueo() {
        return fechaIniLogueo;
    }

    public LocalDateTime getFechaFinLogueo() {
        return fechaFinLogueo;
    }

    public void cerrarSesion() {
        this.fechaFinLogueo = LocalDateTime.now();
    }

    /**
     * * Método Devuelve el usuario asociado.
     * */
    public Usuario obtenerUsuario() {
        return usuario;
    }


    public Usuario conocerUsuario() {
        return usuario;
    }
}
