package Modelo; // O un paquete como Servicios

import java.time.LocalDateTime;
// import java.util.List; // Si necesitaras una lista de motivos para el monitor

public class InterfazMonitor {

    public InterfazMonitor() {
        // Constructor vacío
    }

    public void notificarCierre(String numeroOrden, String identificadorSismografo,
                                Estado estadoSismografo, LocalDateTime fechaHoraActualizacion,
                                MotivoFueraServicio motivo) {

        System.out.println("\n--- Notificación al Monitor de Operaciones ---");
        System.out.println("Evento: Actualización por Cierre de Orden de Inspección");
        System.out.println("--------------------------------------------------");
        System.out.println("Orden de Inspección N°: " + numeroOrden + " cerrada.");
        System.out.println("Fecha y Hora de Actualización: " + fechaHoraActualizacion);

        if (identificadorSismografo != null && estadoSismografo != null) {
            System.out.println("\nDetalles del Sismógrafo:");
            System.out.println("  ID Sismógrafo: " + identificadorSismografo);
            System.out.println("  Estado Reportado: " + estadoSismografo.getNombre());

            if (motivo != null && estadoSismografo.esFueraDeServicio()) {
                System.out.println("  Causa 'Fuera de Servicio': " + motivo.conocerMotivoTipo().obtenerDescripcion());
                System.out.println("  Detalle Adicional: " + motivo.getComentario());
            }
        } else {
            System.out.println("\nEl sismógrafo asociado no cambió a 'Fuera de Servicio' o no se especificó motivo.");
        }
        System.out.println("--------------------------------------------------");

        // Lógica para enviar datos a un dashboard, API de monitoreo, etc.
    }
}
