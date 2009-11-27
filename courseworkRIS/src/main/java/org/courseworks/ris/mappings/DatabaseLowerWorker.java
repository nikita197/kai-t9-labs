package org.courseworks.ris.mappings;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.main.AbstractEntity;
import org.hibernate.Session;

public class DatabaseLowerWorker {
	public static String CARS = "Cars", DRIVERS = "Drivers";

	public static List<AbstractEntity> getEntity(Session session, String table) {
		List<AbstractEntity> forReturn = new ArrayList<AbstractEntity>();
		for (Object obj : session.createQuery("from " + table).list()) {
			forReturn.add((AbstractEntity) obj);
		}
		return forReturn;
	}

	public static List<String> getEntities() {
		List<String> forReturn = new ArrayList<String>();
		forReturn.add("Cars");
		forReturn.add("Drivers");
		return forReturn;
	}
}
