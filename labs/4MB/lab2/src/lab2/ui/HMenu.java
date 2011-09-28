package lab2.ui;

import java.io.IOException;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class HMenu {
	private AbstractIOLayer _ioLayer;

	private Label _currentFolder;

	private List _filesList;

	private Tree _commandTree;

	private TreeItem _opTreeItem;

	private TreeItem _navTreeItem;

	private TreeItem _lsTreeItem;

	private TreeItem _cdTreeItem;

	private TreeItem _renameTreeItem;

	private TreeItem _rmTreeItem;

	public HMenu() {
		_ioLayer = new GIOLayer();
	}

	public void start() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 0;
		layout.horizontalSpacing = 2;
		layout.numColumns = 2;
		shell.setLayout(layout);

		createContent(shell);

		shell.setText("Non-standard control menu");
		shell.setSize(400, 600);
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	private void createContent(final Shell shell) {
		// Command tree
		_commandTree = new Tree(shell, SWT.READ_ONLY | SWT.BORDER);
		GridData gd1 = new GridData(SWT.FILL, SWT.FILL, false, false);
		gd1.verticalSpan = 2;
		_commandTree.setLayoutData(gd1);

		_opTreeItem = new TreeItem(_commandTree, SWT.NONE, 0);
		_opTreeItem.setText("Operations");
		_navTreeItem = new TreeItem(_commandTree, SWT.NONE, 0);
		_navTreeItem.setText("Navigation");
		_cdTreeItem = new TreeItem(_navTreeItem, SWT.NONE, 0);
		_cdTreeItem.setText("Change directory");
		_lsTreeItem = new TreeItem(_navTreeItem, SWT.NONE, 0);
		_lsTreeItem.setText("List files");
		_rmTreeItem = new TreeItem(_opTreeItem, SWT.NONE, 0);
		_rmTreeItem.setText("Remove file");
		_renameTreeItem = new TreeItem(_opTreeItem, SWT.NONE, 0);
		_renameTreeItem.setText("Rename file");

		_opTreeItem.setExpanded(true);
		_navTreeItem.setExpanded(true);

		// Current folder
		_currentFolder = new Label(shell, SWT.BORDER);
		_currentFolder.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
				false));
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		// Separator
		// new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL)
		// .setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// List of files
		_filesList = new List(shell, SWT.READ_ONLY | SWT.BORDER);
		_filesList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Background settings
		_currentFolder.setBackground(_filesList.getBackground());
		shell.setBackground(_commandTree.getBackground());

		// ------------------------ Listeners ----------------------------------
		// Listeners for enable/disable menu functions
		_filesList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				setCEnabled(_filesList.getSelectionCount() == 0 ? false : true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				setCEnabled(_filesList.getSelectionCount() == 0 ? false : true);
			}
		});

		_filesList.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				setCEnabled(_filesList.getSelectionCount() == 0 ? false : true);
			}

			@Override
			public void focusLost(FocusEvent e) {
				setCEnabled(_filesList.getSelectionCount() == 0 ? false : true);
			}

		});

		// Double click execution
		_commandTree.addListener(SWT.MouseDoubleClick, new Listener() {

			@Override
			public void handleEvent(Event event) {
				if (_commandTree.getSelection().length == 1) {
					TreeItem selItem = _commandTree.getSelection()[0];
					if (selItem == _renameTreeItem) {
						// RenameSelectedFile
						InputDialog dlg = new InputDialog(shell.getShell(),
								"Renaming", "Enter name:", null, null);
						if (dlg.open() == Window.OK) {
							try {
								if (_filesList.getSelection().length == 1) {
									_ioLayer.rename(
											_filesList.getSelection()[0]
													.substring(5), dlg
													.getValue());
									_filesList.setItem(
											_filesList.getSelectionIndex(),
											_filesList.getSelection()[0]
													+ dlg.getValue());
								}
							} catch (Exception e) {
								MessageBox msgBox = new MessageBox(Display
										.getDefault().getActiveShell(),
										SWT.ERROR);
								msgBox.setMessage("Error: " + e.getMessage());
								msgBox.setText("Error");
								msgBox.open();
							}
						}
					}
					if (selItem == _rmTreeItem) {
						try {
							if (_filesList.getSelection().length == 1) {
								_ioLayer.rm(_filesList.getSelection()[0]
										.substring(5));
								_filesList.removeAll();
								setCEnabled(false);
							}
						} catch (IOException e) {
							MessageBox msgBox = new MessageBox(Display
									.getDefault().getActiveShell(), SWT.ERROR);
							msgBox.setMessage("Error: " + e.getMessage());
							msgBox.setText("Error");
							msgBox.open();
						}
					}
					if (selItem == _cdTreeItem) {
						InputDialog dlg = new InputDialog(shell.getShell(),
								"Change directory", "Enter new directory:",
								null, null);
						if (dlg.open() == Window.OK) {
							try {
								_ioLayer.cd(dlg.getValue());
								_currentFolder.setText(_ioLayer
										.getCurrentDirectory()
										.getAbsolutePath());
								_filesList.removeAll();
								setCEnabled(false);
							} catch (Exception e) {
								MessageBox msgBox = new MessageBox(Display
										.getDefault().getActiveShell(),
										SWT.ERROR);
								msgBox.setMessage("Error: " + e.getMessage());
								msgBox.setText("Error");
								msgBox.open();
							}
						}
					}
					if (selItem == _lsTreeItem) {
						try {
							_filesList.removeAll();
							String sep = System.getProperty("line.separator");
							for (String f : _ioLayer.ls().split(sep)) {
								_filesList.add(f);
							}
						} catch (Exception e) {
							MessageBox msgBox = new MessageBox(Display
									.getDefault().getActiveShell(), SWT.ERROR);
							msgBox.setMessage("Access denied");
							msgBox.setText("Error");
							msgBox.open();
						}
					}
				}
			}

		});

		setCEnabled(false);
	}

	private void setCEnabled(boolean state) {
		Color color = (state ? Display.getCurrent().getSystemColor(
				SWT.COLOR_BLACK) : Display.getCurrent().getSystemColor(
				SWT.COLOR_DARK_GRAY));

		_renameTreeItem.setForeground(color);
		_rmTreeItem.setForeground(color);
	}
}
