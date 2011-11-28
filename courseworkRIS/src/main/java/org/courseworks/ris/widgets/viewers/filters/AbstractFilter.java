package org.courseworks.ris.widgets.viewers.filters;

import java.lang.reflect.Type;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.widgets.Composite;

/**
 * Абстрактный фильтр
 */
public abstract class AbstractFilter extends Composite {

    /** Тип фильтра */
    private final Type _type;

    /** Контейнер */
    protected Composite container;

    /**
     * Получение инстанса класса, в зависимости от типа фильтра
     * 
     * @param composite Композит
     * @param style Стиль
     * @param type Тип фильтра
     * @return Абстрактный фильт
     */
    public static AbstractFilter getInstance(Composite composite, int style,
            Type type) {
        if (type.equals(Integer.class)) {
            return new IntegerFilter(composite, style, type);
        } else if (type.equals(Boolean.class)) {
            return new BooleanFilter(composite, style, type);
        } else if (type.equals(String.class)) {
            return new StringFilter(composite, style, type);
        } else if (type.equals(AbstractEntity.class)) {
            return new RelatedObjectFilter(composite, style, type);
        }
        return null;
    }

    /**
     * Конструктор
     * 
     * @param composite Композит
     * @param style Стиль
     * @param type Тип фильтра
     */
    public AbstractFilter(Composite composite, int style, Type type) {
        super(composite, style);
        _type = type;

        container = new Composite(composite, style);
    }

    /**
     * Проверка отфильтровывания
     * 
     * @param value Значение
     * @return <code>true</code>, если значение отфильтровалось(прошло
     *         проверку), <code>false</code>, если значение было отброшено
     *         фильтром
     */
    public abstract boolean checkAgreement(Object value);

    public Type getType() {
        return _type;
    }

}
