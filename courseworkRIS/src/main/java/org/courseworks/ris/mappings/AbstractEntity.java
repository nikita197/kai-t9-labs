package org.courseworks.ris.mappings;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.courseworks.ris.cmanager.session.DbTable;

/**
 * Каждый дочерний класс должен иметь методы:<br>
 * public static String getViewName(); - возвращает представление имени таблицы<br>
 * public static Field[] getViewableFields(); - возвращает видимые поля<br>
 * public static String getFieldPresent(Field field); - возвращает представления
 * public static Field[] getRequiredFields(); - возвращает необходимые для
 * заполнения поля<br>
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

	public Object getFieldValue(Field field) throws IllegalArgumentException,
			IllegalAccessException {
		if (Calendar.class.equals(field.getType())) {
			Calendar date = (Calendar) field.get(this);
			if (date != null) {
				return formateDate(date);
			}
		}
		return field.get(this);
	}

	@Override
	public String toString() {
		return getName();
	}

	public abstract void generateUID();

	public static String formateDate(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		return sdf.format(date.getTime());
	}
}
