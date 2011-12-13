package org.kai.CMV.lab4.widgets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExtendedTable extends Table {
	public static int TABLE1 = 1, TABLE2 = 2;
	private static List<Table> _tables;
	private static int _currentTable = -1;

	public ExtendedTable(Composite parent, int style) {
		super(parent, style);
	}

	public void fill(int table) throws IllegalArgumentException,
			IllegalAccessException {
		_currentTable = table;
		refresh();
	}

	public void refresh() throws IllegalArgumentException,
			IllegalAccessException {
		removeAll();
		if (_currentTable != -1) {
			for (TableItem item : _tables.get(_currentTable).getItems()) {
				TableItem newRow = new TableItem(this, SWT.NONE);
				newRow.setData(item);
			}
			for (int i = 0; i < _tables.get(_currentTable).getColumnCount(); i++) {
				this.getColumn(i).setText(
						_tables.get(_currentTable).getColumns()[i].getText());
			}
		}
	}

	public static List<Class<?>> getFields() {
		List<Class<?>> outList = null;
		switch (_currentTable) {
		case 1:
			break;
		case 2:
			break;
		}
		return outList;
	}

	public static List<String> getHeaders() {
		List<String> columnHeaders = null;
		for (TableColumn column : _tables.get(_currentTable).getColumns()) {
			columnHeaders.add(column.getText());
		}
		return columnHeaders;
	}

	@Override
	public void pack() {
		TableColumn[] columns = getColumns();

		int columnsWidth = 0;
		for (TableColumn column : columns) {
			column.pack();
			columnsWidth += column.getWidth();
		}

		// Если колонки суммарно имеют меньшюю ширину, чем таблица
		int difference = getSize().x - columnsWidth;
		if (difference > 0) {
			int additionalWidth = difference / columns.length;
			for (TableColumn column : columns) {
				column.setWidth(column.getWidth() + additionalWidth);
			}
		}
	}

	@Override
	public void checkSubclass() {

	}
}
