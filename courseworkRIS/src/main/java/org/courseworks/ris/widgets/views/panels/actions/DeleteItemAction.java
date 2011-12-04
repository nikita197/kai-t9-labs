package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.jface.resource.ImageDescriptor;

public class DeleteItemAction extends AbstractAction {

	public DeleteItemAction(ExtendedTable table, ActionsPanel panel,
			String name, ImageDescriptor icon) {
		super(table, panel, name, icon);
	}

	@Override
	public void run() {
		try {
			AbstractEntity entity = _visualTable.getSelectedItem();
			entity.getTable().deleteItem(entity);
			_visualTable.refresh();
		} catch (Exception e) {
			System.out
					.println("Удаление невозможно - существуют связаные записи");
		}
	}
}