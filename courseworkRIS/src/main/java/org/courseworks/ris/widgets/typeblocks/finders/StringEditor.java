package org.courseworks.ris.widgets.typeblocks.finders;

import java.lang.reflect.Type;

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

	/**
	 * Конструктор
	 * 
	 * @param aComposite
	 *            Композит
	 * @param aStyle
	 *            Стиль
	 * @param aType
	 *            Тип
	 */
	public StringEditor(Composite aComposite, int aStyle, Type aType) {
		super(aComposite, aStyle, aType);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		super.setLayout(layout);

		text = new Text(this, SWT.SINGLE);
		text.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getSearchValue() {
		return text.getText();
	}
}
