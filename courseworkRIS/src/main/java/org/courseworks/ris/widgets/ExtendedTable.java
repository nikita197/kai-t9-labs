package org.courseworks.ris.widgets;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.DbTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExtendedTable extends Table {
    private Field[] _fields;

    public ExtendedTable(Composite parent, int style) {
        super(parent, style);
    }

    public void initType(DbTable dbTable) {
        _fields = dbTable.getType().getDeclaredFields();
        for (Field fld : _fields) {
            TableColumn newColumn = new TableColumn(this, SWT.NONE);
            newColumn.setData(fld);
            newColumn.setText(fld.getName());
        }
    }

    public void fill(DbTable dbTable) throws IllegalArgumentException,
            IllegalAccessException {
        for (Object obj : dbTable.getItems()) {
            TableItem newRow = new TableItem(this, SWT.NONE);
            newRow.setData(obj);
            int index = 0;
            for (Field fld : _fields) {
                newRow.setText(index, fld.get(obj).toString());
                index++;
            }
        }
    }

    public void pack() {
        for (TableColumn tbc : this.getColumns()) {
            tbc.pack();
        }
    }

    @Override
    public void checkSubclass() {

    }
}
