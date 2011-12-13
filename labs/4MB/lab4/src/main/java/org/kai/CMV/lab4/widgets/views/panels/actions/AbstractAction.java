package org.kai.CMV.lab4.widgets.views.panels.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolItem;
import org.kai.CMV.lab4.widgets.ExtendedTable;
import org.kai.CMV.lab4.widgets.TableViewer;

public abstract class AbstractAction {

	protected TableViewer _tableViewer;

	protected ExtendedTable _visualTable;

	protected ToolItem _item;

	protected ActionsPanel _panel;

	protected String _name;

	protected ImageDescriptor _icon;

	public AbstractAction(TableViewer tableViewer, ExtendedTable table,
			ActionsPanel panel, String name, ImageDescriptor icon) {
		_tableViewer = tableViewer;
		_visualTable = table;
		_panel = panel;
		_name = name;
		_icon = icon;

		_item = _panel.addItem(this);

		_item.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				run();
			}

		});
	}

	public abstract void run();

	public void setEnabled(boolean enabled) {
		_item.setEnabled(enabled);
	}

}
