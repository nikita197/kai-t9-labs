package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.orgelqueue.Payment;
import org.courseworks.ris.widgets.ExtendedTable;
import org.courseworks.ris.widgets.TableViewer;
import org.courseworks.ris.widgets.views.UpdateView;
import org.eclipse.jface.resource.ImageDescriptor;

public class UpdateItemAction extends AbstractAction {

    public UpdateItemAction(TableViewer tableViewer, ExtendedTable table,
            ActionsPanel panel, String name, ImageDescriptor icon) {
        super(tableViewer, table, panel, name, icon);
    }

    @Override
    public void run() {
        try {
            if (_visualTable.getSelectedItem() != null) {
                UpdateView view = new UpdateView(_panel.getShell(),
                        "Изменение записи", _table);
                view.setItem(_visualTable.getSelectedItem());
                if (view.open()) {
                    AbstractEntity entity = view.getItem();
                    entity.getTable().updateItem(entity);

                    AbstractAction.patchit(entity.getTable());
                    if (entity instanceof Payment) {
                        ((Payment) entity).generateCost();
                    }

                    _tableViewer.removeFilter();
                    _visualTable.refresh();
                }
            }
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}
