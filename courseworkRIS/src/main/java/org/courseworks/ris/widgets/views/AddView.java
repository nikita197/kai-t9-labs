package org.courseworks.ris.widgets.views;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.typeblocks.editors.AbstractFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;

public class AddView {

    public static final int TYPE_ADD = 0;

    public static final int TYPE_UPDATE = 1;

    private final Shell _shell;

    private final DbTable _table;

    private final List<AbstractFieldEditor> _fieldEditors;

    private AbstractEntity _item;

    private final Button _acceptButton;

    private final Button _discardButton;

    private boolean _actionPerformed;

    private boolean _exit;

    public AddView(Shell shell, String name, DbTable table, int type)
            throws InstantiationException, IllegalAccessException {
        _actionPerformed = false;
        _exit = false;
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

        _acceptButton.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event aEvent) {
                try {
                    _actionPerformed = true;
                    itemFillback();
                } catch (Exception e) {
                    MessageBox msgBox =
                            new MessageBox(_shell, SWT.OK | SWT.ICON_ERROR);
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
        for (Field field : _table.getViewableFields()) {
            Label name = new Label(composite, SWT.NONE);
            name.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
            name.setText(_table.getFieldPresentation(field.getName()));

            AbstractFieldEditor editor =
                    AbstractFieldEditor.getInstance(composite, SWT.NONE,
                            field.getType());
            editor.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            editor.setData(field);
            _fieldEditors.add(editor);
        }
    }

    private void fillContent() throws IllegalAccessException {
        for (AbstractFieldEditor editor : _fieldEditors) {
            Field field = (Field) editor.getData();
            Object value = field.get(_item);

            // PATCH for linked fields
            if (value instanceof AbstractEntity) {
                AbstractEntity entity = (AbstractEntity) value;
                value = new Object[2];

                // All possible objects from table
                List<AbstractEntity> allItems = entity.getTable().getItems();
                Object[] valueA = allItems.toArray();
                ((Object[]) value)[0] = valueA;

                // Index of current item
                ((Object[]) value)[1] = allItems.indexOf(entity);
            }

            editor.setValue(value);
        }

    }

    private void itemFillback() throws IllegalAccessException {
        for (AbstractFieldEditor editor : _fieldEditors) {
            Field field = (Field) editor.getData();
            field.set(_item, editor.getValue());
        }
    }
}
