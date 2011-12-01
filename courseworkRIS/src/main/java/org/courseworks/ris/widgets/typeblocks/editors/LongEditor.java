package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * Класс для поиска больших числовых значений
 */
public class LongEditor extends AbstractFieldEditor {

    /** Поле ввода значений */
    protected Text numericText;

    /**
     * Конструктор
     * 
     * @param aComposite Композит
     * @param aStyle Стиль
     * @param aType Тип
     */
    public LongEditor(Composite aComposite, int aStyle, Type aType) {
        super(aComposite, aStyle, aType);

        GridLayout layout = new GridLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        super.setLayout(layout);

        numericText = new Text(this, SWT.SINGLE);
        numericText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

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
    public Object getValue() {
        return Long.valueOf(numericText.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object aValue) {
        if (!(aValue instanceof Long)) {
            throw new IllegalArgumentException("value must be Long");
        }

        Long value = (Long) aValue;
        numericText.setText(String.valueOf(value));
    }
}