package org.kai.CMV.lab4.mappings;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.kai.CMV.lab4.cmanager.session.DbTable;

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

	public List<AbstractEntity> getAllowedLinkedItems(Field field) {
		List<AbstractEntity> allowedItems = new LinkedList<AbstractEntity>();
		for (AbstractEntity linkedItem : _table.getRelatedTable(field)
				.getItems()) {
			if (linkedItem.isLinkAllowed()) {
				allowedItems.add(linkedItem);
			}
		}

		// add current item's field
		try {
			if (field.get(this) != null) {
				allowedItems.add((AbstractEntity) field.get(this));
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		return allowedItems;
	}

	public abstract boolean isLinkAllowed();

	@Override
	public String toString() {
		return getName();
	}

	public abstract void generateUID();

}
