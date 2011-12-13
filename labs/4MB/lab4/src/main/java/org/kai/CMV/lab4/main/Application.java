package org.kai.CMV.lab4.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.kai.CMV.lab4.gui.ProgWin;

public class Application {
	private static Shell _shell;
	private static long beginTime = -1, endTime = -1;

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

	public static void setBeginTime(long beginTime) {
		Application.beginTime = beginTime;
	}

	public static void setEndTime(long endTime) {
		Application.endTime = endTime;
	}

	public static void calcElapsedTime() {
		try {
			if (beginTime != -1 && endTime != -1) {
				String elapsedTimeInString = String
						.valueOf(endTime - beginTime);
				byte[] elapsedTime = (elapsedTimeInString + "\n").getBytes();
				File outFile = new File("src/main/resources/elapsedTime");
				FileOutputStream fos = new FileOutputStream(outFile, true);
				fos.write(elapsedTime);
				fos.close();
				showElapsedTime(elapsedTimeInString);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void showElapsedTime(String time) {
		String timeInSec = String.valueOf(Integer.parseInt(time) / 1000);
		_shell.setEnabled(false);
		final Shell miniWin = new Shell(_shell, SWT.APPLICATION_MODAL);
		miniWin.setLayout(new GridLayout(1, false));
		miniWin.addMouseListener(new MouseListener() {

			@Override
			public void mouseUp(MouseEvent e) {
			}

			@Override
			public void mouseDown(MouseEvent e) {
				miniWin.close();
				_shell.setEnabled(true);
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
			}
		});

		Text txt = new Text(miniWin, SWT.NONE);
		txt.setText("Затраченное время: " + timeInSec + " секунд");
		txt.setEnabled(false);
		miniWin.pack();

		int miniWinLocationX = _shell.getSize().x / 2 - miniWin.getSize().x / 2;
		int miniWinLocationY = _shell.getSize().y / 2 - miniWin.getSize().y / 2;
		miniWin.setLocation(miniWinLocationX, miniWinLocationY);

		miniWin.open();
	}
}
