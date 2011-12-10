package org.courseworks.ris.widgets.views.panels.actions;

import java.util.List;

import org.courseworks.ris.cmanager.SessionsManager;
import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.widgets.ExtendedTable;
import org.courseworks.ris.widgets.TableViewer;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;

public abstract class AbstractAction {

    protected TableViewer _tableViewer;

    protected ExtendedTable _visualTable;

    protected EntitySet _table;

    protected ToolItem _item;

    protected ActionsPanel _panel;

    protected String _name;

    protected ImageDescriptor _icon;

    public AbstractAction(TableViewer tableViewer, ExtendedTable table,
            ActionsPanel panel, String name, ImageDescriptor icon) {
        _tableViewer = tableViewer;
        _visualTable = table;
        _panel = panel;
        _name = name;
        _icon = icon;

        _item = _panel.addItem(this);

        _item.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                run();
            }

        });
    }

    void setTable(EntitySet table) {
        _table = table;
    }

    EntitySet getTable() {
        return _table;
    }

    public abstract void run();

    public void setEnabled(boolean enabled) {
        _item.setEnabled(enabled);
    }

    /**
     * Патч для обновления
     * 
     * @param table
     */
    public static void patchit(DbTable table) {
        // Payment
        if ("payment".equals(table.getName().toLowerCase())) {
            final String parkingTable = "parking";
            final String parkingPlaceTable = "parking_place";
            for (ExtendedSession session : SessionsManager.getSessions()) {
                List<DbTable> tables = session.getTables();
                for (DbTable tmp : tables) {
                    if ((parkingTable.equals(tmp.getName().toLowerCase()))
                            || (parkingPlaceTable.equals(tmp.getName()
                                    .toLowerCase()))) {
                        tmp.refresh();
                    }
                }
            }
        }

        // Parking
        if ("parking".equals(table.getName().toLowerCase())) {
            final String parkingPlaceTable = "parking_place";
            for (ExtendedSession session : SessionsManager.getSessions()) {
                List<DbTable> tables = session.getTables();
                for (DbTable tmp : tables) {
                    if (parkingPlaceTable.equals(tmp.getName().toLowerCase())) {
                        tmp.refresh();
                    }
                }
            }
        }

    }
}
