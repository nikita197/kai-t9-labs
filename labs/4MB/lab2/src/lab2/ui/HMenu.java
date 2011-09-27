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
import org.eclipse.swt.widgets.Button;
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

	private boolean _filesListHaveSeletedItem = false;

	public HMenu() {
		_ioLayer = new GIOLayer();
	}

	public void start() {
		Display display = new Display();
		final Shell shell = new Shell(display);
		GridLayout layout = new GridLayout();
		layout.verticalSpacing = 10;
		layout.horizontalSpacing = 25;
		layout.numColumns = 2;
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

	private void createContent(final Shell shell) {
		_commandTree = new Tree(shell, SWT.READ_ONLY | SWT.EMBEDDED);
		_commandTree.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false,
				false));

		// Current folder
		_currentFolder = new Label(shell, SWT.NONE);
		_currentFolder.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true,
				false));
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		final Button execButton = new Button(shell, SWT.NONE);
		execButton.setText("Execute command");
		execButton
				.setLayoutData(new GridData(SWT.LEFT, SWT.NONE, false, false));
		execButton.setEnabled(false);

		// Separator
		new Label(shell, SWT.SEPARATOR | SWT.HORIZONTAL)
				.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		new Label(shell, 0);

		// List of files
		_filesList = new List(shell, SWT.READ_ONLY);
		_filesList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_currentFolder.setBackground(_filesList.getBackground());

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

		// ------------------------ Listeners ----------------------------------
		_filesList.addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (_filesList.getSelectionIndex() != 1) {
					_filesListHaveSeletedItem = true;
					if (!getSelectedCommand().equals("empty_command")) {
						execButton.setEnabled(true);
					}
				} else {
					_filesListHaveSeletedItem = false;
				}
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				if (_filesList.getSelectionIndex() != 1) {
					_filesListHaveSeletedItem = true;
					if (!getSelectedCommand().equals("empty_command")) {
						execButton.setEnabled(true);
					}
				} else {
					_filesListHaveSeletedItem = false;
				}
			}
		});

		execButton.addListener(SWT.MouseUp, new Listener() {
			@Override
			public void handleEvent(Event event) {
				executeSelectedCommand(shell);
			}
		});

		_commandTree.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				String selectedCommand = getSelectedCommand();
				if (selectedCommand.equals("empty_command")
						|| (selectedCommand.equals("rename") || selectedCommand
								.equals("rm")) && !_filesListHaveSeletedItem) {
					execButton.setEnabled(false);
					return;
				}
				execButton.setEnabled(true);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				String selectedCommand = getSelectedCommand();
				if (selectedCommand.equals("empty_command")
						|| (selectedCommand.equals("rename") || selectedCommand
								.equals("rm")) && !_filesListHaveSeletedItem) {
					execButton.setEnabled(false);
					return;
				}
				execButton.setEnabled(true);
			}
		});
	}

	private String getSelectedCommand() {
		for (TreeItem ti : _commandTree.getSelection()) {
			if (ti.equals(_opTreeItem) || ti.equals(_navTreeItem)) {
				return "empty_command";
			} else if (ti.equals(_lsTreeItem)) {
				return "ls";
			} else if (ti.equals(_cdTreeItem)) {
				return "cd";
			} else if (ti.equals(_renameTreeItem)) {
				return "rename";
			} else if (ti.equals(_rmTreeItem)) {
				return "rm";
			} else {
				return "unknown_selected_element_please_check_tree";
			}
		}
		return null;
	}

	private void executeSelectedCommand(Shell shell) {
		String selectedCommand = getSelectedCommand();

		if (selectedCommand.equals("ls")) {
			// List files
			_filesList.removeAll();
			String sep = System.getProperty("line.separator");
			for (String f : _ioLayer.ls().split(sep)) {
				_filesList.add(f);
			}
		} else if (selectedCommand.equals("cd")) {
			// Change directory
			InputDialog dlg = new InputDialog(shell.getShell(),
					"Enter new directory", null, null, null);
			if (dlg.open() == Window.OK) {
				try {
					_ioLayer.cd(dlg.getValue());
					_currentFolder.setText(_ioLayer.getCurrentDirectory()
							.getAbsolutePath());
					_filesList.removeAll();
					_filesListHaveSeletedItem = false;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (selectedCommand.equals("rename")) {
			// RenameSelectedFile
			InputDialog dlg = new InputDialog(shell.getShell(),
					"Enter new file name", null, null, null);
			if (dlg.open() == Window.OK) {
				try {
					_ioLayer.rename(
							_filesList.getItem(_filesList.getSelectionIndex())
									.substring(5), dlg.getValue());
					_filesList.setItem(_filesList.getSelectionIndex(),
							_filesList.getItem(_filesList.getSelectionIndex())
									.substring(0, 5) + dlg.getValue());
				} catch (IllegalArgumentException e) {
					MessageBox msgBox = new MessageBox(Display.getDefault()
							.getActiveShell(), SWT.OK);
					msgBox.setMessage("File name is incorrect");
					msgBox.open();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else if (selectedCommand.equals("rm")) {
			// DeleteSelectedFile
			try {
				_ioLayer.rm(_filesList.getItem(_filesList.getSelectionIndex())
						.substring(5));
				_filesList.removeAll();
				_filesListHaveSeletedItem = false;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
