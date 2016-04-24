/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorcrucigrama.vista;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.GlazedLists;
import generadorcrucigrama.dato.Palabra;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import org.javabuilders.BuildResult;

import org.javabuilders.swing.SwingJavaBuilder;
import org.javabuilders.swing.plugin.glazedlists.SwingGlazedListsConfig;

/**
 *
 * @author CENTRAL
 */
public class DlgPalabra extends JDialog {

    private BuildResult result;
    private JTable table;
    private final EventList<Palabra> datos = GlazedLists.threadSafeList(new BasicEventList<Palabra>());
    private TableColumnAdjuster tableColumnAdjuster;

    public DlgPalabra(List<Palabra> palabras) {
        super();
        if (palabras != null) {
            this.datos.addAll(palabras);
        }
        result = SwingJavaBuilder.build(this);
        
        tableColumnAdjuster = new TableColumnAdjuster(table);
        tableColumnAdjuster.setDynamicAdjustment(true);
        //tableColumnAdjuster.setOnlyAdjustLarger(true);
        tableColumnAdjuster.adjustColumns();
        table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        table.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                tableColumnAdjuster.adjustColumns();
            }
        });

    }

    public void nuevo() {
        datos.add(new Palabra());
    }

    public void eliminar() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            datos.remove(index);
            table.clearSelection();
        }
    }

    public void salir() {
        List<Palabra> auxiliar = new ArrayList<Palabra>();
        for (Palabra p : datos) {
            if (!p.getEnunciado().trim().isEmpty() && !p.getRespuesta().trim().isEmpty()) {
                if (!auxiliar.contains(p)) {
                    auxiliar.add(p);
                }
            }
        }
        datos.clear();
        datos.addAll(auxiliar);
        setVisible(false);
    }

    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JDialog.setDefaultLookAndFeelDecorated(false);
        //SwingJavaBuilder.getConfig().addType(JDateChooser.class);
        SwingJavaBuilder.getConfig().addResourceBundle("GeneradorCrucigrama");
        SwingGlazedListsConfig.init();
        DlgPalabra dlg = new DlgPalabra(null);
        dlg.setModal(true);
        dlg.setVisible(true);
    }
}
