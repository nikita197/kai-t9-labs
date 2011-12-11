package org.courseworks.ris.mappings.orgelqueue;

import java.lang.reflect.Field;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "markhelper")
public class MarkHelper extends AbstractEntity {

    @Id
    public String id;
    public String name;

    @Override
    public String getName() {
        return id.trim();
    }

    @Override
    public void generateUID() {
    }

    public boolean isLinkAllowed() {
        // машин с такой маркой может быть сколько угодно
        return true;
    }

    // -----------------------------------------------------------------

    public static String getViewName() {
        return "Справочник машин";
    }

    public static String getFieldPresent(Field field) {
        String fieldName = field.getName();
        if ("id".equals(fieldName)) {
            return "Идентификатор";
        } else
            if ("name".equals(fieldName)) {
                return "Имя";
            }
        return null;
    }

    public static Field[] getFields() {
        return MarkHelper.class.getFields();
    }

    public static Field[] getRequiredFields() {
        return MarkHelper.class.getFields();
    }

    public static Field[] getEditableFields() {
        return MarkHelper.class.getFields();
    }

    public static Field[] getViewableFields() {
        return MarkHelper.class.getFields();
    }
}
