package org.kai.CMV.lab4.widgets.typeblocks.filters;

import java.lang.reflect.Type;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.kai.CMV.lab4.main.SC;

public class DateFilter extends AbstractFilter {

	private static final String B = ">";
	private static final String S = "<";
	private static final String E = "=";

	/** Список значений */
	protected Combo combo;
	private Calendar _currentTime;
	private Text _timeText;
	private Button _editButton;
	private DateTime _calendar;
	private DateTime _time;

	public DateFilter(Composite aComposite, int aStyle, Type aType) {
		super(aComposite, aStyle, aType);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 4;
		super.setLayout(layout);

		Label label = new Label(this, SWT.NONE);
		label.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 4, 1));
		label.setText(SC.FILTER_HEADER);

		Label xLabel = new Label(this, SWT.NONE);
		xLabel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));
		xLabel.setText(SC.VAR_VALUE);

		combo = new Combo(this, SWT.READ_ONLY);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false));

		combo.add(E);
		combo.add(B);
		combo.add(S);
		combo.select(0);

		_timeText = new Text(this, SWT.READ_ONLY | SWT.BORDER);
		_timeText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		// _editButton = new Button(this, SWT.PUSH);
		// _editButton.setText("E");
		// _editButton
		// .setLayoutData(new GridData(SWT.LEFT, SWT.TOP, false, false));

		_timeText.addListener(SWT.MouseUp, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (createDateTimeShell()) {
					_timeText.setText(AbstractFilter.formateDate(_currentTime));
				}
			}

		});
	}

	private boolean createDateTimeShell() {
		final boolean[] result = new boolean[] { false };

		final Shell shell = new Shell(this.getShell());
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		shell.setLayout(gridLayout);

		_time = new DateTime(shell, SWT.TIME);
		_time.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		if (_currentTime != null) {
			_time.setTime(_currentTime.get(Calendar.HOUR),
					_currentTime.get(Calendar.MINUTE),
					_currentTime.get(Calendar.SECOND));
		}

		_calendar = new DateTime(shell, SWT.CALENDAR);
		_calendar.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));
		if (_currentTime != null) {
			_calendar.setDate(_currentTime.get(Calendar.YEAR),
					_currentTime.get(Calendar.MONTH),
					_currentTime.get(Calendar.DAY_OF_MONTH));
		}

		Button okButton = new Button(shell, SWT.PUSH);
		okButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
		okButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (_currentTime == null) {
					_currentTime = Calendar.getInstance();
				}
				_currentTime.set(_calendar.getYear(), _calendar.getMonth(),
						_calendar.getDay(), _time.getHours(),
						_time.getMinutes(), _time.getSeconds());
				_currentTime.set(Calendar.MILLISECOND, 0);
				result[0] = true;
				shell.close();
			}

		});
		okButton.setText("OK");

		Button cancelButton = new Button(shell, SWT.PUSH);
		cancelButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false));
		cancelButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				shell.close();
			}

		});
		cancelButton.setText("Cancel");

		shell.pack();
		shell.open();

		Shell parentShell = this.getShell();
		Display display = shell.getDisplay();
		while ((!parentShell.isDisposed()) && (!shell.isDisposed())) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return result[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean checkAgreement(Object aValue) {
		if (!(aValue instanceof Calendar)) {
			throw new IllegalArgumentException("Argument must be Calendar");
		}

		if (_currentTime == null) {
			return true;
		}

		Calendar value = (Calendar) aValue;
		if (E.equals(combo.getText())) {
			return (!value.after(_currentTime) && !value.before(_currentTime));
		} else if (B.equals(combo.getText())) {
			return value.after(_currentTime);
		} else if (S.equals(combo.getText())) {
			return value.before(_currentTime);
		}

		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		combo.setEnabled(enabled);
	}
}
