package org.courseworks.ris.widgets.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.typeblocks.editors.AbstractFieldEditor;
import org.courseworks.ris.widgets.typeblocks.editors.RelatedObjectEditor;
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
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.hibernate.Session;

public class AddView {

	public static final int TYPE_ADD = 0;

	public static final int TYPE_UPDATE = 1;

	private final Shell _shell;

	private final DbTable _sourceTable;

	private DbTable _currentTable;

	private final List<AbstractFieldEditor> _fieldEditors;

	private AbstractEntity _item;

	private Combo _sessionsCombo;

	private List<AbstractFieldEditor> _relatedObjectsEditors;

	private final Button _acceptButton;

	private final Button _discardButton;

	private boolean _actionPerformed;

	private boolean _exit;

	private int selectedIndex;

	public AddView(Shell shell, String name, DbTable table, int type)
			throws InstantiationException, IllegalAccessException {
		_actionPerformed = false;
		_exit = false;
		_currentTable = null;
		_relatedObjectsEditors = new ArrayList<AbstractFieldEditor>();
		_fieldEditors = new ArrayList<AbstractFieldEditor>();
		_sourceTable = table;

		_shell = new Shell(shell, SWT.SHELL_TRIM);
		GridLayout shellLayout = new GridLayout();
		shellLayout.numColumns = 2;
		_shell.setLayout(shellLayout);
		_shell.setText(name);

		_sessionsCombo = new Combo(_shell, SWT.READ_ONLY);
		_sessionsCombo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 2, 1));

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
			_sessionsCombo.setEnabled(false);
		}
		_acceptButton.setText("ОК");
		_discardButton.setText("Отмена");

		_acceptButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				try {
					_actionPerformed = true;
					itemFillback();
				} catch (Exception e) {
					MessageBox msgBox = new MessageBox(_shell, SWT.OK
							| SWT.ICON_ERROR);
					msgBox.setText("Ошибка");
					msgBox.setText("Необходимые поля не были заполнены.");
					_actionPerformed = false;
				}
			}

		});
		_discardButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				_exit = true;
			}

		});

		initContent(composite);

		_shell.pack();
	}

	public void setItem(AbstractEntity item) {
		_item = item;
	}

	public AbstractEntity getItem() {
		return _item;
	}

	public boolean open() throws IllegalAccessException {
		fillContent();
		_shell.open();

		Shell parentShell = _shell.getShell();
		Display display = _shell.getDisplay();

		while ((!parentShell.isDisposed()) && (!_shell.isDisposed())
				&& (!_exit)) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}

		return _actionPerformed;
	}

	private void initContent(Composite composite) {
		for (Field field : _sourceTable.getViewableFields()) {
			Label name = new Label(composite, SWT.NONE);
			name.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
			name.setText(_sourceTable.getFieldPresentation(field.getName()));

			AbstractFieldEditor editor = AbstractFieldEditor.getInstance(
					composite, SWT.NONE, field.getType());
			editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
			editor.setData(field);
			_fieldEditors.add(editor);

			if ((!field.getType().isPrimitive())
					&& (field.getType().getSuperclass()
							.equals(AbstractEntity.class))) {
				_relatedObjectsEditors.add(editor);
			}
		}
	}

	private void fillContent() throws IllegalAccessException {
		String[] sessions = ConnectionsManager.getSessionsNames();
		for (String session : sessions) {
			_sessionsCombo.add(session);
		}

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					if ((arg0 == null)
							|| (selectedIndex != _sessionsCombo
									.getSelectionIndex())) {
						selectedIndex = _sessionsCombo.getSelectionIndex();
						ExtendedSession session = ConnectionsManager
								.getSession(_sessionsCombo.getText());
						_currentTable = session
								.getTable(_sourceTable.getName());

						for (AbstractFieldEditor editor : _relatedObjectsEditors) {
							Field field = (Field) editor.getData();
							DbTable table = _currentTable
									.getRelatedTable(field);

							Object[] value = new Object[2];
							value[0] = table.getItems().toArray();
							value[1] = 0;

							editor.setValue(value);
						}
					}
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		};

		// update case
		Session currentSession = _item.getSession().getSession();
		String currentSessionName = ConnectionsManager.getName(currentSession);
		for (int i = 0; i < sessions.length; i++) {
			if (sessions[i].equals(currentSessionName)) {
				_sessionsCombo.select(i);
			}
		}

		// add case
		if (_sessionsCombo.getSelectionIndex() == -1) {
			_sessionsCombo.select(0);
		}

		listener.handleEvent(null);

		_sessionsCombo.addListener(SWT.Selection, listener);

		for (AbstractFieldEditor editor : _fieldEditors) {
			Field field = (Field) editor.getData();
			Object value = field.get(_item);

			if ((value == null) || (editor instanceof RelatedObjectEditor)) {
				continue;
			}

			editor.setValue(value);
		}

	}

	private void itemFillback() throws IllegalAccessException {
		for (AbstractFieldEditor editor : _fieldEditors) {
			Field field = (Field) editor.getData();
			field.set(_item, editor.getValue());
		}

		_item.setTable(_currentTable);
		_item.setSession(ConnectionsManager.getSession(_sessionsCombo.getText()));
		_item.generateUID();
	}
}
