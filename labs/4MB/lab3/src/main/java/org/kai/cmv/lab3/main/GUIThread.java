package org.kai.cmv.lab3.main;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.kai.cmv.lab3.data.JMFdataProvider;
import org.kai.cmv.lab3.widgets.views.containers.MainMenu;
import org.kai.cmv.lab3.widgets.views.containers.Screen;

public class GUIThread implements Runnable {
	private static MainMenu _mainMenu;
	private static Screen _screen;
	private static JMFdataProvider _dataProvider;
	private static Shell _shell;
	private static SimpleDateFormat _sdf = new SimpleDateFormat("hh:mm:ss");

	@Override
	public void run() {
		try {
			initDataProvider();
			initGUI();
			_dataProvider.rewriteProperties("filesList");
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void initDataProvider() {
		_dataProvider = new JMFdataProvider();
		_dataProvider.fillGenList();
	}

	private static void initGUI() throws IllegalArgumentException,
			IllegalAccessException {
		Display display = new Display();
		_shell = new Shell(display);

		GridLayout shellLayout = new GridLayout(2, false);
		shellLayout.horizontalSpacing = -1;
		_shell.setLayout(shellLayout);

		_mainMenu = new MainMenu(_shell, SWT.BORDER);
		GridData mmData = new GridData(SWT.FILL, SWT.FILL, true, true);
		mmData.heightHint = 600;
		mmData.widthHint = 100;
		_mainMenu.setLayoutData(mmData);

		_screen = new Screen(_shell, SWT.BORDER);
		GridData screenData = new GridData(SWT.FILL, SWT.FILL, true, true);
		screenData.widthHint = 900;
		screenData.heightHint = 600;
		screenData.exclude = true;
		_screen.setLayoutData(screenData);

		_mainMenu.addListeners();

		_shell.pack();
		_shell.open();
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		_screen.getScMonitor().disposePlayer();
	}

	public static Screen getScreen() {
		return _screen;
	}

	public static JMFdataProvider getDataProvider() {
		return _dataProvider;
	}

	public static Shell getShell() {
		return _shell;
	}

	public void setWatch() {
		if (!GUIThread.getShell().isDisposed()) {
			_mainMenu.getWatch().setTime(_sdf.format(new Date()));
		}
	}
}
