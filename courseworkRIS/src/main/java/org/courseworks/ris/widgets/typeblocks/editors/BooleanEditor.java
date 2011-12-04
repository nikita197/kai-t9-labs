package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Field;

import org.courseworks.ris.main.SC;
import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;

/**
 * Класс для поиска бинарных значений
 */
public class BooleanEditor extends AbstractFieldEditor {

    /** Список значений */
    protected Combo combo;

    public BooleanEditor(Composite composite, int style, Field field,
            boolean nullable) {
        super(composite, style, field, nullable);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        combo = new Combo(this, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        if (nullable) {
            combo.add(SC.NULL_STRING);
        }

        combo.add(SC.TRUE);
        combo.add(SC.FALSE);

        combo.select(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        if (SC.TRUE.equals(combo.getText())) {
            return true;
        } else
            if (SC.FALSE.equals(combo.getText())) {
                return false;
            } else {
                return null;
            }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object aValue) {
        if (aValue == null) {
            combo.select(combo.indexOf(SC.NULL_STRING));
            return;
        }

        if (!(aValue instanceof Boolean)) {
            throw new IllegalArgumentException("value must be Boolean");
        }

        Boolean value = (Boolean) aValue;
        if (value) {
            combo.select(combo.indexOf(SC.TRUE));
        } else {
            combo.select(combo.indexOf(SC.FALSE));
        }
    }

    @Override
    public void setEditingItem(AbstractEntity item) {
    }
}
