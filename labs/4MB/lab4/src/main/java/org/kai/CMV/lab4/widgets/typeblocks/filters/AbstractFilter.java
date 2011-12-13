package org.kai.CMV.lab4.widgets.typeblocks.filters;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.eclipse.swt.widgets.Composite;
import org.kai.CMV.lab4.mappings.AbstractEntity;

/**
 * Абстрактный фильтр
 */
public abstract class AbstractFilter extends Composite {

	/** Тип фильтра */
	private final Type _type;

	/**
	 * Получение инстанса класса, в зависимости от типа фильтра
	 * 
	 * @param composite
	 *            Композит
	 * @param style
	 *            Стиль
	 * @param type
	 *            Тип фильтра
	 * @return Абстрактный фильтр
	 */
	public static AbstractFilter getInstance(Composite composite, int style,
			Class<?> type) {
		if ((type.equals(Long.class)) || (type.equals(long.class))) {
			return new LongFilter(composite, style, type);
		} else if ((type.equals(Integer.class)) || (type.equals(int.class))) {
			return new IntegerFilter(composite, style, type);
		} else if ((type.equals(Boolean.class)) || (type.equals(boolean.class))) {
			return new BooleanFilter(composite, style, type);
		} else if (type.equals(String.class)) {
			return new StringFilter(composite, style, type);
		} else if (type.equals(Calendar.class)) {
			return new DateFilter(composite, style, type);
		} else if (type.getSuperclass().equals(AbstractEntity.class)) {
			return new RelatedObjectFilter(composite, style, type);
		}
		return null;
	}

	/**
	 * Конструктор
	 * 
	 * @param composite
	 *            Композит
	 * @param style
	 *            Стиль
	 * @param type
	 *            Тип фильтра
	 */
	public AbstractFilter(Composite composite, int style, Type type) {
		super(composite, style);
		_type = type;
	}

	/**
	 * Проверка отфильтровывания
	 * 
	 * @param value
	 *            Значение
	 * @return <code>true</code>, если значение отфильтровалось(прошло
	 *         проверку), <code>false</code>, если значение было отброшено
	 *         фильтром
	 */
	public abstract boolean checkAgreement(Object value);

	public Type getType() {
		return _type;
	}

	public static String formateDate(Calendar date) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
		return sdf.format(date.getTime());
	}

}
