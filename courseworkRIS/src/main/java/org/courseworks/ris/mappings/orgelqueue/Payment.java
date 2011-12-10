package org.courseworks.ris.mappings.orgelqueue;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;

@SuppressWarnings("serial")
@Entity
@Table(name = "payment")
public class Payment extends AbstractEntity implements Serializable {

    @Id
    public long id;

    @ManyToOne
    @JoinColumn(name = "parking_id")
    public Parking parking_id;

    @ManyToOne
    @JoinColumn(name = "rate_id")
    public Rate rate_id;

    public Float cost;

    @Override
    public String getName() {
        return "payment:" + parking_id;
    }

    @Override
    public void generateUID() {
        String tableName = getTable().getName();
        EntitySet table = Application.getGenTables().getTable(tableName);

        e1: for (int i = 200000;; i++) {
            for (AbstractEntity item : table.getItems()) {
                Payment rate = (Payment) item;
                if (rate.id == i) {
                    continue e1;
                }
            }

            id = i;
            break;
        }

        cost = 0f;
    }

    public boolean isLinkAllowed() {
        // разрешены все (вызовов все равно не будет)
        return true;
    }

    public void generateCost() {
        if ((parking_id.pickup_date != null) && (parking_id.put_date != null)) {
            long put = parking_id.put_date.getTimeInMillis();
            long pickup = parking_id.pickup_date.getTimeInMillis();

            float currentCost = (((float) pickup - put) / (1000 * 3600))
                    * rate_id.h_cost;
            cost = currentCost;
        }

        Session session = getTable().getSession().getHBSession();
        Transaction transaction = session.beginTransaction();
        session.update(this);
        transaction.commit();
    }

    // -----------------------------------------------------------------

    public static String getViewName() {
        return "Платежи";
    }

    public static String getFieldPresent(Field field) {
        String fieldName = field.getName();
        if ("id".equals(fieldName)) {
            return "Идентификатор";
        } else
            if ("parking_id".equals(fieldName)) {
                return "Парковка";
            } else
                if ("rate_id".equals(fieldName)) {
                    return "Тариф";
                } else
                    if ("cost".equals(fieldName)) {
                        return "Конечная цена";
                    }
        return null;
    }

    public static Field[] getFields() {
        return Payment.class.getFields();
    }

    public static Field[] getRequiredFields() {
        Field[] fields = new Field[2];
        try {
            fields[0] = Payment.class.getField("parking_id");
            fields[1] = Payment.class.getField("rate_id");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Field[] getEditableFields() {
        Field[] fields = new Field[2];
        try {
            fields[0] = Payment.class.getField("parking_id");
            fields[1] = Payment.class.getField("rate_id");
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return fields;
    }

    public static Field[] getViewableFields() {
        Field[] allFields = Payment.class.getDeclaredFields();
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
