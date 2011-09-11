package org.kai.cmv.lab1;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.kai.cmv.lab1.widgets.NumbersView;

public class Program {

	public static void main(String[] args) {
		final Point shellSize = new Point(600, 600);
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		System.out.println("qqwe1");
		new NumbersView(shell, SWT.NONE);

		shell.setSize(shellSize);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

}
