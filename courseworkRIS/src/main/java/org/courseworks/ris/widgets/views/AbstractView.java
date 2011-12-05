package org.courseworks.ris.widgets.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.EntitySet;
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
import org.eclipse.swt.widgets.Shell;

public abstract class AbstractView {

    protected final EntitySet _table;

    protected AbstractEntity _item;

    protected DbTable _currentTable;

    protected final List<AbstractFieldEditor> _fieldEditors;

    protected final Shell _shell;

    protected Combo _sessionsCombo;

    protected Label _headerLabel;

    protected final Button _acceptButton;

    protected final Button _discardButton;

    protected boolean _actionPerformed;

    protected int selectedIndex;

    public AbstractView(Shell shell, String name, EntitySet table)
            throws InstantiationException, IllegalAccessException {
        _actionPerformed = false;
        _fieldEditors = new ArrayList<AbstractFieldEditor>();
        _table = table;

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

    protected void fillItem() throws IllegalAccessException {
        for (AbstractFieldEditor editor : _fieldEditors) {
            Field field = (Field) editor.getData();
            field.set(_item, editor.getValue());
        }

        _item.setTable(_currentTable);
    }

    protected List<String> getNfilledFields() throws IllegalAccessException {
        List<String> fieldPresns = new LinkedList<String>();
        for (Field field : _table.getRequiredFields()) {
            if (field.get(_item) == null) {
                fieldPresns.add(_table.getFieldPresentation(field));
            }
        }
        return fieldPresns;
    }

    public void setItem(AbstractEntity item) {
        _item = item;
    }

    public AbstractEntity getItem() {
        return _item;
    }
}
