package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Field;

import org.courseworks.ris.mappings.AbstractEntity;
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

    public LongEditor(Composite composite, int style, Field field,
            boolean nullable) {
        super(composite, style, field, nullable);

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
        if (numericText.getText() == "") {
            return null;
        }

        return Long.valueOf(numericText.getText());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setValue(Object aValue) {
        if (aValue == null) {
            numericText.setText("");
            return;
        }

        if (!(aValue instanceof Long)) {
            throw new IllegalArgumentException("value must be Long");
        }

        Long value = (Long) aValue;
        numericText.setText(String.valueOf(value));
    }

    @Override
    public void setEditingItem(AbstractEntity item) {
    }
}
