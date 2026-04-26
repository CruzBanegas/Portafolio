// Archivo: PantallaInspeccion.java
// Paquete: Interfaz
package Interfaz;

import Controlador.GestorInspeccion;
import Interfaz.TableModel.OrdenInspeccionTableModel;
import Modelo.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.util.*;
import java.util.List;

public class PantallaInspeccion extends JFrame {
    private GestorInspeccion gestor;

    // --- Componentes UI ---
    private JButton btnMostrarFormularioCierre; // Nuevo botón para iniciar el proceso
    private JPanel panelPrincipalCierre;      // Panel que contendrá la tabla y campos de cierre
    private boolean formularioCierreVisible = false; // Para controlar la visibilidad

    private JLabel lblEmpleadoLogueado;
    private JTable tblOrdenesInspeccion;
    private OrdenInspeccionTableModel ordenTableModel;
    private JTextArea txtObservacionCierre;
    private JList<MotivoTipo> lstMotivosTipo;
    private JPanel panelMotivosAgregados;
    private List<MotivoConComentario> motivosSeleccionados = new ArrayList<>();
    private JCheckBox chkFueraDeServicio;
    private JTextArea txtComentarioMotivo;
    private JButton btnCerrarOrdenesConfirmar; // Renombrado para claridad
    private JPanel panelMotivo;
    private List<MotivoTipo> motivosTipoDisponiblesCache;
    // --- Fin Componentes UI ---

    public PantallaInspeccion(GestorInspeccion gestor, Scanner scanner) {
        this.gestor = gestor;
        this.motivosTipoDisponiblesCache = this.gestor.buscarMotivosFueraServicio(crearMotivosTipoEjemplo());

        setTitle("Sistema de Inspecciones"); // Título más general al inicio
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 200); // Tamaño inicial más pequeño
        setLocationRelativeTo(null);

        // Inicializa TODOS los componentes, pero algunos estarán ocultos al principio
        initComponents();
        layoutComponentesIniciales(); // Layout para el estado inicial
        initListeners();
    }

    // Main llama a este método
    public void opcionCerrarOrdenInspeccion() {
        // Solo hace visible la ventana en su estado inicial.
        // La carga de datos y el formulario de cierre se activarán con el botón.
        if (!this.isVisible()) {
            this.setVisible(true);
        }
    }

    private void initComponents() {
        // 1. Componentes principales
        btnMostrarFormularioCierre = new JButton("Cerrar Orden de Inspección");
        lblEmpleadoLogueado = new JLabel("Empleado: ");

        // 2. Configuración de tabla
        ordenTableModel = new OrdenInspeccionTableModel(new ArrayList<>());
        tblOrdenesInspeccion = new JTable(ordenTableModel);
        tblOrdenesInspeccion.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        tblOrdenesInspeccion.setAutoCreateRowSorter(true);

        // 3. Áreas de texto
        txtObservacionCierre = new JTextArea(5, 30);
        txtObservacionCierre.setLineWrap(true);
        txtObservacionCierre.setWrapStyleWord(true);

        // 4. Checkbox
        chkFueraDeServicio = new JCheckBox("¿Sismógrafo/s quedará/n 'Fuera de Servicio'?");

        // 5. Configuración del sistema de motivos
        configurarComponentesMotivos();

        // 6. Botón de confirmación
        btnCerrarOrdenesConfirmar = new JButton("Confirmar Cierre de Órdenes Seleccionadas");

        // 7. Panel principal
        panelPrincipalCierre = new JPanel(new BorderLayout(10, 10));
        panelPrincipalCierre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void configurarComponentesMotivos() {
        // Lista de motivos
        lstMotivosTipo = new JList<>(new Vector<>(motivosTipoDisponiblesCache));
        lstMotivosTipo.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        lstMotivosTipo.setVisibleRowCount(4);
        lstMotivosTipo.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof MotivoTipo) {
                    setText(((MotivoTipo) value).obtenerDescripcion());
                }
                return this;
            }
        });

        // Panel para motivos agregados
        panelMotivosAgregados = new JPanel();
        panelMotivosAgregados.setLayout(new BoxLayout(panelMotivosAgregados, BoxLayout.Y_AXIS));

        // Área de comentario
        txtComentarioMotivo = new JTextArea(3, 30);
        txtComentarioMotivo.setLineWrap(true);
        txtComentarioMotivo.setWrapStyleWord(true);

        // Botón y acción para agregar motivos
        JButton btnAgregarMotivo = new JButton("Agregar Motivo(s)");
        btnAgregarMotivo.addActionListener(this::agregarMotivosAction);

        // Configuración del panel de motivos
        panelMotivo = new JPanel(new BorderLayout(10, 10));
        panelMotivo.add(crearPanelSeleccionMotivos(), BorderLayout.NORTH);
        panelMotivo.add(crearPanelComentario(btnAgregarMotivo), BorderLayout.CENTER);
        panelMotivo.add(new JScrollPane(panelMotivosAgregados), BorderLayout.SOUTH);
        panelMotivo.setVisible(false);
    }

    private JPanel crearPanelSeleccionMotivos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Seleccione motivos:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(lstMotivosTipo), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelComentario(JButton btnAgregarMotivo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Comentario:"), BorderLayout.NORTH);
        panel.add(new JScrollPane(txtComentarioMotivo), BorderLayout.CENTER);
        panel.add(btnAgregarMotivo, BorderLayout.SOUTH);
        return panel;
    }

    private void agregarMotivosAction(ActionEvent e) {
        List<MotivoTipo> seleccionados = lstMotivosTipo.getSelectedValuesList();
        String comentario = txtComentarioMotivo.getText().trim();

        if (seleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un motivo", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        seleccionados.forEach(motivo -> {
            MotivoConComentario mcc = new MotivoConComentario(motivo, comentario);
            motivosSeleccionados.add(mcc);
            panelMotivosAgregados.add(new JLabel(
                    String.format("- %s: %s", motivo.obtenerDescripcion(),
                            comentario.isEmpty() ? "Sin comentario" : comentario)
            ));
        });

        panelMotivosAgregados.revalidate();
        txtComentarioMotivo.setText("");
    }

    private void layoutComponentesIniciales() {
        // Layout simple para el botón inicial
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 50)); // Centrar el botón
        add(btnMostrarFormularioCierre);
        // El panelPrincipalCierre no se añade aún al JFrame directamente, o se añade y se oculta.
        // Por simplicidad, lo construiremos y añadiremos cuando se necesite.
    }

    private void construirYMostrarFormularioCierre() {
        // Cambiar el título de la ventana
        setTitle("Cierre de Órdenes de Inspección");

        // Limpiar el layout anterior (el del botón)
        getContentPane().removeAll(); // Quita todos los componentes del content pane
        setLayout(new BorderLayout()); // Establecer el layout principal para el formulario

        // Construir el panelPrincipalCierre con sus componentes
        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        northPanel.add(lblEmpleadoLogueado);
        panelPrincipalCierre.add(northPanel, BorderLayout.NORTH);

        panelPrincipalCierre.add(new JScrollPane(tblOrdenesInspeccion), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        int y = 0;
        gbc.gridx = 0; gbc.gridy = y++; gbc.gridwidth = 2;
        southPanel.add(new JLabel("Seleccione de la tabla las órdenes a cerrar:"), gbc);
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 1; southPanel.add(new JLabel("Observación de Cierre:"), gbc);
        gbc.gridx = 1; southPanel.add(new JScrollPane(txtObservacionCierre), gbc);
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 2; southPanel.add(chkFueraDeServicio, gbc);
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.weightx = 1.0; southPanel.add(panelMotivo, gbc);
        gbc.gridy = y++; gbc.gridx = 0; gbc.gridwidth = 2; gbc.fill = GridBagConstraints.NONE; gbc.anchor = GridBagConstraints.CENTER;
        southPanel.add(btnCerrarOrdenesConfirmar, gbc); // Usar el botón renombrado
        panelPrincipalCierre.add(southPanel, BorderLayout.SOUTH);

        // Añadir el panelPrincipalCierre al content pane del JFrame
        add(panelPrincipalCierre, BorderLayout.CENTER);

        // Ajustar tamaño de la ventana y revalidar
        setSize(900, 700); // Tamaño para el formulario completo
        setLocationRelativeTo(null);
        revalidate();
        repaint();

        formularioCierreVisible = true;
    }


    private void initListeners() {
        btnMostrarFormularioCierre.addActionListener(e -> {
            // 1. RI: selecciona la opción “Cerrar Orden de Inspección”. (Este es el evento)
            construirYMostrarFormularioCierre(); // Muestra el formulario
            habilitarPantalla_CargarDatos();     // Carga los datos en el formulario recién mostrado
        });

        chkFueraDeServicio.addItemListener(e -> {
            if (!formularioCierreVisible) return; // No hacer nada si el formulario no está visible
            boolean seleccionado = e.getStateChange() == ItemEvent.SELECTED;
            panelMotivo.setVisible(seleccionado);
            // ... (lógica de habilitar/deshabilitar lstMotivosTipo y txtComentarioMotivo como antes)
            boolean hayMotivosValidos = motivosTipoDisponiblesCache != null && !motivosTipoDisponiblesCache.isEmpty() &&
                    (lstMotivosTipo.getModel().getSize() == 0 || !((MotivoTipo)lstMotivosTipo.getModel().getElementAt(0)).obtenerDescripcion().equals("No hay motivos configurados"));
            lstMotivosTipo.setEnabled(seleccionado && hayMotivosValidos);
            txtComentarioMotivo.setEnabled(seleccionado && hayMotivosValidos);

            // Ajustar tamaño solo si el panelMotivo cambia de visibilidad
            // this.pack(); // Puede ser muy agresivo
            // this.setSize(Math.max(900, getWidth()), Math.max(700, getHeight()));
            // this.setLocationRelativeTo(null);
            // Es mejor que el panel southPanel ya tenga un tamaño preferido adecuado.
            revalidate();
            repaint();
        });

        btnCerrarOrdenesConfirmar.addActionListener(this::accionConfirmarCierreOrdenes);
    }

    // Renombrado para distinguir de la preparación inicial de la ventana
    private void habilitarPantalla_CargarDatos() {
        // 2. Sistema: busca el empleado... y todas las órdenes...
        gestor.nuevoCierreOrden();

        Empleado empleadoLogueado = gestor.obtenerEmpleadoLogueado();
        lblEmpleadoLogueado.setText("Empleado: " + (empleadoLogueado != null ?
                empleadoLogueado.getNombre() + " " + empleadoLogueado.getApellido() : "No identificado"));

        List<OrdenDeInspeccion> ordenesDelEmpleado = gestor.obtenerOrdenesPorEmpleado();
        List<OrdenDeInspeccion> ordenesAbiertasParaMostrar = new ArrayList<>();
        // ... (filtrado y ordenado de órdenes como antes)
        if (ordenesDelEmpleado != null) {
            for (OrdenDeInspeccion orden : ordenesDelEmpleado) {
                if (orden.conocerEstado() != null && !orden.conocerEstado().esCerrada()) {
                    ordenesAbiertasParaMostrar.add(orden);
                }
            }
        }

        if (ordenesAbiertasParaMostrar.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "No tiene órdenes de inspección abiertas asignadas.",
                    "Información", JOptionPane.INFORMATION_MESSAGE);
            btnCerrarOrdenesConfirmar.setEnabled(false);
        } else {
            ordenesAbiertasParaMostrar.sort(Comparator.comparing(
                    OrdenDeInspeccion::getFechaHoraFinalizacion,
                    Comparator.nullsLast(Comparator.naturalOrder())
            ));
            btnCerrarOrdenesConfirmar.setEnabled(true);
        }
        ordenTableModel.setOrdenes(ordenesAbiertasParaMostrar);
    }

    // accionConfirmarCierreOrdenes y limpiarFormularioYRefrescar permanecen similares
    // pero limpiarFormularioYRefrescar podría resetear la pantalla al estado inicial del botón.

    private void accionConfirmarCierreOrdenes(ActionEvent e) {
        // 1. Validar selección de órdenes
        int[] selectedRows = tblOrdenesInspeccion.getSelectedRows();
        if (selectedRows.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar al menos una orden de inspección.",
                    "Error de Selección",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Obtener órdenes seleccionadas
        List<OrdenDeInspeccion> ordenesSeleccionadas = new ArrayList<>();
        for (int rowIndex : selectedRows) {
            ordenesSeleccionadas.add(
                    ordenTableModel.getOrdenAt(tblOrdenesInspeccion.convertRowIndexToModel(rowIndex))
            );
        }

        // 3. Validar motivos si está marcado "Fuera de Servicio"
        if (chkFueraDeServicio.isSelected()) {
            if (motivosSeleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Debe agregar al menos un motivo para 'Fuera de Servicio'.",
                        "Error de Validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }

        // 4. Tomar datos y confirmar
        gestor.tomarSeleccionOrdenInspeccion(ordenesSeleccionadas);
        gestor.tomarObservacion(txtObservacionCierre.getText().trim());

        if (chkFueraDeServicio.isSelected()) {
            gestor.tomarMotivos(motivosSeleccionados); // Pasar todos los motivos con comentarios
        } else {
            gestor.tomarMotivos(new ArrayList<>()); // Lista vacía si no es "Fuera de Servicio"
        }

        // 5. Mostrar resumen
        StringBuilder resumen = new StringBuilder("--- Resumen de Cierre ---\n");
        resumen.append("Órdenes a cerrar: ").append(ordenesSeleccionadas.size()).append("\n");
        resumen.append("Observación: ").append(txtObservacionCierre.getText().trim().isEmpty() ?
                "(Sin observación)" : txtObservacionCierre.getText().trim()).append("\n");

        if (chkFueraDeServicio.isSelected()) {
            resumen.append("\nMotivos de 'Fuera de Servicio':\n");
            for (MotivoConComentario motivo : motivosSeleccionados) {
                resumen.append("- ").append(motivo.getMotivo().obtenerDescripcion())
                        .append(": ").append(motivo.getComentario()).append("\n");
            }
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                resumen.toString(),
                "Confirmar Cierre",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // 6. Procesar confirmación
        if (confirmacion == JOptionPane.YES_OPTION) {
            if (gestor.validarObservacionYCierreDeOrden()) {
                gestor.tomarConfirmacion();
                JOptionPane.showMessageDialog(this,
                        "Órdenes cerradas exitosamente",
                        "Éxito",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiarFormularioYRefrescar_RecargarDatos();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Error al validar el cierre. Revise los datos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormularioYRefrescar_RecargarDatos() {
        // 1. Limpiar componentes principales (originales)
        txtObservacionCierre.setText("");
        chkFueraDeServicio.setSelected(false);

        // 2. Limpiar componentes NUEVOS de selección múltiple:
        lstMotivosTipo.clearSelection();  // Deseleccionar items en la JList
        txtComentarioMotivo.setText(""); // Limpiar área de comentarios

        // 3. Limpiar motivos agregados:
        panelMotivosAgregados.removeAll(); // Eliminar los JLabels mostrados
        motivosSeleccionados.clear();     // Vaciar la lista interna

        // 4. Resetear visibilidad del panel
        panelMotivo.setVisible(false);

        // 5. Recargar datos de órdenes disponibles
        habilitarPantalla_CargarDatos();

        // 6. Actualizar la interfaz
        panelMotivosAgregados.revalidate();
        panelMotivosAgregados.repaint();
    }

    // Opcional: un método para resetear completamente al estado inicial del botón
    private void resetearAPantallaInicial() {
        getContentPane().removeAll();
        layoutComponentesIniciales();
        setTitle("Sistema de Inspecciones");
        setSize(500, 200);
        setLocationRelativeTo(null);
        formularioCierreVisible = false;
        revalidate();
        repaint();
    }

    private List<MotivoTipo> crearMotivosTipoEjemplo() {
        // REEMPLAZAR por estos nuevos nombres:
        return Arrays.asList(
                new MotivoTipo("Falla eléctrica"),
                new MotivoTipo("Daño físico"),
                new MotivoTipo("Calibración requerida"),
                new MotivoTipo("Interferencia ambiental")
        );
    }
}