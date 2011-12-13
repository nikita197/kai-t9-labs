package org.kai.CMV.lab4.widgets.views.panels.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.kai.CMV.lab4.mappings.AbstractEntity;
import org.kai.CMV.lab4.widgets.ExtendedTable;
import org.kai.CMV.lab4.widgets.TableViewer;

public class DeleteItemAction extends AbstractAction {

    public DeleteItemAction(TableViewer tableViewer, ExtendedTable table,
            ActionsPanel panel, String name, ImageDescriptor icon) {
        super(tableViewer, table, panel, name, icon);
    }

    @Override
    public void run() {
        try {
            if (_visualTable.getSelectedItem() != null) {
                AbstractEntity entity = _visualTable.getSelectedItem();
                entity.getTable().deleteItem(entity);

                AbstractAction.patchit(entity.getTable());

                _tableViewer.removeFilter();
                _visualTable.refresh();
            }
        } catch (Exception e) {
            MessageBox msgBox = new MessageBox(_panel.getShell(), SWT.OK
                    | SWT.ICON_ERROR);
            msgBox.setText("Удаление невозможно.");
            msgBox.setMessage("Существуют записи, связанные с этой записью.");
            msgBox.open();
        }
    }
}
