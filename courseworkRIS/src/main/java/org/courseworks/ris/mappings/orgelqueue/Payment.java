package org.courseworks.ris.mappings.orgelqueue;

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

@Entity
@Table(name = "payment")
public class Payment extends AbstractEntity {

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
		return "payment:" + id;
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 100000;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Payment payment = (Payment) item;
				if (payment.id == i) {
					continue e1;
				}
			}

			id = i;
			break;
		}
	}

	// -----------------------------------------------------------------

	public static String getViewName() {
		return "Платежи";
	}

	public static String getFieldPresent(Field field) {
		String fieldName = field.getName();
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("parking_id".equals(fieldName)) {
			return "Парковка";
		} else if ("rate_id".equals(fieldName)) {
			return "Тариф";
		} else if ("cost".equals(fieldName)) {
			return "Конечная цена";
		}
		return null;
	}

	public static Field[] getFields() {
		return Payment.class.getFields();
	}

	public static Field[] getRequiredFields() {
		return Payment.class.getFields();
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
