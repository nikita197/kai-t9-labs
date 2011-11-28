package org.courseworks.ris.widgets.viewers;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.viewers.filters.AbstractFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FilterPanel extends Composite {
	private Combo _fieldCombo;
	private Table _table;
	private AbstractFilter _filter;
	private DbTable _dbTable;

	public FilterPanel(Composite parent, int style, Table table) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		this.setVisible(false);

		_table = table;
	}

	public void initContent(DbTable dbTable) {
		_dbTable = dbTable;

		_fieldCombo = new Combo(this, SWT.BORDER);
		for (TableColumn tbc : _table.getColumns()) {
			_fieldCombo.add(tbc.getText());
		}

		Button filterButton = new Button(this, SWT.NONE);
		filterButton.setText("Filter");

		Button clearButton = new Button(this, SWT.NONE);
		clearButton.setText("Clear filter");

		_fieldCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_filter = AbstractFilter.getInstance(FilterPanel.this,
						SWT.BORDER,
						_dbTable.getFieldType(_fieldCombo.getText()));
			}
		});
		filterButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					refreshTable();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				final int column = _fieldCombo.getSelectionIndex();
				for (TableItem item : _table.getItems()) {
					if (!_filter.checkAgreement(item.getText(column))) {
						item.dispose();
					}
				}
			}
		});

		clearButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					refreshTable();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		});
	}

	private void refreshTable() throws IllegalArgumentException,
			IllegalAccessException {
		for (Object obj : _dbTable.getItems()) {
			TableItem newRow = new TableItem(_table, SWT.NONE);
			newRow.setData(obj);
			int index = 0;
			for (Field fld : _dbTable.getType().getDeclaredFields()) {
				newRow.setText(index, fld.get(obj).toString());
				index++;
			}
		}
	}
}
