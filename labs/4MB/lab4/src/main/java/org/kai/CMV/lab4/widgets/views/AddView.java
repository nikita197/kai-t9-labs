package org.kai.CMV.lab4.widgets.views;

import java.lang.reflect.Field;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.kai.CMV.lab4.cmanager.SessionsManager;
import org.kai.CMV.lab4.cmanager.session.EntitySet;
import org.kai.CMV.lab4.cmanager.session.ExtendedSession;
import org.kai.CMV.lab4.mappings.AbstractEntity;
import org.kai.CMV.lab4.widgets.typeblocks.editors.AbstractFieldEditor;

public class AddView extends AbstractView {

    public AddView(Shell shell, String name, EntitySet table)
            throws InstantiationException, IllegalAccessException {
        super(shell, name, table);
        _headerLabel.setText("Заполните поля добавляемого объекта:");
    }

    @Override
    protected void okButtonPressed() {
        try {
            fillItem();

            List<String> unfilledFields = getNfilledFields();
            if (unfilledFields.size() == 0) {
                _actionPerformed = true;
                _shell.close();
            } else {
                MessageBox msgBox = new MessageBox(_shell, SWT.OK
                        | SWT.ICON_ERROR);
                msgBox.setText("Необходимые поля не были заполнены.");
                StringBuffer fields = new StringBuffer();
                for (String field : unfilledFields) {
                    fields.append("'" + field + "' ");
                }
                msgBox.setMessage("Необходимо заполнить поля: "
                        + fields.toString());
                msgBox.open();
                _actionPerformed = false;
            }
        } catch (Exception e) {
            _actionPerformed = false;
        }
    }

    @Override
    protected void cancelButtonPressed() {
        _actionPerformed = false;
        _shell.close();
    }

    @Override
    protected void fillContent() throws IllegalAccessException {
        String[] sessions = SessionsManager.getSessionsNames();
        for (String session : sessions) {
            _sessionsCombo.add(session);
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

    protected void fillItem() throws IllegalAccessException {
        super.fillItem();
        _item.generateUID();
    }

    public void setItem(AbstractEntity item) {
        _item = item;
    }

    public AbstractEntity getItem() {
        return _item;
    }

}
