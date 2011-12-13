package org.courseworks.ris.mappings.orgelqueue;

import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "parking")
public class Parking extends AbstractEntity {

    @Id
    public long id;
    @ManyToOne
    @JoinColumn(name = "client_id")
    public Client client_id;
    @ManyToOne
    @JoinColumn(name = "car_id")
    public Car car_id;
    @ManyToOne
    @JoinColumn(name = "parking_place_id")
    public Parking_place parking_place_id;

    @Temporal(value = TemporalType.DATE)
    public Calendar put_date;
    @Temporal(value = TemporalType.DATE)
    public Calendar pickup_date;

    @Override
    public String getName() {
        return client_id + " | " + car_id + " | " + parking_place_id;
    }

    @Override
    public void generateUID() {
        String tableName = getTable().getName();
        EntitySet table = Application.getGenTables().getTable(tableName);

        e1: for (int i = 200000;; i++) {
            for (AbstractEntity item : table.getItems()) {
                Parking parking = (Parking) item;
                if (parking.id == i) {
                    continue e1;
                }
            }

            id = i;
            break;
        }
    }

    public boolean isLinkAllowed() {
        // разрешены только незакрытые парковки(без даты съема с парковки)
        return (pickup_date == null);
    }

    // -----------------------------------------------------------------

    public static String getViewName() {
        return "Парковка";
    }

    public static String getFieldPresent(Field field) {
        String fieldName = field.getName();
        if ("id".equals(fieldName)) {
            return "Идентификатор";
        } else
            if ("client_id".equals(fieldName)) {
                return "Клиент";
            } else
                if ("car_id".equals(fieldName)) {
                    return "Машина";
                } else
                    if ("parking_place_id".equals(fieldName)) {
                        return "Парковочное место";
                    } else
                        if ("put_date".equals(fieldName)) {
                            return "Время поставки на парковку";
                        } else
                            if ("pickup_date".equals(fieldName)) {
                                return "Время снятия с парковки";
                            }
        return null;
    }

    public static Field[] getFields() {
        return Parking.class.getFields();
    }

    public static Field[] getRequiredFields() {
        Field[] fields = new Field[5];
        try {
            fields[0] = Parking.class.getField("id");
            fields[1] = Parking.class.getField("client_id");
            fields[2] = Parking.class.getField("car_id");
            fields[3] = Parking.class.getField("parking_place_id");
            fields[4] = Parking.class.getField("put_date");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Field[] getEditableFields() {
        Field[] fields = new Field[4];
        try {
            fields[0] = Parking.class.getField("client_id");
            fields[1] = Parking.class.getField("car_id");
            fields[2] = Parking.class.getField("parking_place_id");
            fields[3] = Parking.class.getField("put_date");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Field[] getViewableFields() {
        Field[] allFields = Parking.class.getDeclaredFields();
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
