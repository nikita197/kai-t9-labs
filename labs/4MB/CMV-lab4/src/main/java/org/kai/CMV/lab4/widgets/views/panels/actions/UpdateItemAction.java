package org.kai.CMV.lab4.widgets.views.panels.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.kai.CMV.lab4.widgets.ExtendedTable;

public class UpdateItemAction extends AbstractAction {

	public UpdateItemAction(TableViewer tableViewer, ExtendedTable table,
			ActionsPanel panel, String name, ImageDescriptor icon) {
		super(tableViewer, table, panel, name, icon);
	}

	@Override
	public void run() {
	}
}
