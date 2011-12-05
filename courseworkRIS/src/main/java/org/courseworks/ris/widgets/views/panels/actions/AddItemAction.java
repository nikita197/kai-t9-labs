package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.ExtendedTable;
import org.courseworks.ris.widgets.TableViewer;
import org.courseworks.ris.widgets.views.AddView;
import org.eclipse.jface.resource.ImageDescriptor;

public class AddItemAction extends AbstractAction {

    public AddItemAction(TableViewer tableViewer, ExtendedTable table,
            ActionsPanel panel, String name, ImageDescriptor icon) {
        super(tableViewer, table, panel, name, icon);
    }

    @Override
    public void run() {
        try {
            if (_table != null) {
                AddView view = new AddView(_panel.getShell(),
                        "Добавление записи", _table);
                view.setItem((AbstractEntity) _table.getContentType()
                        .newInstance());
                if (view.open()) {
                    AbstractEntity entity = view.getItem();
                    entity.getTable().addNewItem(entity);
                    _tableViewer.removeFilter();
                    _visualTable.refresh();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
