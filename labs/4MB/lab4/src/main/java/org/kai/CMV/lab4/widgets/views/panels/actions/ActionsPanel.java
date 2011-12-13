package org.kai.CMV.lab4.widgets.views.panels.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Панель действий: добавления, ... , удаления записи
 */
public class ActionsPanel {

	private Composite _composite;
	private ToolBar _toolBar;

	private List<AbstractAction> _actions;

	public ActionsPanel(Composite composite, int style) {
		_actions = new ArrayList<AbstractAction>();

		_composite = new Composite(composite, SWT.BORDER);
		_composite.setLayout(new FillLayout());
		_toolBar = new ToolBar(_composite, style);
	}

	public void setLayoutData(Object layoutData) {
		_composite.setLayoutData(layoutData);
	}

	ToolItem addItem(AbstractAction action) {
		_actions.add(action);

		ToolItem item = new ToolItem(_toolBar, SWT.PUSH);
		item.setText(action._name);
		if (action._icon != null) {
			item.setImage(action._icon.createImage());
		}
		return item;
	}

	public Shell getShell() {
		return _composite.getShell();
	}
}
