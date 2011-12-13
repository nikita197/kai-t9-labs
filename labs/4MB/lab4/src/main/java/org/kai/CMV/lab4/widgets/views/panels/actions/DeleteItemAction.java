package org.kai.CMV.lab4.widgets.views.panels.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.kai.CMV.lab4.widgets.ExtendedTable;
import org.kai.CMV.lab4.widgets.TableViewer;

public class DeleteItemAction extends AbstractAction {

	public DeleteItemAction(TableViewer tableViewer, ExtendedTable table,
			ActionsPanel panel, String name, ImageDescriptor icon) {
		super(tableViewer, table, panel, name, icon);
	}

	@Override
	public void run() {

	}
}
