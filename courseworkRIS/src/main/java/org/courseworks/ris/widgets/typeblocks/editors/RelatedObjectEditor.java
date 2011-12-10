package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.main.SC;
import org.courseworks.ris.mappings.AbstractEntity;
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

    // private AbstractEntity _editItem;
    private List<AbstractEntity> _possibleValues;

    public RelatedObjectEditor(Composite composite, int style, Field field,
            boolean nullable) {
        super(composite, style, field, nullable);

        _possibleValues = new ArrayList<AbstractEntity>();

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        text = new Combo(this, SWT.READ_ONLY);
        text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        int index = text.getSelectionIndex();
        if (nullable) {
            index--;
        }

        if (index < 0) {
            return null;
        }

        return _possibleValues.get(index);
    }

    /**
     * Передаем на заполнение объект
     * 
     * @param aValue Массив из 2 элементов: <массив всевозможных
     *            значений>;<индекс выбранного элемента>
     */
    @Override
    public void setValue(Object aValue) {
        if (aValue == null) {
            text.select(0);
            return;
        }

        int index = _possibleValues.indexOf(aValue);
        if (nullable) {
            index++;
        }
        text.select(index);
    }

    @Override
    public void setEditingItem(AbstractEntity item) {
        // _editItem = item;
        text.removeAll();
        _possibleValues.clear();

        if (nullable) {
            text.add(SC.NULL_STRING);
        }

        // DbTable relTable = item.getTable().getRelatedTable(_field);
        for (AbstractEntity entity : item.getAllowedLinkedItems(_field)) {
            text.add(entity.toString());
            _possibleValues.add(entity);
        }
    }
}
