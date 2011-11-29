package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.cmanager.session.DbTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class TableViewer {

	private ExtendedTable _table;
	private FindPanel _findP;
	private FilterPanel _filtP;

	public TableViewer(Composite parent, int style) {
		Composite _compt = new Composite(parent, SWT.NONE);
		_compt.setLayout(new GridLayout(2, false));
		init(_compt, SWT.MULTI);
	}

	public void init(Composite parent, int style) {
		_table = new ExtendedTable(parent, style);
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, true);
		data.heightHint = 200;
		_table.setLayoutData(data);

		final Composite toolsPanel = new Composite(parent, SWT.NONE);
		toolsPanel.setLayout(new GridLayout(1, false));
		toolsPanel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		Composite mainPanel = new Composite(toolsPanel, SWT.NONE);
		mainPanel.setLayout(new GridLayout(3, false));

		Button filterButton = new Button(mainPanel, SWT.TOGGLE);
		Button findButton = new Button(mainPanel, SWT.TOGGLE);
		filterButton.setText("Filter");
		findButton.setText("Find");

		_filtP = new FilterPanel(toolsPanel, SWT.BORDER, _table);
		_findP = new FindPanel(toolsPanel, SWT.BORDER, _table);

		_filtP.setLayout(new GridLayout(1, false));
		_filtP.setLayoutData(new GridData(SWT.TOP, SWT.TOP, true, true));
		_filtP.setVisible(false);
		_findP.setLayout(new GridLayout(1, false));
		_findP.setLayoutData(new GridData(SWT.TOP, SWT.TOP, true, true));
		_findP.setVisible(false);

		filterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				filterButtonClick(toolsPanel);
			}
		});
		findButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				findButtonClick(toolsPanel);
			}
		});
	}

	public void fill(DbTable dbTable) throws IllegalArgumentException,
			IllegalAccessException {
		_table.removeAll();
		_table.createColumns(dbTable.getType());
		_table.refresh(dbTable);
		_table.pack();
		_filtP.initContent(dbTable);
		_findP.initContent(dbTable);
	}

	private void findButtonClick(Composite parent) {
		if (true == _findP.getVisible()) {
			_findP.setVisible(false);
		} else {
			GridData filterpGridData = ((GridData) _filtP.getLayoutData());
			GridData findpGridData = ((GridData) _findP.getLayoutData());
			filterpGridData.exclude = true;
			findpGridData.exclude = false;
			_findP.setVisible(true);
			_filtP.setVisible(false);
			parent.layout();
		}
	}

	private void filterButtonClick(Composite parent) {
		if (true == _filtP.getVisible()) {
			_filtP.setVisible(false);
		} else {
			GridData filterpGridData = ((GridData) _filtP.getLayoutData());
			filterpGridData.exclude = false;
			_filtP.setVisible(true);
			_findP.setVisible(false);
			parent.layout();
		}
	}
}