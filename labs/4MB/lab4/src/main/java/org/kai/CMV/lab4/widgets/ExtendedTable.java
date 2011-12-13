package org.kai.CMV.lab4.widgets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.kai.CMV.lab4.widgets.typeblocks.filters.AbstractFilter;

public class ExtendedTable extends Table {
	public static int TABLE1 = 1, TABLE2 = 2;
	private static List<String[]> _tablesHeaders;
	private static List<List<Object[]>> _tablesData;
	private static int _currentTable = -1;

	public ExtendedTable(Composite parent, int style) {
		super(parent, style);

		createTables();
	}

	public void createTables() {
		String[] headers = new String[] { "Наименование", "Количество",
				"Стоимость", "Дата поступления" };
		List<Object[]> data = new ArrayList<Object[]>();

		Object[] line1 = new Object[] { "АК-47", 460, 2400,
				Calendar.getInstance() };
		Object[] line2 = new Object[] { "Ракеты противотанковые", 460, 2400,
				Calendar.getInstance() };
		Object[] line3 = new Object[] { "Гранаты", 460, 5400,
				Calendar.getInstance() };
		Object[] line4 = new Object[] { "Мины", 460, 1400,
				Calendar.getInstance() };
		Object[] line5 = new Object[] { "Плюшевые медведи", 460, 2400,
				Calendar.getInstance() };
		Object[] line6 = new Object[] { "Зачетки", 460, 400,
				Calendar.getInstance() };
		Object[] line7 = new Object[] { "Книги", 460, 2400,
				Calendar.getInstance() };
		Object[] line8 = new Object[] { "Клавиатуры", 460, 2400,
				Calendar.getInstance() };
		Object[] line9 = new Object[] { "Мышки", 460, 2400,
				Calendar.getInstance() };
		Object[] line10 = new Object[] { "Наушники", 460, 2400,
				Calendar.getInstance() };
		Object[] line11 = new Object[] { "Мониторы", 460, 2400,
				Calendar.getInstance() };
		Object[] line12 = new Object[] { "Конфеты", 460, 2400,
				Calendar.getInstance() };
		Object[] line13 = new Object[] { "Ноутбуки", 460, 2400,
				Calendar.getInstance() };

		data.add(line1);
		data.add(line2);
		data.add(line3);
		data.add(line4);
		data.add(line5);
		data.add(line6);
		data.add(line7);
		data.add(line8);
		data.add(line9);
		data.add(line10);
		data.add(line11);
		data.add(line12);
		data.add(line13);

		_tablesHeaders = new ArrayList<String[]>();
		_tablesData = new ArrayList<List<Object[]>>();

		_tablesHeaders.add(headers);
		_tablesData.add(data);

		_currentTable = 0;

		for (String column : headers) {
			TableColumn tColumn = new TableColumn(this, SWT.NONE);
			tColumn.setText(column);
			tColumn.pack();
		}
	}

	public void fill(int table) throws IllegalArgumentException,
			IllegalAccessException {
		_currentTable = table;
		refresh();
	}

	public void refresh() {
		removeAll();
		if (_currentTable != -1) {
			for (Object[] item : _tablesData.get(_currentTable)) {
				TableItem newRow = new TableItem(this, SWT.NONE);
				newRow.setData(item);

				String[] headers = _tablesHeaders.get(_currentTable);
				for (int i = 0; i < headers.length; i++) {
					String value = null;
					if (item[i] instanceof Calendar) {
						value = AbstractFilter.formateDate((Calendar) item[i]);
					} else {
						value = item[i].toString();
					}
					newRow.setText(i, value);
				}
			}
		}
	}

	public static Class<?>[] getFields() {
		return new Class<?>[] { String.class, Integer.class, Integer.class,
				Calendar.class };
	}

	public static String[] getHeaders() {
		return _tablesHeaders.get(_currentTable);
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
