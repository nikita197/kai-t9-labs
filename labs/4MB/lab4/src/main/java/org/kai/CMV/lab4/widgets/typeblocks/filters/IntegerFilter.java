package org.kai.CMV.lab4.widgets.typeblocks.filters;

import java.lang.reflect.Type;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.kai.CMV.lab4.main.SC;

/**
 * Фильтр числовых значений
 */
public class IntegerFilter extends AbstractFilter {

	private static final String B = ">";

	private static final String S = "<";

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
	 * @param aComposite
	 *            Композит
	 * @param aStyle
	 *            Стиль
	 * @param aType
	 *            Тип
	 */
	public IntegerFilter(Composite aComposite, int aStyle, Type aType) {
		super(aComposite, aStyle, aType);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 3;
		super.setLayout(layout);

		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 3, 1));
		label.setText(SC.FILTER_HEADER);

		Label xLabel = new Label(this, SWT.NONE);
		xLabel.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, true));
		xLabel.setText(SC.VAR_VALUE);

		combo = new Combo(this, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, true));

		combo.add(E);
		combo.add(B);
		combo.add(S);
		combo.add(NE);
		combo.add(BE);
		combo.add(SE);

		combo.select(0);

		numericText = new Text(this, SWT.SINGLE | SWT.BORDER);
		numericText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));
		numericText.setText("0");

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
		if (!(aValue instanceof Integer) && !(aValue.getClass() == int.class)) {
			System.out.println(aValue.getClass());
			throw new IllegalArgumentException("Argument must be Integer");
		}

		Integer value = (Integer) aValue;
		Integer filterValue = Integer.valueOf(numericText.getText());
		if (B.equals(combo.getText())) {
			return (value > filterValue);
		} else if (S.equals(combo.getText())) {
			return (value < filterValue);
		} else if (BE.equals(combo.getText())) {
			return (value >= filterValue);
		} else if (SE.equals(combo.getText())) {
			return (value <= filterValue);
		} else if (E.equals(combo.getText())) {
			return (value.equals(filterValue));
		} else if (NE.equals(combo.getText())) {
			return (!value.equals(filterValue));
		} else {
			return false;
		}
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		combo.setEnabled(enabled);
		numericText.setEnabled(enabled);
	}
}
