package org.courseworks.ris.widgets.viewers;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.viewers.filters.AbstractFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FilterPanel extends Composite {
    private ExtendedTable _table;
    private DbTable _dbTable;
    private Combo _fieldCombo;
    private Composite _filterPlace;
    private AbstractFilter _filter;
    private Button _filterButton;
    private Button _clearButton;
    private boolean _filterActive;

    public FilterPanel(Composite parent, int style, ExtendedTable table) {
        super(parent, style);
        _table = table;
        setLayout(new GridLayout(1, false));

        init();
    }

    public void init() {
        _fieldCombo = new Combo(this, SWT.BORDER);

        _filterPlace = new Composite(this, SWT.NONE);
        _filterPlace
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        _filterPlace.setLayout(new FillLayout());

        _filterButton = new Button(this, SWT.NONE);
        _filterButton.setText("Filter");

        _clearButton = new Button(this, SWT.NONE);
        _clearButton.setText("Clear filter");
        _clearButton.setEnabled(false);

        _fieldCombo.addListener(SWT.Selection, new Listener() {
            @Override
            public void handleEvent(Event event) {
                initFilter();
            }
        });
        _filterButton.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event arg0) {
                try {
                    applyFilter();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        _clearButton.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                try {
                    removeFilter();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
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

    private void initFilter() {
        if (_filter != null) {
            _filter.dispose();
        }

        _filter = AbstractFilter.getInstance(_filterPlace, SWT.BORDER,
                _dbTable.getFieldClass(_fieldCombo.getText()));
        _filter.getParent().layout();
        _filter.getShell().layout();
    }

    private void applyFilter() throws IllegalAccessException {
        if (_filterActive) {
            removeFilter();
        }

        final int filterColumn = _fieldCombo.getSelectionIndex();
        Field field = (Field) _table.getColumn(filterColumn).getData();

        for (TableItem item : _table.getItems()) {
            AbstractEntity object = (AbstractEntity) item.getData();
            Object fieldValue = field.get(object);
            if (!_filter.checkAgreement(fieldValue)) {
                item.dispose();
            }
        }

        setState(true);
    }

    private void removeFilter() throws IllegalArgumentException,
            IllegalAccessException {
        _table.removeAll();
        _table.fill(_dbTable);

        setState(false);
    }

    /**
     * true - filtered, false - not filtered
     * 
     * @param state
     */
    private void setState(boolean state) {
        _filterActive = state;
        _clearButton.setEnabled(state);
        _filterButton.setEnabled(!state);
        _fieldCombo.setEnabled(!state);
    }

}
