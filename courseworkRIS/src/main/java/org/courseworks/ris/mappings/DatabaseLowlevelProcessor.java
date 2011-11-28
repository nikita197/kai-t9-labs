package org.courseworks.ris.mappings;

import java.util.List;

import org.courseworks.ris.cmanager.session.ExtendedSession;

public class DatabaseLowlevelProcessor {

	public static String CARS = "Cars";

	public static String DRIVERS = "Drivers";

	public static String ENGINE = "Engine";

	public static String PLANE = "Plane";

	@SuppressWarnings("unchecked")
	public static List<AbstractEntity> selectFromTable(ExtendedSession session,
			String tableName) {
		return session.getSession().createQuery("from " + tableName).list();
	}

	public static String[] getEntities(int type) {
		switch (type) {
		case ExtendedSession.HPREPAIR_SESSION: {
			return new String[] { CARS, DRIVERS };
		}

		case ExtendedSession.ORGELQUEUE_SESSION: {
			return new String[] { ENGINE, PLANE };
		}
		default:
			return null;
		}
	}
}
