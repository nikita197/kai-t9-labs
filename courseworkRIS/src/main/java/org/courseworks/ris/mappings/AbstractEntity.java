package org.courseworks.ris.mappings;

import org.courseworks.ris.cmanager.session.DbTable;

/**
 * Каждый дочерний класс должен иметь методы:<br>
 * public static String getViewName(); - возвращает представление имени таблицы<br>
 * public static Field[] getViewableFields(); - возвращает видимые поля<br>
 * public static String getFieldPresent(Field field); - возвращает представления
 * полей<br>
 */
public abstract class AbstractEntity extends Object {

	private DbTable _table;

	public abstract String getName();

	public DbTable getTable() {
		return _table;
	}

	public void setTable(DbTable table) {
		_table = table;
	}

	@Override
	public String toString() {
		return getName();
	}

	public abstract void generateUID();

	// public abstract Field[] getFields();
	//
	// public abstract Field[] getViewableFields();
	//
	// public abstract String getFieldPresent(String fieldName);
}
