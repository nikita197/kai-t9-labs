package org.kai.cmv.lab3.widgets.views.containers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.kai.cmv.lab3.helpers.Helper;
import org.kai.cmv.lab3.main.GUIThread;

public class MainMenu extends Composite {
	private Helper _helper;
	private Button _sheduleButton, _favoritesButton, _monutorButton;
	private Watch _watch;
	private int _helpMode;

	public MainMenu(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(1, false));

		initContent();
	}

	private void initContent() {
		_watch = new Watch(this, SWT.BORDER);
		GridData clocksData = new GridData(SWT.CENTER, SWT.TOP, true, true);
		clocksData.verticalIndent = -6;
		clocksData.widthHint = 52;
		_watch.setLayoutData(clocksData);

		GridData gridData = new GridData(SWT.FILL, SWT.TOP, false, true);
		gridData.heightHint = 50;
		_sheduleButton = new Button(this, SWT.TOGGLE);
		_sheduleButton.setText("Расписание");
		_sheduleButton.setLayoutData(gridData);
		_favoritesButton = new Button(this, SWT.TOGGLE);
		_favoritesButton.setText("Избранное");
		_favoritesButton.setLayoutData(gridData);
		_monutorButton = new Button(this, SWT.TOGGLE);
		_monutorButton.setText("Монитор");
		_monutorButton.setLayoutData(gridData);
		_sheduleButton.setFocus();

		_helper = new Helper(this, SWT.NONE, this);
		_helper.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false, false));
		((GridData) _helper.getLayoutData()).verticalIndent = -11;
		((GridData) _helper.getLayoutData()).horizontalIndent = -5;
		setHelpMode(Helper.MAIN_HELP);
	}

	public void addListeners() {

		_sheduleButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (_sheduleButton.getSelection()) {
					_favoritesButton.setSelection(false);
					_monutorButton.setSelection(false);
				}
				setScreenVisible(_sheduleButton.getSelection(), Screen.SHEDULE);
			}
		});

		_favoritesButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (_favoritesButton.getSelection()) {
					_sheduleButton.setSelection(false);
					_monutorButton.setSelection(false);
				}
				setScreenVisible(_favoritesButton.getSelection(),
						Screen.FAVORITES);
			}
		});

		_monutorButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (_monutorButton.getSelection()) {
					_favoritesButton.setSelection(false);
					_sheduleButton.setSelection(false);
				}
				setScreenVisible(_monutorButton.getSelection(), Screen.MONITOR);
			}
		});
	}

	private void setScreenVisible(boolean visible, int visibleComponent) {
		final Screen screen = GUIThread.getScreen();
		final GridData screenData = (GridData) screen.getLayoutData();
		if (visible) {
			if (((Object) visibleComponent) != null) {
				screenData.exclude = false;
				screen.show(visibleComponent);
				getShell().layout();
				getShell().pack();
			} else {
				throw new NullPointerException();
			}
		} else {
			screenData.exclude = true;
			getShell().layout();
			getShell().pack();
			if (visibleComponent == Screen.MONITOR)
				screen.getScMonitor().stopPlayer();
			setHelpMode(Helper.MAIN_HELP);
		}
	}

	public Watch getWatch() {
		return _watch;
	}

	public int getHelpMode() {
		return _helpMode;
	}

	public void setHelpMode(int helpMode) {
		_helpMode = helpMode;
	}
}
