package app;

import Controlador.GestorInspeccion;
import Interfaz.PantallaInspeccion;
import Modelo.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // --- Configuración de Datos de Prueba ---
        Empleado empleado1 = new Empleado("Carlos", "Perez", "3512065788", "jperez@gmail.com", "Administrador");
        empleado1.setMail("carlos.perez@example.com");
        empleado1.setTelefono("123456789");
        Usuario usuario1 = new Usuario("cperez", "1234", empleado1);
        Sesion sesionActiva = new Sesion(LocalDateTime.now(), usuario1);

        // El estado para las órdenes que se mostrarán en la tabla para cerrar
        Estado estadoCompletamenteRealizada = new Estado("Completamente Realizada");
        Estado estadoCerradaOI = new Estado("Cerrada");
        Estado estadoHabInspSismo = new Estado("Habilitado Para Inspección");

        EstacionSismologica estacionA = new EstacionSismologica("EST-A", "Norte", -31.4, -64.1);
        Sismografo sismografoA1 = new Sismografo("SISM-A01", estadoHabInspSismo, estacionA);
        estacionA.setSismografo(sismografoA1);

        EstacionSismologica estacionB = new EstacionSismologica("EST-B", "Sur", -32.5, -65.2);
        Sismografo sismografoB1 = new Sismografo("SISM-B01", estadoHabInspSismo, estacionB);
        estacionB.setSismografo(sismografoB1);

        List<OrdenDeInspeccion> todasLasOrdenes = new ArrayList<>();

        // Orden 1
        OrdenDeInspeccion orden1 = new OrdenDeInspeccion(
                "OI-001", // Numero de Orden
                LocalDateTime.now().minusDays(7), // Fecha Hora Inicio de la orden (cuando se creó/asignó)
                empleado1,
                estacionA
        );
        orden1.actualizarEstado(estadoCompletamenteRealizada); // Actualizar al estado correcto
        // ***** ASIGNAR FECHA DE FINALIZACIÓN PREVISTA/REALIZADA *****
        orden1.setFechaHoraFinalizacion(LocalDateTime.now().plusDays(5)); // Ejemplo: Se espera que finalice en 5 días desde hoy

        // Orden 2
        OrdenDeInspeccion orden2 = new OrdenDeInspeccion(
                "OI-002", // Numero de Orden
                LocalDateTime.now().minusDays(3), // Fecha Hora Inicio
                empleado1,
                estacionB
        );
        orden2.actualizarEstado(estadoCompletamenteRealizada); // Actualizar al estado correcto
        // ***** ASIGNAR FECHA DE FINALIZACIÓN PREVISTA/REALIZADA *****
        orden2.setFechaHoraFinalizacion(LocalDateTime.now().plusDays(2)); // Ejemplo: Se espera que finalice en 2 días desde hoy

        // Orden para otro empleado (no debería aparecer para empleado1 si el filtro del gestor funciona)
        Empleado empleado2 = new Empleado("Ana", "Gomez", "3513087744", "anagomez@gmail.com", "Gerente");
        OrdenDeInspeccion orden3 = new OrdenDeInspeccion(
                "OI-003",
                LocalDateTime.now().minusDays(5),
                empleado2,
                estacionA
        );
        orden3.actualizarEstado(estadoCompletamenteRealizada);
        orden3.setFechaHoraFinalizacion(LocalDateTime.now().plusDays(3)); // También necesita una fecha

        // Orden ya cerrada (esta SÍ debería tener fechaHoraFinalizacion y fechaHoraCierre)
        OrdenDeInspeccion orden4 = new OrdenDeInspeccion(
                "OI-004",
                LocalDateTime.now().minusDays(10),
                empleado1,
                estacionB
        );
        orden4.actualizarEstado(estadoCerradaOI);
        LocalDateTime fechaRealDeFinalizacionYCierre = LocalDateTime.now().minusDays(1).minusHours(5);
        orden4.setFechaHoraFinalizacion(fechaRealDeFinalizacionYCierre); // Fecha en que realmente finalizó
        orden4.setFechaHoraCierre(fechaRealDeFinalizacionYCierre);      // Fecha en que se cerró administrativamente
        orden4.setObservacionCierre("Cerrada anteriormente.");

        todasLasOrdenes.add(orden1);
        todasLasOrdenes.add(orden2);
        todasLasOrdenes.add(orden3);
        todasLasOrdenes.add(orden4);

        // --- Inicialización del Sistema ---
        Scanner scanner = new Scanner(System.in);
        GestorInspeccion gestor = new GestorInspeccion(sesionActiva, todasLasOrdenes);
        PantallaInspeccion pantalla = new PantallaInspeccion(gestor, scanner);
        pantalla.opcionCerrarOrdenInspeccion();
        // scanner.close(); // Considerar si es necesario
    }
}