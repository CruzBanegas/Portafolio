package Modelo;

public class Empleado {
        private String nombre;
        private String apellido;
        private String RI;
        private String mail;
        private String telefono;

        public Empleado(String nombre, String apellido, String telefono, String mail, String rolInterno) {
            this.nombre = nombre;
            this.apellido = " ";
            this.RI = rolInterno;
            this.mail = null;
            this.telefono = null;
        }

        public String getNombre() {
            return nombre;
        }
        public String getApellido() { return apellido;}
        public String getRILogueado() {
            return RI;
        }
        public String getMail() {
            return mail;
        }
        public String getTelefono() {
            return telefono;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public void setRI(String RI) {
            this.RI = RI;
        }

        public void setMail(String mail) {
            this.mail = mail;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empleado empleado = (Empleado) o;
        // Comparar por un identificador único es lo mejor, como el RI si es único.
        if (RI != null ? !RI.equals(empleado.RI) : empleado.RI != null) return false;
        // Podrías añadir más campos a la comparación si RI no es suficiente o puede ser null
        // y quieres una igualdad más estricta (nombre, apellido).
        // Por ahora, si RI es el identificador principal, esto debería bastar.
        return true; // Si llega aquí, los RI son iguales (o ambos null, lo cual no debería pasar si RI es clave)
    }

    @Override
        public String toString() {
            return nombre + " " + apellido + " (" + RI + ")";
        }
    }
