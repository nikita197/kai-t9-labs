package org.courseworks.ris.mappings.orgelqueue;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.hprepair.Cars;

@Entity
@Table(name = "plane")
public class Plane extends AbstractEntity {

	@Id
	public long id;

	@Column(length = 30)
	public String name;

	public boolean state;

	@ManyToOne
	@JoinColumn(name = "engine")
	public Engine engine;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 100;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Plane plane = (Plane) item;
				if (plane.id == i) {
					continue e1;
				}
			}

			id = i;
			break;
		}
	}

	// -----------------------------------------------------------------

	public static String getViewName() {
		return "Самолеты";
	}

	public static String getFieldPresent(Field field) {
		String fieldName = field.getName();
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("name".equals(fieldName)) {
			return "Имя";
		} else if ("state".equals(fieldName)) {
			return "Состояние";
		} else if ("engine".equals(fieldName)) {
			return "Двигатель";
		}
		return null;
	}

	public static Field[] getFields() {
		return Cars.class.getFields();
	}

	public static Field[] getViewableFields() {
		Field[] allFields = Plane.class.getDeclaredFields();
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
