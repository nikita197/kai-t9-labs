package lab2.ui;

import java.io.IOException;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class SMenu {

	private AbstractIOLayer _ioLayer;

	private Label _currentFolder;

	private List _fileListText;

	public SMenu() {
		_ioLayer = new GIOLayer();
	}

	public void start() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		layout.numColumns = 1;
		shell.setLayout(layout);

		createContent(shell);

		shell.setText("Control menu");
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
		// Main menu
		Menu menu = new Menu(composite.getShell(), SWT.BAR);
		composite.getShell().setMenuBar(menu);

		// File cascade item
		MenuItem fMItem = new MenuItem(menu, SWT.CASCADE);
		fMItem.setText("&File");

		// File dropdown Menu
		Menu fDMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
		fMItem.setMenu(fDMenu);

		MenuItem fMItem1 = new MenuItem(fDMenu, SWT.PUSH);
		fMItem1.setText("&Exit");

		// Navigation cascade item
		MenuItem navMItem = new MenuItem(menu, SWT.CASCADE);
		navMItem.setText("&Navigation");

		// Navigation dropdown Menu
		Menu navDMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
		navMItem.setMenu(navDMenu);

		MenuItem navMItem1 = new MenuItem(navDMenu, SWT.PUSH);
		navMItem1.setText("&List files");

		new MenuItem(navDMenu, SWT.SEPARATOR);

		MenuItem navMItem2 = new MenuItem(navDMenu, SWT.PUSH);
		navMItem2.setText("&Change directory");

		// Operations cascade item
		MenuItem opMItem = new MenuItem(menu, SWT.CASCADE);
		opMItem.setText("&Operations");

		// Operations dropdown Menu
		Menu opDMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
		opMItem.setMenu(opDMenu);

		MenuItem opMItem1 = new MenuItem(opDMenu, SWT.PUSH);
		opMItem1.setText("&Rename file");

		new MenuItem(opDMenu, SWT.SEPARATOR);

		MenuItem opMItem2 = new MenuItem(opDMenu, SWT.PUSH);
		opMItem2.setText("&Remove file");

		// Current folder
		_currentFolder = new Label(composite, SWT.NONE);
		_currentFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		// Separator
		new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// List of files
		_fileListText = new List(composite, SWT.READ_ONLY | SWT.MULTI);
		_fileListText
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_currentFolder.setBackground(_fileListText.getBackground());

		// ------------------------ Listeners ----------------------------------

		navMItem1.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// List files
				_fileListText.removeAll();
				String sep = System.getProperty("line.separator");
				for (String f : _ioLayer.ls().split(sep)) {
					_fileListText.add(f);
				}
			}
		});

		navMItem2.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// Change directory
				// // _fileListText.removeAll();

				/*
				 * InputDialog dlg = new
				 * InputDialog(Display.getCurrent().getActiveShell(), "",
				 * "Enter 5-8 characters", label.getText(), new
				 * LengthValidator()); if (dlg.open() == Window.OK) { // User
				 * clicked OK; update the label with the input
				 * label.setText(dlg.getValue()); }
				 */

				/*
				 * composite.getShell().setEnabled(false); Shell dialog = new
				 * Shell(composite.getShell());
				 * dialog.setText("Enter new directory"); new Label(dialog,
				 * SWT.NONE); dialog.setSize(200, 200); dialog.open();
				 */

				/*
				 * MessageBox msgBox = new MessageBox(Display.getDefault()
				 * .getActiveShell(), SWT);
				 * msgBox.setMessage("Folder is not empty. Remove recursively?"
				 * ); msgBox.setText("Removing " + fileName); if (msgBox.open()
				 * == SWT.OK) { _ioLayer.cd(fileName);
				 * _currentFolder.setText(_ioLayer.getCurrentDirectory()
				 * .getAbsolutePath()); }
				 */
			}
		});

		opMItem1.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// RenameSelectedFile
			}
		});

		opMItem2.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// DeleteSelectedFile
				try {
					_ioLayer.rm(_fileListText.getItem(_fileListText
							.getSelectionIndex()));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	// /private

}
