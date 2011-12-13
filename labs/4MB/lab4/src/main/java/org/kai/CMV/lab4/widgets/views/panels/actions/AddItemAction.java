package org.kai.CMV.lab4.widgets.views.panels.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.kai.CMV.lab4.mappings.AbstractEntity;
import org.kai.CMV.lab4.mappings.orgelqueue.Payment;
import org.kai.CMV.lab4.widgets.ExtendedTable;
import org.kai.CMV.lab4.widgets.TableViewer;
import org.kai.CMV.lab4.widgets.views.AddView;

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

                    AbstractAction.patchit(entity.getTable());
                    if (entity instanceof Payment) {
                        ((Payment) entity).generateCost();
                    }

                    _tableViewer.removeFilter();
                    _visualTable.refresh();
                }
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
