package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;

public abstract class AbstractAction {

    protected ExtendedTable _visualTable;

    protected DbTable _table;

    protected ToolItem _item;

    protected ActionsPanel _panel;

    protected String _name;

    protected ImageDescriptor _icon;

    public AbstractAction(ExtendedTable table, ActionsPanel panel, String name,
            ImageDescriptor icon) {
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

    void setTable(DbTable table) {
        _table = table;
    }

    DbTable getTable() {
        return _table;
    }

    public abstract void run();

    public void setEnabled(boolean enabled) {
        _item.setEnabled(enabled);
    }

}
