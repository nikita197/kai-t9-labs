package org.courseworks.ris.widgets.viewers.filters;

import java.lang.reflect.Type;

import org.courseworks.ris.main.SC;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Фильтр числовых значений
 */
public class IntegerFilter extends AbstractFilter {

    private static final String BE = ">=";

    private static final String SE = "<=";

    private static final String E = "=";

    private static final String NE = "!=";

    /** Список возможных отношений */
    protected Combo combo;

    /** Значение */
    private final Text numericText;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public IntegerFilter(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        container.setLayout(layout);

        Label label = new Label(container, SWT.NONE);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 3, 1));
        label.setText(SC.FILTER_HEADER);

        Label xLabel = new Label(container, SWT.NONE);
        xLabel.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));
        xLabel.setText(SC.VAR_VALUE);

        combo = new Combo(container, SWT.READ_ONLY);
        combo.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, true));

        combo.add(BE);
        combo.add(SE);
        combo.add(E);
        combo.add(NE);

        combo.select(0);

        numericText = new Text(container, SWT.SINGLE);
        numericText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        numericText.addListener(SWT.Verify, new Listener() {

            @Override
            public void handleEvent(Event e) {
                String string = e.text;
                char[] chars = new char[string.length()];
                string.getChars(0, chars.length, chars, 0);
                for (int i = 0; i < chars.length; i++) {
                    if (!('0' <= chars[i] && chars[i] <= '9')) {
                        e.doit = false;
                        return;
                    }
                }
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean checkAgreement(Object aValue) {
        if (!(aValue instanceof Integer)) {
            throw new IllegalArgumentException("Argument must be Integer");
        }

        Integer value = (Integer) aValue;
        Integer filterValue = Integer.valueOf(numericText.getText());
        if (BE.equals(combo.getText())) {
            return (value >= filterValue);
        } else if (SE.equals(combo.getText())) {
            return (value <= filterValue);
        } else if (E.equals(combo.getText())) {
            return (value == filterValue);
        } else if (NE.equals(combo.getText())) {
            return (value != filterValue);
        } else {
            return false;
        }
    }
}
