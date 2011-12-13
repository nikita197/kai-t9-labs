package org.kai.CMV.lab4.main;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.kai.CMV.lab4.gui.ProgWin;

public class Application {
	private static Shell _shell;

	public static void main(String[] args) {
		try {
			initGUI();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private static void initGUI() throws IllegalArgumentException,
			IllegalAccessException {
		final Point shellSize = new Point(1000, 600);
		Display display = new Display();

		_shell = new Shell(display);
		_shell.setLayout(new GridLayout());
		_shell.setText(SC.APP_HEADER);

		new ProgWin(_shell);

		_shell.setSize(shellSize);
		_shell.open();

		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}
}
