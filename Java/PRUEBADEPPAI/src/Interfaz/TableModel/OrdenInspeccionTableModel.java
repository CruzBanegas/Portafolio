// Archivo: Interfaz/TableModel/OrdenInspeccionTableModel.java
package Interfaz.TableModel;

import Modelo.OrdenDeInspeccion;
import Modelo.Sismografo;
import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter; // Importación correcta
import java.util.ArrayList;
import java.util.List;

public class OrdenInspeccionTableModel extends AbstractTableModel {
    private List<OrdenDeInspeccion> ordenes;
    private final String[] columnNames = {"N° Orden", "Nombre de la Estación", "Sismógrafo ID", "Estado Actual", "Fecha Finalizacion"};

    // ***** CAMBIO AQUÍ: Modificar el patrón del formateador *****
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Solo día, mes y año

    public OrdenInspeccionTableModel(List<OrdenDeInspeccion> ordenes) {
        this.ordenes = new ArrayList<>(ordenes);
    }

    public void setOrdenes(List<OrdenDeInspeccion> ordenes) {
        this.ordenes = new ArrayList<>(ordenes);
        fireTableDataChanged();
    }

    public OrdenDeInspeccion getOrdenAt(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < ordenes.size()) {
            return ordenes.get(rowIndex);
        }
        return null;
    }

    @Override
    public int getRowCount() {
        return ordenes.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        OrdenDeInspeccion orden = ordenes.get(rowIndex);
        switch (columnIndex) {
            case 0: return orden.getNumeroOrden();
            case 1: return orden.conocerEstacionSismologica().getNombre();
            case 2:
                Sismografo s = orden.conocerEstacionSismologica().obtenerSismografo();
                return (s != null) ? s.getIdentificadorSismografo() : "N/D";
            case 3: return orden.conocerEstado().getNombre();
            case 4: // Columna "Finalización Prevista"
                return orden.getFechaHoraFinalizacion() != null ?
                        orden.getFechaHoraFinalizacion().format(DATE_FORMATTER) : "N/A"; // Usar el nuevo formateador
            default: return null;
        }
    }
}