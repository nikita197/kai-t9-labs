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

		data.add(getNewLine("АК-47", 460, 2400));
		data.add(getNewLine("Ракеты противотанковые", 460, 2400));
		data.add(getNewLine("Гранаты", 460, 5400));
		data.add(getNewLine("Мины", 460, 1400));
		data.add(getNewLine("Плюшевые медведи", 460, 2400));
		data.add(getNewLine("Зачетки", 460, 400));
		data.add(getNewLine("Книги", 460, 2400));
		data.add(getNewLine("Клавиатуры", 460, 2400));
		data.add(getNewLine("Мышки", 460, 2400));
		data.add(getNewLine("Наушники", 460, 2400));
		data.add(getNewLine("Мониторы", 460, 2400));
		data.add(getNewLine("Конфеты", 460, 2400));
		data.add(getNewLine("Ноутбуки", 460, 2400));

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

	private int getRandom(int value) {
		return (int) (Math.floor(Math.random() * value) + 1);
	}

	private Object[] getNewLine(String name, int number, int value) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(2011, getRandom(11), getRandom(29), getRandom(23),
				getRandom(58), getRandom(58));
		return new Object[] { name, number, value, calendar };
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
