package org.courseworks.ris.mappings;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.ExtendedSession;

public abstract class AbstractEntity extends Object {

    private ExtendedSession _session;

    private DbTable _table;

    public abstract String getName();

    public DbTable getTable() {
        return _table;
    }

    public void setTable(DbTable table) {
        _table = table;
    }

    public ExtendedSession getSession() {
        return _session;
    }

    public void setSession(ExtendedSession session) {
        _session = session;
    }

    @Override
    public String toString() {
        return getName();
    }

    public abstract Field[] getFields();

    public abstract Field[] getViewableFields();

    public abstract String getFieldPresent(String fieldName);
}
