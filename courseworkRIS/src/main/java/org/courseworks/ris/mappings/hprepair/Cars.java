package org.courseworks.ris.mappings.hprepair;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.courseworks.ris.cmanager.session.DbTable;
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
	public String getFieldPresent(String fieldName) {
		if ("Name".equals(fieldName)) {
			return "Имя";
		} else if ("id".equals(fieldName)) {
			return "Идентификатор";
		}
		return null;
	}

	@Override
	public Field[] getFields() {
		return getClass().getFields();
	}

	@Override
	public Field[] getViewableFields() {
		Field[] allFields = getClass().getDeclaredFields();
		List<Field> viewableFields = new LinkedList<Field>();

		for (Field field : allFields) {
			if ("id".equals(field.getName())) {
				continue;
			}
			viewableFields.add(field);
		}

		return viewableFields.toArray(new Field[] {});
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		DbTable table = Application.getGenTables().getTable(tableName);

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
}
