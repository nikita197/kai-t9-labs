package org.courseworks.ris.cmanager.session;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.mappings.AbstractEntity;

public class EntitySet {

    protected final Class<?> _type;

    protected final String _name;

    protected final List<AbstractEntity> _items;

    private Method _getName;
    private Method _getViewableFields;
    private Method _getEditableFields;
    private Method _getRequiredFields;
    private Method _getFieldPresent;

    public EntitySet(Class<?> type) {
        _name = type.getSimpleName();
        _type = type;
        _items = new ArrayList<AbstractEntity>();

        try {
            _getName = _type.getMethod("getViewName");
            _getViewableFields = _type.getMethod("getViewableFields");
            _getRequiredFields = _type.getMethod("getRequiredFields");
            _getEditableFields = _type.getMethod("getEditableFields");
            _getFieldPresent = _type.getMethod("getFieldPresent", Field.class);
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    // ------------------------- List methods ------------------------------

    public void addItems(List<AbstractEntity> items) {
        _items.addAll(items);
    }

    public void addItem(AbstractEntity item) {
        _items.add(item);
    }

    public void removeItem(AbstractEntity item) {
        _items.remove(item);
    }

    public void clear() {
        _items.clear();
    }

    public List<AbstractEntity> getItems() {
        return _items;
    }

    public String getName() {
        return _name;
    }

    // ------------------------- Type methods ------------------------------

    public Class<?> getContentType() {
        return _type;
    }

    public Field[] getRequiredFields() {
        try {
            return (Field[]) _getRequiredFields.invoke(null);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public Field[] getEditableFields() {
        try {
            return (Field[]) _getEditableFields.invoke(null);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public Field[] getViewableFields() {
        try {
            return (Field[]) _getViewableFields.invoke(null);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public String getFieldPresentation(Field field) {
        try {
            return (String) _getFieldPresent.invoke(null, field);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }

    public String getViewName() {
        try {
            return (String) _getName.invoke(null);
        } catch (IllegalAccessException e) {
            return null;
        } catch (InvocationTargetException e) {
            return null;
        }
    }
}
