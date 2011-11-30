package org.courseworks.ris.widgets.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.typeblocks.finders.AbstractFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AddView {

	public static final int TYPE_ADD = 0;
	public static final int TYPE_UPDATE = 1;

	private Shell _shell;
	private DbTable _table;
	private List<AbstractFieldEditor> _fieldEditors;
	private AbstractEntity _item;
	private Button _acceptButton;
	private Button _discardButton;

	private boolean _actionPerformed;

	public AddView(Shell shell, String name, DbTable table, int type) {
		_actionPerformed = false;
		_fieldEditors = new ArrayList<AbstractFieldEditor>();
		_table = table;

		_shell = new Shell(shell, SWT.SHELL_TRIM);
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		_shell.setLayout(shellLayout);
		_shell.setText(name);

		Label label = new Label(_shell, SWT.NONE);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2,
				1));

		Composite composite = new Composite(_shell, SWT.BORDER);
		GridLayout compositeLayout = new GridLayout();
		compositeLayout.numColumns = 2;
		composite.setLayout(compositeLayout);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2,
				1));

		_acceptButton = new Button(_shell, SWT.PUSH);
		_discardButton = new Button(_shell, SWT.PUSH);

		if (type == TYPE_ADD) {
			label.setText("Заполните поля добавляемого объекта:");
		} else if (type == TYPE_UPDATE) {
			label.setText("Изменение полей записи:");
		}
		_acceptButton.setText("ОК");
		_discardButton.setText("Отмена");

		initContent(composite);

		_shell.pack();
	}

	public void setItem(AbstractEntity item) {
		_item = item;
	}

	public AbstractEntity getItem() {
		return _item;
	}

	public boolean open() {
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

	private void initContent(Composite composite) {
		for (Field field : _table.getViewableFields()) {
			Label name = new Label(composite, SWT.NONE);
			name.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			name.setText(_table.getFieldPresentation(field.getName()));

			AbstractFieldEditor editor = AbstractFieldEditor.getInstance(
					composite, SWT.NONE, field.getType());
			editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			_fieldEditors.add(editor);
		}
	}

	private void fillContent() {
		// заполнение виджетов
	}
}
