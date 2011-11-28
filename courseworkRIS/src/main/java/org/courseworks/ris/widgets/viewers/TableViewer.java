package org.courseworks.ris.widgets.viewers;

import java.lang.reflect.Field;
import org.courseworks.ris.cmanager.session.DbTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableViewer {

	private Table _table;
	private Field[] _fields;
	private FindPanel _findP;
	private FilterPanel _filtP;

	public TableViewer(Composite parent, int style) {
		Composite _compt = new Composite(parent, SWT.NONE);
		_compt.setLayout(new GridLayout(2, false));
		init(_compt, style);
	}

	public void init(Composite parent, int style) {
		_table = new Table(parent, style);
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

	public void fill(DbTable dbTable)
			throws IllegalArgumentException, IllegalAccessException {
		_table.removeAll();
		createColumns(dbTable.getType());
		refresh(dbTable);
		pack();
		_filtP.initContent(dbTable);
		_findP.initContent(dbTable);
	}

	public void createColumns(Class<?> cls) {
		_fields = cls.getDeclaredFields();
		for (Field fld : _fields) {
			TableColumn newColumn = new TableColumn(_table, SWT.NONE);
			newColumn.setData(fld.getClass());
			newColumn.setText(fld.getName());
		}
	}

	public void refresh(DbTable dbTable)
			throws IllegalArgumentException, IllegalAccessException {
		for (Object obj : dbTable.getItems()) {
			TableItem newRow = new TableItem(_table, SWT.NONE);
			newRow.setData(obj);
			int index = 0;
			for (Field fld : _fields) {
				newRow.setText(index, fld.get(obj).toString());
				index++;
			}
		}
	}

	public void pack() {
		for (TableColumn tbc : _table.getColumns()) {
			tbc.pack();
		}
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