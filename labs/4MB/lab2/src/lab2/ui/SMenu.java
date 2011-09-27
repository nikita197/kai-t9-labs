package lab2.ui;

import java.io.IOException;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class SMenu {

	private AbstractIOLayer _ioLayer;

	private Label _currentFolder;

	private List _fileListText;

	private MenuItem _opMItem1;

	private MenuItem _opMItem2;

	public SMenu() {
		_ioLayer = new GIOLayer();
	}

	public void start() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 0;
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

		_opMItem1 = new MenuItem(opDMenu, SWT.PUSH);
		_opMItem1.setText("&Rename file");

		new MenuItem(opDMenu, SWT.SEPARATOR);

		_opMItem2 = new MenuItem(opDMenu, SWT.PUSH);
		_opMItem2.setText("&Remove file");

		// Current folder
		_currentFolder = new Label(composite, SWT.NONE);
		_currentFolder.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		// Disabling menuitems
		changeMenuItemsEnable(false);

		// Separator
		new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// List of files
		_fileListText = new List(composite, SWT.READ_ONLY);
		_fileListText
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		_currentFolder.setBackground(_fileListText.getBackground());

		// ------------------------ Listeners ----------------------------------
		_fileListText.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (_fileListText.getSelectionIndex() != 1) {
					changeMenuItemsEnable(true);
				} else {
					changeMenuItemsEnable(false);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (_fileListText.getSelectionIndex() != 1) {
					changeMenuItemsEnable(true);
				} else {
					changeMenuItemsEnable(false);
				}

			}
		});

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
				InputDialog dlg = new InputDialog(composite.getShell(),
						"Enter new directory", null, null, null);
				if (dlg.open() == Window.OK) {
					try {
						_ioLayer.cd(dlg.getValue());
						_currentFolder.setText(_ioLayer.getCurrentDirectory()
								.getAbsolutePath());
						_fileListText.removeAll();
						changeMenuItemsEnable(false);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		_opMItem1.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// RenameSelectedFile
				InputDialog dlg = new InputDialog(composite.getShell(),
						"Enter new file name", null, null, null);
				if (dlg.open() == Window.OK) {
					try {
						_ioLayer.rename(
								_fileListText.getItem(
										_fileListText.getSelectionIndex())
										.substring(5), dlg.getValue());
						_fileListText.setItem(
								_fileListText.getSelectionIndex(),
								_fileListText.getItem(
										_fileListText.getSelectionIndex())
										.substring(0, 5)
										+ dlg.getValue());
					} catch (IllegalArgumentException e) {
						MessageBox msgBox = new MessageBox(Display.getDefault()
								.getActiveShell(), SWT.OK);
						msgBox.setMessage("File name is incorrect");
						msgBox.open();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});

		_opMItem2.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// DeleteSelectedFile
				try {
					_ioLayer.rm(_fileListText.getItem(
							_fileListText.getSelectionIndex()).substring(5));
					_fileListText.removeAll();
					changeMenuItemsEnable(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		fMItem1.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				// exit
				System.exit(0);
			}
		});

	}

	private void changeMenuItemsEnable(boolean value) {
		_opMItem1.setEnabled(value);
		_opMItem2.setEnabled(value);
	}
}
