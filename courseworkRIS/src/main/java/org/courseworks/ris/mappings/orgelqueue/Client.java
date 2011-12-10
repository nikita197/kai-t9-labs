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
@Table(name = "client")
public class Client extends AbstractEntity {

	@Id
	public long id;
	public String surname;
	public String name;
	public String middlename;
	public String passp_number;

	@Override
	public String getName() {
		return surname + " " + name + " " + middlename;
	}

	@Override
	public void generateUID() {
		String tableName = getTable().getName();
		EntitySet table = Application.getGenTables().getTable(tableName);

		e1: for (int i = 200000;; i++) {
			for (AbstractEntity item : table.getItems()) {
				Client client = (Client) item;
				if (client.id == i) {
					continue e1;
				}
			}

			id = i;
			break;
		}
	}

	// -----------------------------------------------------------------

	public static String getViewName() {
		return "Клиенты";
	}

	public static String getFieldPresent(Field field) {
		String fieldName = field.getName();
		if ("id".equals(fieldName)) {
			return "Идентификатор";
		} else if ("surname".equals(fieldName)) {
			return "Фамилия";
		} else if ("name".equals(fieldName)) {
			return "Имя";
		} else if ("middlename".equals(fieldName)) {
			return "Отчество";
		} else if ("passp_number".equals(fieldName)) {
			return "Номер паспорта";
		}
		return null;
	}

	public static Field[] getFields() {
		return Client.class.getFields();
	}

	public static Field[] getRequiredFields() {
		return Client.class.getFields();
	}

	public static Field[] getViewableFields() {
		Field[] allFields = Client.class.getDeclaredFields();
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
