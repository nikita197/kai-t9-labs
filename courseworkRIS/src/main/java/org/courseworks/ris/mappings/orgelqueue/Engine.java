package org.courseworks.ris.mappings.orgelqueue;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.hprepair.Cars;

@Entity
@Table(name = "engine")
public class Engine extends AbstractEntity {

    @Id
    public long id;

    public String mark;

    @Temporal(value = TemporalType.DATE)
    public Calendar makedate;

    @Override
    public String getName() {
        return mark;
    }

    @Override
    public void generateUID() {
        String tableName = getTable().getName();
        EntitySet table = Application.getGenTables().getTable(tableName);

        e1: for (int i = 100;; i++) {
            for (AbstractEntity item : table.getItems()) {
                Engine engine = (Engine) item;
                if (engine.id == i) {
                    continue e1;
                }
            }

            id = i;
            break;
        }
    }

    // -----------------------------------------------------------------

    public static String getViewName() {
        return "Двигатели";
    }

    public static String getFieldPresent(Field field) {
        String fieldName = field.getName();
        if ("id".equals(fieldName)) {
            return "Идентификатор";
        } else
            if ("mark".equals(fieldName)) {
                return "Марка";
            } else
                if ("makedate".equals(fieldName)) {
                    return "Дата производства";
                }
        return null;
    }

    public static Field[] getFields() {
        return Cars.class.getFields();
    }

    public static Field[] getViewableFields() {
        Field[] allFields = Engine.class.getDeclaredFields();
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
