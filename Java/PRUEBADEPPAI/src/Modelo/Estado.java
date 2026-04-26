package Modelo;

public class Estado {
    private final String nombre;
    public Estado(String nombre) {
        this.nombre = nombre;
    }
    public String getNombre() {
        return nombre;
    }
    public boolean esFueraDeServicio() {
        return nombre.equalsIgnoreCase("Fuera de Servicio");
    }
    public boolean esCerrada() {
        return nombre.equalsIgnoreCase("Cerrada");
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Estado)) return false;
        Estado otro = (Estado) obj;
        return nombre.equalsIgnoreCase(otro.nombre);
    }
    public int hashCode() {
        return nombre.toLowerCase().hashCode();
    }
    public String toString() {
        return nombre;
    }
}