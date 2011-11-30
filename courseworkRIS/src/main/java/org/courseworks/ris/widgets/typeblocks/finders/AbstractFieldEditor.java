package org.courseworks.ris.widgets.typeblocks.finders;

import java.lang.reflect.Type;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.widgets.Composite;

/**
 * Абстрактный класс поиска
 */
public abstract class AbstractFieldEditor extends Composite {

    /** Тип искомых значений */
    private final Type _type;

    /**
     * Получение инстанса класса, в зависимости от типа искомого значения
     * 
     * @param composite Композит
     * @param style Стиль
     * @param type Тип искомого значения
     * @return Абстрактный компонент поиска
     */
    public static AbstractFieldEditor getInstance(Composite composite, int style,
            Class<?> type) {
        if ((type.equals(Long.class)) || (type.equals(long.class))) {
            return new LongEditor(composite, style, type);
        } else
            if ((type.equals(Integer.class)) || (type.equals(int.class))) {
                return new IntegerEditor(composite, style, type);
            } else
                if ((type.equals(Boolean.class))
                        || (type.equals(boolean.class))) {
                    return new BooleanEditor(composite, style, type);
                } else
                    if (type.equals(String.class)) {
                        return new StringEditor(composite, style, type);
                    } else
                        if (type.getSuperclass().equals(AbstractEntity.class)) {
                            return new RelatedObjectEditor(composite, style,
                                    type);
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
    public AbstractFieldEditor(Composite composite, int style, Type type) {
        super(composite, style);
        _type = type;
    }

    /**
     * Получение искомого значения
     * 
     * @return Введенное значение для поиска
     */
    public abstract Object getSearchValue();

    public Type getType() {
        return _type;
    }

}
