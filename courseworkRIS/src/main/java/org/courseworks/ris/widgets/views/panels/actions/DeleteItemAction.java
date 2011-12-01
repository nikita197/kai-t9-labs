package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.ExtendedTable;
import org.courseworks.ris.widgets.views.AddView;
import org.eclipse.jface.resource.ImageDescriptor;

public class DeleteItemAction extends AbstractAction {

    public DeleteItemAction(ExtendedTable table, ActionsPanel panel,
            String name, ImageDescriptor icon) {
        super(table, panel, name, icon);
    }

    @Override
    public void run() {
        AddView view;
        try {
            view =
                    new AddView(_panel.getShell(), "Добавление записи", _table,
                            AddView.TYPE_ADD);
            view.setItem(_table.getItems().get(0).getClass().newInstance());
            if (view.open()) {
                AbstractEntity entity = view.getItem();
                entity.getSession().getSession().save(entity);
                _visualTable.refresh();
            }
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }
    }
}