package org.kai.cmv.lab3.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.TraverseEvent;
import org.eclipse.swt.events.TraverseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.kai.cmv.lab3.main.GUIThread;
import org.kai.cmv.lab3.widgets.views.containers.MainMenu;

public class Helper extends Composite {
	private Button _helpButton;
	private Shell _helperWindow;
	private MainMenu _mainMenu;
	private Text _helperText;
	public static final int MAIN_HELP = 1, SHEDULE_HELP = 2,
			FAVORITES_HELP = 3, MONITOR_HELP = 4;

	public Helper(Composite parent, int style, MainMenu mainMenu) {
		super(parent, style);
		setLayout(new GridLayout());

		_mainMenu = mainMenu;

		_helpButton = new Button(this, style);
		_helpButton.setText("?");
		_helpButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false,
				false));

		GridData gridData = (GridData) _helpButton.getLayoutData();

		gridData.widthHint = 20;
		gridData.heightHint = 20;

		gridData.verticalIndent = -5;
		gridData.horizontalIndent = -5;

		_helpButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				show(_mainMenu.getHelpMode());
			}
		});
	}

	public void show(int mode) {
		_helperWindow = new Shell(GUIThread.getShell(), SWT.APPLICATION_MODAL);
		_helperWindow.setLayout(new GridLayout(1, false));
		_helperText = new Text(_helperWindow, SWT.MULTI);
		_helperText.setEnabled(false);

		fillHelper(mode);
		_helperWindow.pack();
		_helperWindow.setLocation(
				Display.getCurrent().getCursorLocation().x - 20, Display
						.getCurrent().getCursorLocation().y - 20);

		addListener(_helperWindow, SWT.MouseUp, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				_helperWindow.close();
			}

		});

		_helperWindow.open();
	}

	private void fillHelper(int mode) {
		switch (mode) {
		case MAIN_HELP:
			_helperText
					.setText("Главное окно программы содержит кнопки открытия панелей\n"
							+ "расписания, избранного и монитора. Чтобы передать канал в\n"
							+ "автомат монитора, необходимо добавить его в расписании и\n"
							+ "передать в избранное, а затем оттуда - в автомат.");
			break;
		case SHEDULE_HELP:
			_helperText
					.setText("В окне расписания пользователь может добавить каналы в список\n"
							+ "расписания, удалить их оттуда, очистить весь список, а также\n"
							+ "передать канал в избранное.");
			break;
		case FAVORITES_HELP:
			_helperText
					.setText("В окне избранного пользователь может удалить каналы из списка,\n"
							+ "либо передать их в автомат монитора");
			break;
		case MONITOR_HELP:
			_helperText
					.setText("Окно монитора содержит экран для воспроизведения видео,\n"
							+ "скрывающиеся панели автомата (позволяет удалять каналы из списка\n"
							+ "автомата, а также воспроизводить выбранный канал двойным кликом на\n"
							+ "элементе списка) и пульта (позволяет регулировать громкость видео,\n"
							+ "ставить воспроизведение на паузу и перемещаться между каналами автомата.");
			break;
		}
	}

	private void addListener(Composite control, int type, Listener listener) {
		control.addListener(type, listener);
		for (Control child : control.getChildren()) {
			child.addListener(type, listener);
			if (child instanceof Composite) {
				addListener((Composite) child, type, listener);
			}
		}
	}
}
