package org.courseworks.ris.widgets.views.panels.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Панель действий: добавления, ... , удаления записи
 */
public class ActionsPanel {

	private Composite _composite;
	private ToolBar _toolBar;

	public ActionsPanel(Composite composite, int style) {
		_composite = new Composite(composite, SWT.BORDER);
		_composite.setLayout(new FillLayout());
		_toolBar = new ToolBar(_composite, style);
	}

	public void setLayoutData(Object layoutData) {
		_composite.setLayoutData(layoutData);
	}

	ToolItem addItem(AbstractAction action) {
		ToolItem item = new ToolItem(_toolBar, SWT.PUSH);
		item.setText(action._name);
		item.setImage(action._icon.createImage());
		return item;
	}
}
