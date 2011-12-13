package org.kai.CMV.lab4.widgets.views;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.kai.CMV.lab4.widgets.ExtendedTable;
import org.kai.CMV.lab4.widgets.typeblocks.editors.AbstractFieldEditor;

public abstract class AbstractView {

	protected final List<AbstractFieldEditor> _fieldEditors;

	protected final Shell _shell;

	protected Combo _sessionsCombo;

	protected Label _headerLabel;

	protected final Button _acceptButton;

	protected final Button _discardButton;

	protected boolean _actionPerformed;

	protected int selectedIndex;

	public AbstractView(Shell shell, String name)
			throws InstantiationException, IllegalAccessException {
		_actionPerformed = false;
		_fieldEditors = new ArrayList<AbstractFieldEditor>();

		_shell = new Shell(shell, SWT.SHELL_TRIM);
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		_shell.setLayout(shellLayout);
		_shell.setText(name);

		_sessionsCombo = new Combo(_shell, SWT.READ_ONLY);
		_sessionsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));

		_headerLabel = new Label(_shell, SWT.NONE);
		_headerLabel.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 2, 1));

		Composite composite = new Composite(_shell, SWT.BORDER);
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.numColumns = 2;
		composite.setLayout(compositeLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));

		Class<?>[] types = ExtendedTable.getFields();
		String[] headers = ExtendedTable.getHeaders();

		for (int i = 0; i < headers.length; i++) {
			Label name1 = new Label(composite, SWT.NONE);
			name1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			name1.setText(headers[i]);

			AbstractFieldEditor editor = AbstractFieldEditor.getInstance(
					composite, SWT.NONE, types[i], AbstractFieldEditor.EDIT,
					false);
			editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			_fieldEditors.add(editor);
		}

		_acceptButton = new Button(_shell, SWT.PUSH);
		_acceptButton.setText("ОК");

		_discardButton = new Button(_shell, SWT.PUSH);
		_discardButton.setText("Отмена");

		_acceptButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				okButtonPressed();
			}

		});
		_discardButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				cancelButtonPressed();
			}

		});

		_shell.pack();
		_shell.setSize(_shell.getSize().x + 75, _shell.getSize().y);
	}

	protected abstract void okButtonPressed();

	protected abstract void cancelButtonPressed();

	public boolean open() throws IllegalAccessException {
		fillContent();
		_shell.open();

		Shell parentShell = _shell.getShell();
		Display display = _shell.getDisplay();

		while ((!parentShell.isDisposed()) && (!_shell.isDisposed())) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return _actionPerformed;
	}

	protected abstract void fillContent() throws IllegalAccessException;

}
