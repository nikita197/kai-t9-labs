package org.courseworks.ris.widgets;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.SessionsManager;
import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class ExtendedTable extends Table {

    private EntitySet _dbTable;
    private Field[] _fields;

    public ExtendedTable(Composite parent, int style) {
        super(parent, style);
    }

    public void initType(EntitySet dbTable) {
        removeAll();
        for (TableColumn column : getColumns()) {
            column.dispose();
        }

        _fields = dbTable.getViewableFields();
        for (Field fld : _fields) {
            TableColumn newColumn = new TableColumn(this, SWT.NONE);
            newColumn.setData(fld);
            newColumn.setText(dbTable.getFieldPresentation(fld));
        }

        TableColumn sessionColumn = new TableColumn(this, SWT.NONE);
        sessionColumn.setText("Сервер");
    }

    public void fill(EntitySet dbTable) throws IllegalArgumentException,
            IllegalAccessException {
        _dbTable = dbTable;
        refresh();
    }

    public void refresh() throws IllegalArgumentException,
            IllegalAccessException {
        removeAll();
        if (_dbTable != null) {
            for (AbstractEntity obj : _dbTable.getItems()) {
                TableItem newRow = new TableItem(this, SWT.NONE);
                newRow.setData(obj);

                int index = 0;
                for (Field fld : _fields) {
                    Object value = obj.getFieldValue(fld);
                    if (value != null) {
                        newRow.setText(index, value.toString());
                    }
                    index++;
                }
                newRow.setText(index,
                        SessionsManager.getName(obj.getTable().getSession()));
            }
        }
    }

    public AbstractEntity getSelectedItem() {
        int index = getSelectionIndex();

        if (index == -1) {
            return null;
        }

        return (AbstractEntity) getItem(index).getData();
    }

    public EntitySet getTable() {
        return _dbTable;
    }

    @Override
    public void pack() {
        TableColumn[] columns = getColumns();

        int columnsWidth = 0;
        for (TableColumn column : columns) {
            column.pack();
            columnsWidth += column.getWidth();
        }

        // Если колонки суммарно имеют меньшюю ширину, чем таблица
        int difference = getSize().x - columnsWidth;
        if (difference > 0) {
            int additionalWidth = difference / columns.length;
            for (TableColumn column : columns) {
                column.setWidth(column.getWidth() + additionalWidth);
            }
        }
    }

    @Override
    public void checkSubclass() {

    }
}
