package lab2.ui;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

public class SMan {

	private static Image RENAME_IMAGE;
	private static Image RECYCLE_IMAGE;

	private AbstractIOLayer _ioLayer;

	private Label _currentFolder;

	private List _fileListText;

	public SMan() {
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

		shell.setText("Direct control");
		shell.setSize(500, 700);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createContent(final Composite composite) {
		if (RENAME_IMAGE == null) {
			RENAME_IMAGE = ImageDescriptor.createFromURL(
					SMan.class.getResource("/icons/rename.png")).createImage();
		}

		if (RECYCLE_IMAGE == null) {
			RECYCLE_IMAGE = ImageDescriptor.createFromURL(
					SMan.class.getResource("/icons/recycle.png")).createImage();
		}

		// Current folder
		_currentFolder = new Label(composite, SWT.NONE);
		GridData gd1 = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd1.horizontalSpan = 3;
		_currentFolder.setLayoutData(gd1);
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		// Separator
		Label sep = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
		GridData gd2 = new GridData(SWT.FILL, SWT.CENTER, true, false);
		gd2.horizontalSpan = 3;
		sep.setLayoutData(gd2);

		// Rename drag target
		Label renameLabel = new Label(composite, SWT.NONE);
		GridData gd3 = new GridData(SWT.LEFT, SWT.FILL, false, true);
		renameLabel.setLayoutData(gd3);
		renameLabel.setImage(RENAME_IMAGE);

		// List of files
		_fileListText = new List(composite, SWT.READ_ONLY | SWT.MULTI);
		GridData gd4 = new GridData(SWT.FILL, SWT.FILL, true, true);
		_fileListText.setLayoutData(gd4);

		// Remove drag target
		Label removeLabel = new Label(composite, SWT.NONE);
		GridData gd5 = new GridData(SWT.RIGHT, SWT.FILL, false, true);
		removeLabel.setLayoutData(gd5);
		removeLabel.setImage(RECYCLE_IMAGE);

		// Backgrounds
		_currentFolder.setBackground(_fileListText.getBackground());
		renameLabel.setBackground(_fileListText.getBackground());
		removeLabel.setBackground(_fileListText.getBackground());
	}
}
