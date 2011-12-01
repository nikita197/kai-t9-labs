package org.courseworks.ris.cmanager.session;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.DatabaseLowlevelProcessor;

public class DbTable {

    private final String _name;

    private final List<AbstractEntity> _items;

    public DbTable(String name) {
        _items = new ArrayList<AbstractEntity>();
        _name = name;
    }

    public void fillItems(ExtendedSession session) {
        for (AbstractEntity obj : DatabaseLowlevelProcessor.selectFromTable(
                session, _name)) {
            obj.setSession(session);
            obj.setTable(this);
            _items.add(obj);
        }
    }

    public void addItems(List<AbstractEntity> items) {
        _items.addAll(items);
    }

    public void clear() {
        _items.clear();
    }

    public String getName() {
        return _name;
    }

    public List<AbstractEntity> getItems() {
        return _items;
    }

    public Field[] getViewableFields() {
        if (_items.size() > 0) {
            return _items.get(0).getViewableFields();
        }
        return null;
    }

    public String getFieldPresentation(String fieldName) {
        if (_items.size() > 0) {
            return _items.get(0).getFieldPresent(fieldName);
        }
        return null;
    }
}
