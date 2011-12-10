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
@Table(name = "rate")
public class Rate extends AbstractEntity {

	@Id
	public long id;
	public int h_cost;

	@Override
	public String getName() {
		return String.valueOf(h_cost) + "руб/час";
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 200000;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Rate rate = (Rate) item;
				if (rate.id == i) {
					continue e1;
				}
			}

			id = i;
			break;
		}
	}

	// -----------------------------------------------------------------

	public static String getViewName() {
		return "Тарифы";
	}

	public static String getFieldPresent(Field field) {
		String fieldName = field.getName();
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("h_cost".equals(fieldName)) {
			return "Руб/час";
		}
		return null;
	}

	public static Field[] getFields() {
		return Rate.class.getFields();
	}

	public static Field[] getRequiredFields() {
		return Rate.class.getFields();
	}

	public static Field[] getViewableFields() {
		Field[] allFields = Rate.class.getDeclaredFields();
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
