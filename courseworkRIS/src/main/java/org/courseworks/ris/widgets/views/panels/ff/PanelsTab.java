package org.courseworks.ris.widgets.views.panels.ff;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

public class PanelsTab {

	private ExtendedTable _visualTable;

	Composite _container;
	private ToolBar _toolBar;
	private List<AbstractPanel> _panels;
	private Listener _toolBarListener;

	public PanelsTab(Composite composite, int style) {
		_panels = new ArrayList<AbstractPanel>();

		init(composite, style);
	}

	public void setTable(ExtendedTable table) {
		_visualTable = table;

		for (AbstractPanel panel : _panels) {
			panel._visualTable = table;
		}
	}

	private void init(Composite composite, int style) {
		_container = new Composite(composite, style);
		_container.setLayout(new GridLayout());

		_toolBarListener = new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				ToolItem currentItem = (ToolItem) arg0.widget;
				AbstractPanel currentPanel = (AbstractPanel) currentItem
						.getData();
				currentPanel.setVisualState(currentItem.getSelection());

				for (AbstractPanel panel : _panels) {
					if (panel != currentPanel) {
						panel.setVisualState(false);
					}
				}

				for (ToolItem item : _toolBar.getItems()) {
					if (item != currentItem) {
						item.setSelection(false);
					}
				}

				_container.layout();
				_visualTable.getParent().layout();
			}

		};

		_toolBar = new ToolBar(_container, SWT.NONE);
		_toolBar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
	}

	public void initType(DbTable dbTable) {
		for (AbstractPanel panel : _panels) {
			panel.initType(dbTable);
		}
	}

	void addPanel(AbstractPanel panel) {
		panel._visualTable = _visualTable;
		panel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		panel.setVisualState(false);
		_panels.add(panel);

		ToolItem item = new ToolItem(_toolBar, SWT.CHECK);
		item.setText(panel.getPanelName());
		item.setData(panel);
		item.addListener(SWT.Selection, _toolBarListener);
	}

	public void setLayoutData(Object layoutData) {
		_container.setLayoutData(layoutData);
	}

}
