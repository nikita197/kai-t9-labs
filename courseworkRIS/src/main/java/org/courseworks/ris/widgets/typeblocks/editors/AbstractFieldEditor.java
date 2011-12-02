package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Field;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.widgets.Composite;

public abstract class AbstractFieldEditor extends Composite {

	public static final int EDIT = 1;
	public static final int FIND = 2;

	protected final Field _field;
	protected final int editType;

	public static AbstractFieldEditor getInstance(Composite composite,
			int style, Field field, int editType) {
		Class<?> type = field.getType();
		if ((type.equals(Long.class)) || (type.equals(long.class))) {
			return new LongEditor(composite, style, field, editType);
		} else if ((type.equals(Integer.class)) || (type.equals(int.class))) {
			return new IntegerEditor(composite, style, field, editType);
		} else if ((type.equals(Boolean.class)) || (type.equals(boolean.class))) {
			return new BooleanEditor(composite, style, field, editType);
		} else if (type.equals(String.class)) {
			return new StringEditor(composite, style, field, editType);
		} else if (type.getSuperclass().equals(AbstractEntity.class)) {
			return new RelatedObjectEditor(composite, style, field, editType);
		}
		return null;
	}

	public AbstractFieldEditor(Composite composite, int style, Field field,
			int editType) {
		super(composite, style);
		_field = field;
		this.editType = editType;
	}

	/**
	 * Получение искомого значения
	 * 
	 * @return Введенное значение для поиска
	 */
	public abstract Object getValue();

	/**
	 * Заполнение поля ввода значением
	 * 
	 * @param value
	 *            Значение
	 */
	public abstract void setValue(Object value);

	/**
	 * Установка редактируемого объекта
	 * 
	 * @param item
	 *            Редактируемый объект
	 */
	public abstract void setEditingItem(AbstractEntity item);

	public Field getField() {
		return _field;
	}

	public Class<?> getType() {
		return _field.getType();
	}

}
