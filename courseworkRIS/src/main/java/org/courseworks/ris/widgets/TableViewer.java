package org.courseworks.ris.widgets;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.views.panels.ff.FilterPanel;
import org.courseworks.ris.widgets.views.panels.ff.FindPanel;
import org.courseworks.ris.widgets.views.panels.ff.PanelsTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class TableViewer {

	private Composite _container;
	private PanelsTab _panelsTab;
	private ExtendedTable _table;

	public TableViewer(Composite parent, int style) {
		_container = new Composite(parent, style);
		_container.setLayout(new GridLayout(1, false));
		init(_container);
	}

	public void init(Composite parent) {
		_panelsTab = new PanelsTab(parent, SWT.BORDER);
		_panelsTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

		new FilterPanel(_panelsTab, "Filter", SWT.NONE);
		new FindPanel(_panelsTab, "Find", SWT.NONE);

		_table = new ExtendedTable(parent, SWT.MULTI | SWT.BORDER);
		_table.setLinesVisible(true);
		_table.setHeaderVisible(true);
		_table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_panelsTab.setTable(_table);
	}

	public void fill(DbTable dbTable) throws IllegalArgumentException,
			IllegalAccessException {
		_table.initType(dbTable);
		_panelsTab.initType(dbTable);

		_table.removeAll();

		_table.fill(dbTable);
		_table.pack();
	}
}
