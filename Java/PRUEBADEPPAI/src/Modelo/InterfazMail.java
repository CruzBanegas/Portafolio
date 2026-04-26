package Modelo; // O un paquete como Servicios

import java.time.LocalDateTime;
import java.util.List; // Si vas a pasar una lista de motivos

public class InterfazMail {

    // No se necesitan atributos de instancia si el método es autocontenido
    // y recibe toda la información que necesita.

    public InterfazMail() {
        // Constructor vacío, o podría inicializar alguna configuración de email si fuera real.
    }

    public void notificarCierre(String numeroOrden, String identificadorSismografo,
                                Estado estadoSismografo, LocalDateTime fechaHoraCierre,
                                String observacionOrden, MotivoFueraServicio motivo) {

        System.out.println("\n--- Notificación por Email ---");
        System.out.println("Asunto: Cierre de Orden de Inspección y Actualización de Sismógrafo");
        System.out.println("--------------------------------------------------");
        System.out.println("Estimado equipo,");
        System.out.println("Se ha procesado el cierre de la Orden de Inspección N°: " + numeroOrden);
        System.out.println("Fecha y Hora del Cierre: " + fechaHoraCierre);
        System.out.println("Observaciones del Cierre: " + observacionOrden);

        if (identificadorSismografo != null && estadoSismografo != null) {
            System.out.println("\nInformación del Sismógrafo afectado:");
            System.out.println("  Identificador: " + identificadorSismografo);
            System.out.println("  Nuevo Estado: " + estadoSismografo.getNombre());

            if (motivo != null && estadoSismografo.esFueraDeServicio()) {
                System.out.println("  Motivo de 'Fuera de Servicio': " + motivo.conocerMotivoTipo().obtenerDescripcion());
                System.out.println("  Comentario del Motivo: " + motivo.getComentario());
            }
        } else {
            System.out.println("\nNo se reportaron cambios específicos en el estado de un sismógrafo para esta orden, o no quedó fuera de servicio.");
        }

        System.out.println("\nSaludos cordiales,");
        System.out.println("Sistema de Gestión de Inspecciones");
        System.out.println("--------------------------------------------------");

        // En un sistema real, aquí iría la lógica para construir y enviar el email
        // usando JavaMail API u otra librería.
    }
}