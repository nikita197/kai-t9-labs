package org.courseworks.ris.widgets.typeblocks.finders;

import java.lang.reflect.Type;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.widgets.Composite;

/**
 * Абстрактный класс поиска
 */
public abstract class AbstractFinder extends Composite {

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
    public static AbstractFinder getInstance(Composite composite, int style,
            Class<?> type) {
        if ((type.equals(Long.class)) || (type.equals(long.class))) {
            return new LongFinder(composite, style, type);
        } else
            if ((type.equals(Integer.class)) || (type.equals(int.class))) {
                return new IntegerFinder(composite, style, type);
            } else
                if ((type.equals(Boolean.class))
                        || (type.equals(boolean.class))) {
                    return new BooleanFinder(composite, style, type);
                } else
                    if (type.equals(String.class)) {
                        return new StringFinder(composite, style, type);
                    } else
                        if (type.getSuperclass().equals(AbstractEntity.class)) {
                            return new RelatedObjectFinder(composite, style,
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
    public AbstractFinder(Composite composite, int style, Type type) {
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
