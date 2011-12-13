package org.kai.CMV.lab4.main;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.widgets.Shell;

/**
 * 
 */
public class SplashScreen {

	private final Display _display;

	private final Shell splash;

	private final Image _image;

	private final ProgressBar _bar;

	private Runnable _runnable;

	public SplashScreen() {
		_display = new Display();
		splash = new Shell(SWT.ON_TOP | SWT.NO_TRIM);
		_image = new Image(null,
				Application.class.getResourceAsStream(Application.IMAGE));

		_bar = new ProgressBar(splash, SWT.NONE);
		_bar.setMaximum(10);

		Label label = new Label(splash, SWT.NONE);
		label.setImage(_image);

		FormLayout layout = new FormLayout();
		splash.setLayout(layout);

		FormData labelData = new FormData();
		labelData.right = new FormAttachment(100, 0);
		labelData.bottom = new FormAttachment(100, 0);
		label.setLayoutData(labelData);

		FormData progressData = new FormData();
		progressData.left = new FormAttachment(0, 5);
		progressData.right = new FormAttachment(100, -5);
		progressData.bottom = new FormAttachment(100, -5);

		_bar.setLayoutData(progressData);
		splash.pack();
		Rectangle splashRect = splash.getBounds();
		Rectangle displayRect = _display.getBounds();
		int x = (displayRect.width - splashRect.width) / 2;
		int y = (displayRect.height - splashRect.height) / 2;
		splash.setLocation(x, y);
	}

	public void setRunnable(Runnable runnable) {
		_runnable = runnable;
	}

	public void open() {
		splash.open();

		_display.asyncExec(_runnable);

		while (_bar.getSelection() != 10) {
			if (!_display.readAndDispatch())
				_display.sleep();
		}
		_display.dispose();
	}

	public void setSelection(int index) {
		_bar.setSelection(index);
	}

}
