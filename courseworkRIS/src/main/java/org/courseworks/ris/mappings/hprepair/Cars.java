package org.courseworks.ris.mappings.hprepair;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;

@Entity
@Table(name = "Cars")
public class Cars extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;

	public String Name;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return Name;
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 100;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Cars car = (Cars) item;
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
		if ("Name".equals(fieldName)) {
			return "Имя";
		} else if ("id".equals(fieldName)) {
			return "Идентификатор";
		}
		return null;
	}

	public static Field[] getFields() {
		return Cars.class.getFields();
	}

	public static Field[] getViewableFields() {
		Field[] allFields = Cars.class.getDeclaredFields();
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
