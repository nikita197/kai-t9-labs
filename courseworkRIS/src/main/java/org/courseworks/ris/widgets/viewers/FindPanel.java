package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.viewers.finders.AbstractFinder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FindPanel extends Composite {
    private Combo _fieldCombo;
    private ExtendedTable _table;
    private AbstractFinder _finder;
    private DbTable _dbTable;

    public FindPanel(Composite parent, int style, ExtendedTable table) {
        super(parent, style);
        _table = table;
        setLayout(new GridLayout(1, false));

        init();
    }

    public void init() {
        _fieldCombo = new Combo(this, SWT.BORDER);

        Button findButton = new Button(this, SWT.NONE);
        findButton.setText("Find");

        _fieldCombo.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                initFinder();
            }
        });

        findButton.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                findValue();
            }
        });
    }

    public void initType(DbTable dbTable) {
        _dbTable = dbTable;

        _fieldCombo.removeAll();
        for (TableColumn tbc : _table.getColumns()) {
            _fieldCombo.add(tbc.getText());
        }
    }

    public void initFinder() {
        if (_finder != null) {
            _finder.dispose();
        }

        _finder = AbstractFinder.getInstance(FindPanel.this, SWT.BORDER,
                _dbTable.getFieldClass(_fieldCombo.getText()));
        _finder.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
    }

    public void findValue() {
        Object object = _finder.getSearchValue();
        final int column = _fieldCombo.getSelectionIndex();
        for (TableItem item : _table.getItems()) {
            if (object.toString().equals(item.getText(column))) {
                _table.setSelection(item);
            }
        }
    }

}
