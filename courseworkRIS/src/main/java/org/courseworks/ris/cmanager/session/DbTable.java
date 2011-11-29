package org.courseworks.ris.cmanager.session;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.DatabaseLowlevelProcessor;

public class DbTable {

    private final List<AbstractEntity> _items;
    private final String _name;

    public DbTable(String name) {
        _items = new ArrayList<AbstractEntity>();
        _name = name;
    }

    public void fillItems(ExtendedSession session) {
        for (AbstractEntity obj : DatabaseLowlevelProcessor.selectFromTable(
                session, _name)) {
            obj.setSession(session);
            _items.add(obj);
        }
    }

    public void addItems(List<AbstractEntity> items) {
        _items.addAll(items);
    }

    public void clear() {
        _items.clear();
    }

    public Class<?> getType() {
        if (_items.size() > 0) {
            return _items.get(0).getClass();
        }
        return null;
    }

    public String getName() {
        return _name;
    }

    public List<AbstractEntity> getItems() {
        return _items;
    }

    // public List<Type> getFieldTypes() {
    // if (_items.size() > 0) {
    // List<Type> typeList = new ArrayList<Type>();
    // for (Field field : _items.get(0).getClass().getDeclaredFields()) {
    // typeList.add(field.getType());
    // }
    // return typeList;
    // }
    // return null;
    // }

    public Field getField(String name) throws NoSuchFieldException {
        return _items.get(0).getClass().getField(name);
    }

    public Class<?> getFieldClass(String fieldName) {
        if (_items.size() > 0) {
            for (Field field : _items.get(0).getClass().getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field.getType();
                }
            }
        }
        return null;
    }

    public Type getFieldType(String fieldName) {
        if (_items.size() > 0) {
            for (Field field : _items.get(0).getClass().getDeclaredFields()) {
                if (field.getName().equals(fieldName)) {
                    return field.getType();
                }
            }
        }
        return null;
    }
}
