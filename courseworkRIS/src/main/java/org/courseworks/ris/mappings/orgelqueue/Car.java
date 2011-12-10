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
@Table(name = "car")
public class Car extends AbstractEntity {

	@Id
	public long id;
	@ManyToOne
	@JoinColumn(name = "mark_id")
	public MarkHelper mark_id;
	public String st_number;
	public String region;

	@Override
	public String getName() {
		return mark_id.toString().trim() + " " + st_number;
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 100000;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Car car = (Car) item;
				if (car.id == i) {
					continue e1;
				}
			}

			id = i;
			break;
		}
	}

	// -----------------------------------------------------------------

	public static String getViewName() {
		return "Машины";
	}

	public static String getFieldPresent(Field field) {
		String fieldName = field.getName();
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("mark_id".equals(fieldName)) {
			return "Марка";
		} else if ("st_number".equals(fieldName)) {
			return "Государственный номер";
		} else if ("region".equals(fieldName)) {
			return "Регион";
		}
		return null;
	}

	public static Field[] getFields() {
		return Car.class.getFields();
	}

	public static Field[] getRequiredFields() {
		return Car.class.getFields();
	}

	public static Field[] getViewableFields() {
		Field[] allFields = Car.class.getDeclaredFields();
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
