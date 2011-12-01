package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Класс для поиска связных объектов<br>
 * На текущий момент аналогичен StringFinder
 */
public class RelatedObjectEditor extends AbstractFieldEditor {

    /** Поле ввода значений */
    protected Combo text;

    private final List<Object> _values;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public RelatedObjectEditor(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        _values = new ArrayList<Object>();

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        text = new Combo(this, SWT.NONE);
        text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        if (_values.size() != 0) {
            return _values.get(text.getSelectionIndex());
        }
        return text.getText();
    }

    /**
     * Передаем на заполнение объект
     * 
     * @param aValue Массив из 2 элементов: <массив всевозможных
     *            значений>;<индекс выбранного элемента>
     */
    @Override
    public void setValue(Object aValue) {
        Object[] value = (Object[]) aValue;
        Object[] allObjects = (Object[]) value[0];
        int selected = (Integer) value[1];

        // Уничтожаем старый редактируемый combo
        text.dispose();
        text = new Combo(this, SWT.READ_ONLY);
        text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        for (Object object : allObjects) {
            _values.add(object);
            text.add(object.toString());
        }

        text.select(selected);
    }
}
