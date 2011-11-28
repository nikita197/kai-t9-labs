package org.courseworks.ris.widgets.viewers.filters;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Фильтр связанных объектов
 */
public class RelatedObjectFilter extends AbstractFilter {

    /** Текстовое поле */
    protected Text text;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public RelatedObjectFilter(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        label.setText(SC.FILTER_HEADER);

        text = new Text(container, SWT.SINGLE);
        text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAgreement(Object aValue) {
        if (!(aValue instanceof AbstractEntity)) {
            throw new IllegalArgumentException(
                    "Argument must be AbstractEntity");
        }

        AbstractEntity value = (AbstractEntity) aValue;
        return value.getName().contains(text.getText());
    }
}
