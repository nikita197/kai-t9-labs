package lab2.ui;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class HMenu {
	private AbstractIOLayer _ioLayer;

	private List _elementsList;

	public HMenu() {
		_ioLayer = new GIOLayer();
	}

	public void start() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
		layout.numColumns = 3;
		shell.setLayout(layout);

		createContent(shell);

		shell.setText("Non-standard Control menu");
		shell.setSize(500, 500);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createContent(Shell shell) {
		// TODO Auto-generated method stub
		_elementsList = new List(shell, SWT.READ_ONLY);
		_elementsList.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true,
				true));

		Button lsButton = new Button(shell, SWT.READ_ONLY);
		// lsButton
	}
}
