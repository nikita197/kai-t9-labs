package lab2.ui;

import java.io.IOException;

import lab2.commands.AbstractIOLayer;
import lab2.commands.GIOLayer;

import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSource;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
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
		_fileListText = new List(composite, SWT.READ_ONLY);
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

		// Filling
		refreshView();

		// Listeners
		_fileListText.addListener(SWT.PUSH, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				String[] selection = _fileListText.getSelection();
				if (selection.length > 0) {
					String selItem = selection[0];
					if ("[D]".equals(selItem.substring(1, 4))) {
						try {
							_ioLayer.cd(selItem.substring(5));
							refreshView();
						} catch (IOException e) {
							MessageBox errMsg = new MessageBox(_fileListText
									.getShell(), SWT.ERROR);
							errMsg.setMessage("Opening error");
							errMsg.setText("Error");
							errMsg.open();
						}
					}
				}

			}

		});

		// Drag and Drop (Source)
		int operations = DND.DROP_MOVE;
		DragSource source = new DragSource(_fileListText, operations);

		Transfer[] types = new Transfer[] { TextTransfer.getInstance() };
		source.setTransfer(types);

		source.addDragListener(new DragSourceListener() {
			public void dragStart(DragSourceEvent event) {
			}

			public void dragSetData(DragSourceEvent event) {
				if (TextTransfer.getInstance().isSupportedType(event.dataType)) {
					event.data = _fileListText.getSelection()[0].substring(5);
				}
			}

			public void dragFinished(DragSourceEvent event) {
			}

		});

		// Drag and Drop (Target, Rename)
		operations = DND.DROP_MOVE;
		DropTarget renameTarget = new DropTarget(renameLabel, operations);

		final TextTransfer textTransfer = TextTransfer.getInstance();
		types = new Transfer[] { textTransfer };
		renameTarget.setTransfer(types);

		renameTarget.addDropListener(new DropTargetListener() {
			public void dragEnter(DropTargetEvent event) {

			}

			public void dragOver(DropTargetEvent event) {
			}

			public void dragOperationChanged(DropTargetEvent event) {
			}

			public void dragLeave(DropTargetEvent event) {
			}

			public void dropAccept(DropTargetEvent event) {
			}

			public void drop(DropTargetEvent event) {
				if ((event.data instanceof String)
						&& (_fileListText.getSelection().length > 0)
						&& (_fileListText.getSelection()[0].substring(5)
								.equals(event.data))) {
					String fileName = (String) event.data;

					InputDialog dlg = new InputDialog(_fileListText.getShell(),
							"Renaming", "Enter new file name", null, null);
					if (dlg.open() == Window.OK) {
						try {
							_ioLayer.rename(fileName, dlg.getValue());
							refreshView();
						} catch (Exception e) {
							MessageBox errMsg = new MessageBox(_fileListText
									.getShell(), SWT.ERROR);
							e.printStackTrace();
							errMsg.setMessage("Renaming error: "
									+ e.getMessage());
							errMsg.setText("Error");
							errMsg.open();
						}
					}
				}
			}
		});

		// Drag and Drop (Target, Remove)
		operations = DND.DROP_MOVE;
		DropTarget target = new DropTarget(removeLabel, operations);

		target.setTransfer(types);

		target.addDropListener(new DropTargetListener() {
			public void dragEnter(DropTargetEvent event) {
			}

			public void dragOver(DropTargetEvent event) {
			}

			public void dragOperationChanged(DropTargetEvent event) {
			}

			public void dragLeave(DropTargetEvent event) {
			}

			public void dropAccept(DropTargetEvent event) {

			}

			public void drop(DropTargetEvent event) {
				if ((event.data instanceof String)
						&& (_fileListText.getSelection().length > 0)
						&& (_fileListText.getSelection()[0].substring(5)
								.equals(event.data))) {
					String fileName = (String) event.data;
					try {
						_ioLayer.rm(fileName);
						refreshView();
					} catch (Exception e) {
						MessageBox errMsg = new MessageBox(_fileListText
								.getShell(), SWT.ERROR);
						errMsg.setMessage("Removing error: " + e.getMessage());
						errMsg.setText("Error");
						errMsg.open();
					}
				}
			}

		});
	}

	private void refreshView() {
		_currentFolder
				.setText(_ioLayer.getCurrentDirectory().getAbsolutePath());

		_fileListText.removeAll();
		String sep = System.getProperty("line.separator");
		String files = _ioLayer.ls();
		_fileListText.add("\t[D]\t..");
		if (files.length() > 0) {
			for (String f : files.split(sep)) {
				_fileListText.add(f);
			}
		}

		if (_fileListText.getItemCount() > 0) {
			_fileListText.setSelection(0);
		}
	}
}
