package org.courseworks.ris.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.courseworks.ris.sqldataprovider.AbstractEntity;
import org.courseworks.ris.sqldataprovider.DbTable;
import org.hibernate.Session;

import javassist.NotFoundException;

public class DatabaseContainer {
	private DatabaseConnector _dConnector;
	private List<DbTable> _dB;

	public DatabaseContainer(DatabaseConnector dConnector) {
		set_dConnector(dConnector);
		_dB = new ArrayList<DbTable>();
	}

	public void fillDb() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			InvocationTargetException, NoSuchMethodException {
		Session currentSession;
		for (String sessionKey : HibernateUtil.getSessionFactoryMapKeys()) {
			currentSession = HibernateUtil.currentSession(sessionKey);
			List<AbstractEntity> carsList = new ArrayList<AbstractEntity>(), 
					driversList = new ArrayList<AbstractEntity>();
			for (Object obj : currentSession.createQuery("from Cars").list()) {
				carsList.add((AbstractEntity) obj);
			}
			for (Object obj : currentSession.createQuery("from Drivers").list()) {
				driversList.add((AbstractEntity) obj);
			}
			//TODO создание/добавление таблицы в список таблиц
		}
	}

	public DatabaseConnector get_dConnector() {
		return _dConnector;
	}

	public void set_dConnector(DatabaseConnector _dConnector) {
		this._dConnector = _dConnector;
	}

}
