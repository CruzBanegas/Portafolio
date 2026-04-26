package Controlador;

import Modelo.*; // Importa todas las clases del paquete Modelo
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestorInspeccion {
    private Sesion sesionActiva;
    private List<OrdenDeInspeccion> ordenesDelEmpleado; // Renombrado para claridad, eran 'ordenesEmpleado'
    private List<OrdenDeInspeccion> ordenesInspeccionSeleccionadas;
    private String observacion;

    // Atributos para manejar el estado "Fuera de Servicio"
    private MotivoTipo motivoTipoSeleccionado;
    private String comentarioMotivoSeleccionado;
    private Estado estadoFueraDeServicioPredefinido; // Estado "Fuera de Servicio"
    private List<MotivoConComentario> motivosSeleccionados = new ArrayList<>();

    // Atributos que se actualizan o usan durante el proceso
    private LocalDateTime fechaHoraActualOperacion;
    private Empleado empleadoLogueado;

    public void tomarMotivos(List<MotivoConComentario> motivos) {
        this.motivosSeleccionados = motivos; // Asigna a una variable de clase
    }
    public List<MotivoConComentario> getMotivosSeleccionados() {
        return motivosSeleccionados;
    }

    public GestorInspeccion(Sesion sesionActiva, List<OrdenDeInspeccion> todasLasOrdenes) {
        this.sesionActiva = sesionActiva;
        if (this.sesionActiva != null && this.sesionActiva.obtenerUsuario() != null) {
            this.empleadoLogueado = this.sesionActiva.obtenerUsuario().conocerEmpleado();
        } else {
            // Manejar el caso de sesión o usuario nulo, quizás lanzar una excepción o loguear un error
            System.err.println("Error: Sesión o usuario no válidos al inicializar GestorInspeccion.");
            this.empleadoLogueado = null; // O un empleado por defecto si tiene sentido
        }

        this.fechaHoraActualOperacion = LocalDateTime.now(); // Inicial, se actualiza en nuevoCierreOrden
        this.ordenesDelEmpleado = new ArrayList<>();
        this.ordenesInspeccionSeleccionadas = new ArrayList<>();

        // Inicializar campos para selección de motivo
        this.motivoTipoSeleccionado = null;
        this.comentarioMotivoSeleccionado = null;

        // Predefinir el estado "Fuera de Servicio" que se usará.
        // En un sistema real, este podría venir de una base de datos o configuración.
        this.estadoFueraDeServicioPredefinido = new Estado("Fuera de Servicio");

        if (this.empleadoLogueado != null && todasLasOrdenes != null) {
            for (OrdenDeInspeccion o : todasLasOrdenes) {
                // Asegurarse que la orden tiene un empleado asignado antes de llamar a esTuEmpleado
                if (o.conocerEmpleado() != null && o.esTuEmpleado(empleadoLogueado)) {
                    // Adicionalmente, podríamos filtrar solo las que no están "Cerrada"
                    if (o.conocerEstado() != null && !o.conocerEstado().esCerrada()){
                        ordenesDelEmpleado.add(o);
                    }
                }
            }
        }
    }

    public void nuevoCierreOrden() {
        this.fechaHoraActualOperacion = LocalDateTime.now();
        // Limpiar selecciones previas por si se reutiliza el gestor para múltiples operaciones
        this.ordenesInspeccionSeleccionadas.clear();
        this.observacion = null;
        this.motivoTipoSeleccionado = null;
        this.comentarioMotivoSeleccionado = null;
    }

    public Empleado obtenerEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public List<OrdenDeInspeccion> obtenerOrdenesPorEmpleado() {
        // Devuelve una copia para evitar modificaciones externas si es necesario,
        // o la lista directamente si se confía en que la pantalla no la modificará.
        return new ArrayList<>(ordenesDelEmpleado);
    }

    public void ordenarPorFechaFinalizacion() {
        // Ordena la lista interna 'ordenesDelEmpleado'
        // La pantalla debería llamar a obtenerOrdenesPorEmpleado() DESPUÉS de esto si quiere la lista ordenada.
        // O, mejor, que obtenerOrdenesPorEmpleado devuelva la lista ya ordenada.
        // Aquí, modificamos la lista interna que luego se usa.
        ordenesDelEmpleado.sort(Comparator.comparing(
                OrdenDeInspeccion::getFechaHoraFinalizacion,
                Comparator.nullsLast(Comparator.naturalOrder()) // Manejar nulos en fecha
        ));
    }

    public void tomarSeleccionOrdenInspeccion(List<OrdenDeInspeccion> seleccionadas) {
        this.ordenesInspeccionSeleccionadas = new ArrayList<>(seleccionadas); // Copia para evitar modificar la lista original de la pantalla
    }

    public void tomarObservacion(String observacion) {
        this.observacion = observacion;
    }
    // ... dentro de GestorInspeccion.java ...
// ... en el método tomarConfirmacion() ...

    public void tomarConfirmacion() {
        // ... (inicio del método igual) ...
        Estado estadoOrdenCerrada = new Estado("Cerrada");
        MotivoFueraServicio motivoParaSismografo = null;

        if (this.motivoTipoSeleccionado != null) {
            motivoParaSismografo = new MotivoFueraServicio(this.comentarioMotivoSeleccionado, this.motivoTipoSeleccionado);
        }

        for (OrdenDeInspeccion orden : ordenesInspeccionSeleccionadas) {
            orden.setFechaHoraFinalizacion(this.fechaHoraActualOperacion);
            orden.setFechaHoraCierre(this.fechaHoraActualOperacion);
            orden.setObservacionCierre(this.observacion);
            orden.actualizarEstado(estadoOrdenCerrada);

            Sismografo sismografoAfectado = orden.conocerEstacionSismologica().obtenerSismografo();
            String idSismografo = null;
            Estado estadoActualSismografo = null;

            if (sismografoAfectado != null) {
                idSismografo = sismografoAfectado.getIdentificadorSismografo(); // Obtener ID
                if (motivoParaSismografo != null && this.estadoFueraDeServicioPredefinido != null) {
                    sismografoAfectado.actualizarSituacion(this.estadoFueraDeServicioPredefinido);
                    CambioEstado cambio = new CambioEstado(
                            this.fechaHoraActualOperacion,
                            this.estadoFueraDeServicioPredefinido,
                            motivoParaSismografo
                    );
                    sismografoAfectado.crearCambioEstado(cambio);
                }
                estadoActualSismografo = sismografoAfectado.obtenerEstadoActual(); // Obtener el estado después de cualquier actualización
            }

            // Notificaciones con parámetros
            InterfazMail mailSender = new InterfazMail();
            mailSender.notificarCierre(
                    orden.getNumeroOrden(),
                    idSismografo, // Puede ser null si no hay sismógrafo
                    estadoActualSismografo, // Puede ser null o el estado anterior si no cambió
                    this.fechaHoraActualOperacion,
                    orden.getObservacionCierre(), // Necesitarás un getter en OrdenDeInspeccion
                    motivoParaSismografo // Puede ser null
            );

            InterfazMonitor monitorNotifier = new InterfazMonitor();
            monitorNotifier.notificarCierre(
                    orden.getNumeroOrden(),
                    idSismografo,
                    estadoActualSismografo,
                    this.fechaHoraActualOperacion,
                    motivoParaSismografo
            );
        }

        // ... (resto del método igual) ...
    }

    // Este método parece no ser invocado directamente por la pantalla según el flujo.
    // Si es para preparar algo internamente, su lógica debe estar clara.
    // Por ahora, lo dejamos como placeholder si se requiere.
    public void habilitarActualizarSituacionSismografos() {
        // Podría haber lógica que filtre u obtenga sismógrafos asociados
        // System.out.println("Gestor: Habilitando actualización de situación de sismógrafos...");
    }

    public List<MotivoTipo> buscarMotivosFueraServicio(List<MotivoTipo> todosLosMotivosDisponibles) {
        // En un sistema real, esto podría filtrar según algún criterio,
        // o cargar desde una base de datos, etc.
        // Por ahora, devuelve la lista que le pasa la pantalla (que es una lista de ejemplo).
        return todosLosMotivosDisponibles;
    }

    public void tomarMotivo(MotivoTipo motivoTipo) {
        this.motivoTipoSeleccionado = motivoTipo;
    }

    public void tomarComentarioMotivo(String comentario) {
        this.comentarioMotivoSeleccionado = comentario;
    }


    public boolean validarObservacionYCierreDeOrden() {
        // La observación no debe ser nula ni vacía
        boolean observacionValida = observacion != null && !observacion.trim().isEmpty();
        // Debe haber al menos una orden seleccionada
        boolean ordenesSeleccionadasValidas = ordenesInspeccionSeleccionadas != null && !ordenesInspeccionSeleccionadas.isEmpty();

        // Podríamos añadir más validaciones, como si el motivo es obligatorio cuando se indica fuera de servicio.
        // if (this.motivoTipoSeleccionado != null && (this.comentarioMotivoSeleccionado == null || this.comentarioMotivoSeleccionado.trim().isEmpty())) {
        //     System.err.println("Validación: Comentario de motivo es requerido si se selecciona un motivo.");
        // return false;
        // }

        return observacionValida && ordenesSeleccionadasValidas;
    }

    // Este método del diagrama original parece ser para buscar un Estado
    // (ej. "Fuera de Servicio") a partir de su nombre.
    // Lo hemos predefinido como estadoFueraDeServicioPredefinido.
    // Si se necesitara buscarlo dinámicamente:
    public Estado buscarEstadoAAsignar(String nombreEstadoBuscado) {
        // En un sistema real, esto podría buscar en una lista de estados disponibles
        // o en una base de datos.
        // Por ahora, simplemente creamos uno nuevo con ese nombre.
        // Pero es mejor usar el predefinido si ya lo tenemos.
        if ("Fuera de Servicio".equalsIgnoreCase(nombreEstadoBuscado)) {
            return this.estadoFueraDeServicioPredefinido;
        }
        return new Estado(nombreEstadoBuscado); // Para otros casos
    }

    public LocalDateTime obtenerFechaHoraActual() {
        // Devuelve la fecha/hora que se estableció para la operación actual
        return this.fechaHoraActualOperacion;
    }

    // Este método parece redundante si la actualización ya se hace en tomarConfirmacion()
    // dentro del bucle para cada sismógrafo. Lo dejo comentado por si tiene otro propósito
    // no evidente en el flujo principal.
    /*
    public void actualizarSituacionSismografo(Sismografo sismografo, Estado nuevoEstado) {
        if (sismografo != null && nuevoEstado != null) {
            sismografo.actualizarSituacion(nuevoEstado);
            // Aquí también podría crearse un CambioEstado si es una actualización genérica
            // y no la específica del cierre de orden.
        }
    }
    */

    // Getters para que la pantalla pueda mostrar el resumen antes de confirmar
    public MotivoTipo getMotivoTipoSeleccionado() {
        return motivoTipoSeleccionado;
    }

    public String getComentarioMotivoSeleccionado() {
        return comentarioMotivoSeleccionado;
    }
}