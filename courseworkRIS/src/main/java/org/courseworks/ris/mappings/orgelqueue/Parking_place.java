package org.courseworks.ris.mappings.orgelqueue;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "parking_place")
public class Parking_place extends AbstractEntity {

    @Id
    public long id;
    public String number;
    public Boolean state;
    public String description;

    @Override
    public String getName() {
        return number.trim();
    }

    @Override
    public void generateUID() {
        String tableName = getTable().getName();
        EntitySet table = Application.getGenTables().getTable(tableName);

        e1: for (int i = 300000;; i++) {
            for (AbstractEntity item : table.getItems()) {
                Parking_place pplace = (Parking_place) item;
                if (pplace.id == i) {
                    continue e1;
                }
            }

            id = i;
            break;
        }
        state = false;
    }

    public boolean isLinkAllowed() {
        // разрешены только свободные парковки
        return !state;
    }

    // -----------------------------------------------------------------

    public static String getViewName() {
        return "Парковочные места";
    }

    public static String getFieldPresent(Field field) {
        String fieldName = field.getName();
        if ("id".equals(fieldName)) {
            return "Идентификатор";
        } else
            if ("number".equals(fieldName)) {
                return "Номер места";
            } else
                if ("state".equals(fieldName)) {
                    return "Состояние";
                } else
                    if ("description".equals(fieldName)) {
                        return "Описание места";
                    }
        return null;
    }

    public static Field[] getFields() {
        return Parking_place.class.getFields();
    }

    public static Field[] getRequiredFields() {
        return Parking_place.class.getFields();
    }

    public static Field[] getEditableFields() {
        Field[] fields = new Field[2];
        try {
            fields[0] = Parking_place.class.getField("number");
            fields[1] = Parking_place.class.getField("description");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Field[] getViewableFields() {
        Field[] allFields = Parking_place.class.getDeclaredFields();
        List<Field> viewableFields = new LinkedList<Field>();

        for (Field field : allFields) {
            if ("id".equals(field.getName())) {
                continue;
            }
            viewableFields.add(field);
        }

        return viewableFields.toArray(new Field[] {});
    }
}
