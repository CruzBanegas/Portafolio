package Modelo;

public class Usuario {
    private final String nombreUsuario;
    private String contrasena;

    // Atributo necesario aunque no figure en el diagrama, para que los métodos funcionen
    private Empleado empleado;

    public Usuario(String nombreUsuario, String contraseña, Empleado empleado) {
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.empleado = empleado;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContraseña() {
        return contrasena;
    }

    /**
     * Devuelve el empleado asociado a este usuario.
     */
    public Empleado conocerEmpleado() {
        return this.empleado;
    }

    /**
     * Devuelve el rol interno del empleado asociado a este usuario.
     */
    public String getRIlogueado() {
        return this.empleado.getRILogueado();
    }

    @Override
    public String toString() {
        return nombreUsuario;
    }
}

