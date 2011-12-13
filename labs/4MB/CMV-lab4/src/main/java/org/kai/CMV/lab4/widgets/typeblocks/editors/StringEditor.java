package org.kai.CMV.lab4.widgets.typeblocks.editors;

import java.lang.reflect.Field;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;

/**
 * Класс для поиска символьных значений
 */
public class StringEditor extends AbstractFieldEditor {

	/** Поле ввода значений */
	protected Text text;

	public StringEditor(Composite composite, int style, Field field,
			boolean nullable) {
		super(composite, style, field, nullable);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		super.setLayout(layout);

		text = new Text(this, SWT.SINGLE | SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getValue() {
		if (text.getText().trim().equals("")) {
			return null;
		}

		return text.getText();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Object aValue) {
		if (aValue == null) {
			text.setText("");
			return;
		}

		if (!(aValue instanceof String)) {
			throw new IllegalArgumentException("value must be String");
		}

		String value = (String) aValue;
		text.setText(value);
	}

	@Override
	public void setEditingItem(AbstractEntity item) {
	}
}
