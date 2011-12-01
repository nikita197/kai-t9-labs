package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
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

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public BooleanEditor(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        combo = new Combo(this, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

        combo.add(SC.TRUE);
        combo.add(SC.FALSE);

        combo.select(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getValue() {
        if (combo.getSelectionIndex() == 0) {
            return new Boolean(true);
        }
        return new Boolean(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object aValue) {
        if (!(aValue instanceof Boolean)) {
            throw new IllegalArgumentException("value must be Boolean");
        }

        Boolean value = (Boolean) aValue;
        if (value) {
            combo.select(0);
        } else {
            combo.select(1);
        }
    }
}
