package org.courseworks.ris.widgets.views.panels.actions;

import org.courseworks.ris.widgets.views.AddView;
import org.eclipse.jface.resource.ImageDescriptor;

public class AddItemAction extends AbstractAction {

	public AddItemAction(ActionsPanel panel, String name, ImageDescriptor icon) {
		super(panel, name, icon);
	}

	@Override
	public void run() {
		AddView view = new AddView(_panel.getShell(), "Добавление записи",
				_table, AddView.TYPE_ADD);
		view.open();
	}
}
