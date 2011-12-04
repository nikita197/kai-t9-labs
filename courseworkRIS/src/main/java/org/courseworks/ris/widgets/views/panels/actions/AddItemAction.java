package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.ExtendedTable;
import org.courseworks.ris.widgets.views.UpdateAddView;
import org.eclipse.jface.resource.ImageDescriptor;

public class AddItemAction extends AbstractAction {

	public AddItemAction(ExtendedTable table, ActionsPanel panel, String name,
			ImageDescriptor icon) {
		super(table, panel, name, icon);
	}

	@Override
	public void run() {
		UpdateAddView view;
		try {
			view = new UpdateAddView(_panel.getShell(), "Добавление записи", _table,
					UpdateAddView.TYPE_ADD);
			view.setItem((AbstractEntity) _table.getContentType().newInstance());
			if (view.open()) {
				AbstractEntity entity = view.getItem();
				entity.getTable().addNewItem(entity);
				_visualTable.refresh();
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
