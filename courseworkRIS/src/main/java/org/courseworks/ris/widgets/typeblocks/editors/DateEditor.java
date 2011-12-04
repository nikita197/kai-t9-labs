package org.courseworks.ris.widgets.typeblocks.editors;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DateTime;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DateEditor extends AbstractFieldEditor {

	private Calendar _currentTime;
	private Text _timeText;
	private Button _editButton;
	private Button _clearButton;
	private DateTime _calendar;
	private DateTime _time;

	public DateEditor(Composite composite, int style, Field field,
			boolean nullable) {
		super(composite, style, field, nullable);

		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.numColumns = 3;
		super.setLayout(layout);

		_timeText = new Text(this, SWT.READ_ONLY | SWT.BORDER);
		_timeText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, true));

		_editButton = new Button(this, SWT.PUSH);
		_editButton.setText("E");
		_editButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false));

		_clearButton = new Button(this, SWT.PUSH);
		_clearButton.setText("C");
		_clearButton.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false));

		_clearButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				_timeText.setText("");
				_currentTime = null;
			}

		});
		_editButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (createDateTimeShell()) {
					_timeText.setText(AbstractEntity.formateDate(_currentTime));
				}
			}

		});
	}

	private boolean createDateTimeShell() {
		final boolean[] result = new boolean[] { false };

		final Shell shell = new Shell(this.getShell());
		shell.setLayout(new RowLayout());

		_calendar = new DateTime(shell, SWT.CALENDAR);
		if (_currentTime != null) {
			_calendar.setDate(_currentTime.get(Calendar.YEAR),
					_currentTime.get(Calendar.MONTH),
					_currentTime.get(Calendar.DAY_OF_MONTH));
		}

		_time = new DateTime(shell, SWT.TIME);
		if (_currentTime != null) {
			_time.setTime(_currentTime.get(Calendar.HOUR),
					_currentTime.get(Calendar.MINUTE),
					_currentTime.get(Calendar.SECOND));
		}

		Button okButton = new Button(shell, SWT.PUSH);
		okButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				if (_currentTime == null) {
					_currentTime = Calendar.getInstance();
				}
				_currentTime.set(_calendar.getYear(), _calendar.getMonth(),
						_calendar.getDay(), _time.getHours(),
						_time.getMinutes(), _time.getSeconds());
				result[0] = true;
				shell.close();
			}

		});
		okButton.setText("OK");

		Button cancelButton = new Button(shell, SWT.PUSH);
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
	public Object getValue() {
		return _currentTime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(Object aValue) {
		if (!(aValue instanceof Calendar)) {
			throw new IllegalArgumentException("aValue must be Calendar");
		}

		_currentTime = (Calendar) aValue;
		_timeText.setText(AbstractEntity.formateDate(_currentTime));
	}

	@Override
	public void setEditingItem(AbstractEntity item) {
	}
}
