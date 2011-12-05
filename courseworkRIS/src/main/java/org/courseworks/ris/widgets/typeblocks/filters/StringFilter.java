package org.courseworks.ris.widgets.typeblocks.filters;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Фильтр символьных значений
 */
public class StringFilter extends AbstractFilter {

    /** Текстовое поле */
    protected Text text;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public StringFilter(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        Label label = new Label(this, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
        label.setText(SC.FILTER_HEADER);

        text = new Text(this, SWT.SINGLE | SWT.BORDER);
        text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAgreement(Object aValue) {
        if (!(aValue instanceof String)) {
            throw new IllegalArgumentException("Argument must be String");
        }

        String value = (String) aValue;
        return value.contains(text.getText());
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        text.setEnabled(enabled);
    }

}
