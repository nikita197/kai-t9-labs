package org.courseworks.ris.widgets.viewers.finders;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

/**
 * Класс для поиска бинарных значений
 */
public class BooleanFinder extends AbstractFinder {

    /** Список значений */
    protected Combo combo;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public BooleanFinder(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        label.setText(SC.SEARCH_HEADER);

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
    public Object getSearchValue() {
        if (combo.getSelectionIndex() == 0) {
            return new Boolean(true);
        }
        return new Boolean(false);
    }
}
