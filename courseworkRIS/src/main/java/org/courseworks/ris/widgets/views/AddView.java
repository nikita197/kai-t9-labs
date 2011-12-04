package org.courseworks.ris.widgets.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.SessionsManager;
import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.typeblocks.editors.AbstractFieldEditor;
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

public class AddView {

    public static final int TYPE_ADD = 1;

    public static final int TYPE_UPDATE = 2;

    private final Shell _shell;

    private final EntitySet _table;

    private int _type;

    private DbTable _currentTable;

    private final List<AbstractFieldEditor> _fieldEditors;

    private AbstractEntity _item;

    private Combo _sessionsCombo;

    private final Button _acceptButton;

    private final Button _discardButton;

    private boolean _actionPerformed;

    private boolean _exit;

    private int selectedIndex;

    public AddView(Shell shell, String name, EntitySet table, int type)
            throws InstantiationException, IllegalAccessException {
        _actionPerformed = false;
        _exit = false;
        _fieldEditors = new ArrayList<AbstractFieldEditor>();
        _table = table;
        _type = type;

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

        for (Field field : _table.getViewableFields()) {
            Label name1 = new Label(composite, SWT.NONE);
            name1.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
            name1.setText(_table.getFieldPresentation(field));

            AbstractFieldEditor editor = AbstractFieldEditor
                    .getInstance(composite, SWT.NONE, field,
                            AbstractFieldEditor.EDIT, false);
            editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            editor.setData(field);
            _fieldEditors.add(editor);
        }

        _acceptButton = new Button(_shell, SWT.PUSH);
        _discardButton = new Button(_shell, SWT.PUSH);

        if (type == TYPE_ADD) {
            label.setText("Заполните поля добавляемого объекта:");
        } else
            if (type == TYPE_UPDATE) {
                label.setText("Изменение полей записи:");
            }
        _acceptButton.setText("ОК");
        _discardButton.setText("Отмена");

        _acceptButton.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event aEvent) {
                try {
                    _actionPerformed = true;
                    fillItem();
                    _shell.close();
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
                _shell.close();
            }

        });

        _shell.pack();
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

    private void fillContent() throws IllegalAccessException {
        if (TYPE_UPDATE == _type) {
            ExtendedSession currentSession = _item.getTable().getSession();
            String currentSessionName = SessionsManager.getName(currentSession);
            _sessionsCombo.add(currentSessionName);
            _sessionsCombo.setEnabled(false);
        } else
            if (TYPE_ADD == _type) {
                String[] sessions = SessionsManager.getSessionsNames();
                for (String session : sessions) {
                    _sessionsCombo.add(session);
                }
            }

        Listener listener = new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                if ((arg0 == null)
                        || (selectedIndex != _sessionsCombo.getSelectionIndex())) {
                    selectedIndex = _sessionsCombo.getSelectionIndex();
                    ExtendedSession session = SessionsManager
                            .getSession(_sessionsCombo.getText());
                    _currentTable = session.getEqualTable(_table);
                    _item.setTable(_currentTable);

                    for (AbstractFieldEditor editor : _fieldEditors) {
                        editor.setEditingItem(_item);
                    }

                }

            }

        };

        _sessionsCombo.select(0);
        listener.handleEvent(null);
        _sessionsCombo.addListener(SWT.Selection, listener);

        for (AbstractFieldEditor editor : _fieldEditors) {
            Field field = (Field) editor.getData();
            Object value = field.get(_item);

            if (value == null) {
                continue;
            }
            editor.setValue(value);
        }

    }

    private void fillItem() throws IllegalAccessException {
        for (AbstractFieldEditor editor : _fieldEditors) {
            Field field = (Field) editor.getData();
            field.set(_item, editor.getValue());
        }

        _item.setTable(_currentTable);
        if (TYPE_ADD == _type) {
            _item.generateUID();
        }
    }

    public void setItem(AbstractEntity item) {
        _item = item;
    }

    public AbstractEntity getItem() {
        return _item;
    }
}
