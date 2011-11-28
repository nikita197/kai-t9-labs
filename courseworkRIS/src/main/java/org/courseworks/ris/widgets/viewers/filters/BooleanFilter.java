package org.courseworks.ris.widgets.viewers.filters;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Фильтр двоичных значений
 */
public class BooleanFilter extends AbstractFilter {

    /** Список значений */
    protected Combo combo;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public BooleanFilter(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        label.setText(SC.FILTER_HEADER);

        combo = new Combo(container, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        combo.add(SC.TRUE);
        combo.add(SC.FALSE);

        combo.select(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAgreement(Object aValue) {
        if (!(aValue instanceof Boolean)) {
            throw new IllegalArgumentException("Argument must be Boolean");
        }

        Boolean value = (Boolean) aValue;
        return ((combo.getSelectionIndex() == 0) && value);
    }
}
