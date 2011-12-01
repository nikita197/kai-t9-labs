package org.courseworks.ris.widgets;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExtendedTable extends Table {

	private DbTable _dbTable;

	private Field[] _fields;

	public ExtendedTable(Composite parent, int style) {
		super(parent, style);
	}

	public void initType(DbTable dbTable) {
		removeAll();
		for (TableColumn column : getColumns()) {
			column.dispose();
		}

		_fields = dbTable.getViewableFields();
		for (Field fld : _fields) {
			TableColumn newColumn = new TableColumn(this, SWT.NONE);
			newColumn.setData(fld);
			newColumn.setText(dbTable.getFieldPresentation(fld.getName()));
		}
	}

	public void fill(DbTable dbTable) throws IllegalArgumentException,
			IllegalAccessException {
		_dbTable = dbTable;
		refresh();
	}

	public void refresh() throws IllegalArgumentException,
			IllegalAccessException {
		removeAll();
		if (_dbTable != null) {
			for (Object obj : _dbTable.getItems()) {
				TableItem newRow = new TableItem(this, SWT.NONE);
				newRow.setData(obj);
				int index = 0;
				for (Field fld : _fields) {
					newRow.setText(index, fld.get(obj).toString());
					index++;
				}
			}
		}
	}

	public AbstractEntity getSelectedItem() {
		int index = getSelectionIndex();

		if (index == -1) {
			return null;
		}

		return (AbstractEntity) getItem(index).getData();
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
