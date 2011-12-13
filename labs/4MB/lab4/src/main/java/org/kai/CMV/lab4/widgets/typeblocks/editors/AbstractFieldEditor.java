package org.kai.CMV.lab4.widgets.typeblocks.editors;

import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;

public abstract class AbstractFieldEditor extends Composite {

	public static final int EDIT = 1;
	public static final int FIND = 2;

	protected final Class<?> _field;
	protected boolean nullable;

	public static AbstractFieldEditor getInstance(Composite composite,
			int style, Class<?> type, int role, boolean nullable) {
		if ((type.equals(Long.class)) || (type.equals(long.class))) {
			return new LongEditor(composite, style, type, nullable);
		} else if ((type.equals(Integer.class)) || (type.equals(int.class))) {
			return new IntegerEditor(composite, style, type, nullable);
		} else if ((type.equals(Boolean.class)) || (type.equals(boolean.class))) {
			return new BooleanEditor(composite, style, type, nullable);
		} else if (type.equals(Calendar.class)) {
			return new DateEditor(composite, style, type, nullable);
		} else if (type.equals(String.class)) {
			return new StringEditor(composite, style, type, nullable);
		}
		return null;
	}

	public AbstractFieldEditor(Composite composite, int style, Class<?> type,
			boolean nullable) {
		super(composite, style);
		_field = type;
		this.nullable = nullable;
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

	public Class<?> getType() {
		return _field;
	}

}
