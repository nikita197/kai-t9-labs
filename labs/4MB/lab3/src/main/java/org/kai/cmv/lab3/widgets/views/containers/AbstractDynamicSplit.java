package org.kai.cmv.lab3.widgets.views.containers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public abstract class AbstractDynamicSplit extends Composite {
	private boolean _maximized = true;
	public static int MINIMIZED = 1, MAXIMIZED = 2;
	private Button _button;
	private int _type;
	private Composite _mainComposite;

	public AbstractDynamicSplit(Composite parent, int style, int type) {
		super(parent, SWT.BORDER);
		_type = type;
		switch (_type) {
		case SWT.TOP:
			setLayout(new GridLayout(1, false));
			_mainComposite = new Composite(this, SWT.NONE);
			initDynamicSplitButton();
			break;
		case SWT.LEFT:
			setLayout(new GridLayout(2, false));
			_mainComposite = new Composite(this, SWT.NONE);
			initDynamicSplitButton();
			break;
		case SWT.BOTTOM:
			setLayout(new GridLayout(1, false));
			initDynamicSplitButton();
			_mainComposite = new Composite(this, SWT.NONE);
			break;
		case SWT.RIGHT:
			setLayout(new GridLayout(2, false));
			initDynamicSplitButton();
			_mainComposite = new Composite(this, SWT.NONE);
			break;
		}
		((GridLayout) getLayout()).marginHeight = -1;
		((GridLayout) getLayout()).marginWidth = -1;
		((GridLayout) getLayout()).verticalSpacing = 1;
		((GridLayout) getLayout()).horizontalSpacing = 1;
		_mainComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true));
		initContent();

		_button.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				changeSplit();
			}
		});

		if (MINIMIZED == style) {
			changeSplit();
		}
		getParent().pack();
	}

	private void changeSplit() {
		_maximized = !_maximized;
		((GridData) _mainComposite.getLayoutData()).exclude = !_maximized;
		_mainComposite.setVisible(_maximized);

		if (_type == SWT.LEFT || _type == SWT.RIGHT) {
			if (_button.getText().equals(">")) {
				_button.setText("<");
			} else {
				_button.setText(">");
			}
		}

		GridData data = (GridData) _button.getParent().getLayoutData();
		if (_type == SWT.TOP || _type == SWT.BOTTOM) {
			if (data.verticalAlignment == SWT.TOP) {
				data.verticalAlignment = SWT.BOTTOM;
			} else {
				data.verticalAlignment = SWT.TOP;
			}
		} else {
			if (data.horizontalAlignment == SWT.LEFT) {
				data.horizontalAlignment = SWT.RIGHT;
			} else {
				data.horizontalAlignment = SWT.LEFT;
			}
		}
		_button.getParent().setLayoutData(data);
		getParent().layout();
	}

	private void initDynamicSplitButton() {
		Composite dynamicSplitButtonComposite = new Composite(this, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		dynamicSplitButtonComposite.setLayout(layout);

		GridData data;
		switch (_type) {
		case SWT.TOP:
			data = new GridData(SWT.FILL, SWT.BOTTOM, true, true);
			break;
		case SWT.LEFT:
			data = new GridData(SWT.LEFT, SWT.FILL, true, true);
			break;
		case SWT.BOTTOM:
			data = new GridData(SWT.FILL, SWT.TOP, true, true);
			break;
		case SWT.RIGHT:
			data = new GridData(SWT.RIGHT, SWT.FILL, true, true);
			break;
		default:
			data = new GridData();
		}
		dynamicSplitButtonComposite.setLayoutData(data);

		_button = new Button(dynamicSplitButtonComposite, SWT.NONE);
		GridData buttonData = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		if (_type == SWT.TOP || _type == SWT.BOTTOM) {
			_button.setText("-");
			buttonData.heightHint = 15;
			buttonData.widthHint = 30;
		} else if (_type == SWT.LEFT || _type == SWT.RIGHT) {
			if (_maximized && _type == SWT.LEFT || !_maximized
					&& _type == SWT.RIGHT) {
				_button.setText("<");
			} else {
				_button.setText(">");
			}
			buttonData.heightHint = 30;
			buttonData.widthHint = 15;
		}
		_button.setLayoutData(buttonData);
	}

	public abstract void initContent();

	public Composite getComp() {
		return _mainComposite;
	}
}
