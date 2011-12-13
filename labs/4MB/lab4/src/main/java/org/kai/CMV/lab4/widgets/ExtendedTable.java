package org.kai.CMV.lab4.widgets;

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExtendedTable extends Table {
	public static int TABLE1 = 1, TABLE2 = 2;
	private static List<String[]> _tablesHeaders;
	private static List<List<String[]>> _tablesData;
	private static int _currentTable = -1;

	public ExtendedTable(Composite parent, int style) {
		super(parent, style);
		createTables();
	}

	public void createTables() {
		String[] headers = null;
		List<String[]> data = null;
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
			for (String[] item : _tablesData.get(_currentTable)) {
				TableItem newRow = new TableItem(this, SWT.NONE);
				newRow.setData(item);
			}
			for (int i = 0; i < _tablesHeaders.get(_currentTable).length; i++) {
				this.getColumn(i).setText(_tablesHeaders.get(_currentTable)[i]);
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
		for (String item : _tablesHeaders.get(_currentTable)) {
			columnHeaders.add(item);
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
