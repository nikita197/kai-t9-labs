package org.courseworks.ris.cmanager.session;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.main.AbstractEntity;
import org.courseworks.ris.widgets.viewers.DbTable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public abstract class ExtendedSession extends TableList {

	private Session _session;
	private SessionFactory _factory;
	protected Configuration _configuration;

	public ExtendedSession(Configuration aConfiguration) {
		_configuration = aConfiguration;
		_factory = createFactory();
		_session = _factory.openSession();
	}

	public void fillTables() {
		DbTable cars = new DbTable("Cars");
		DbTable drivers = new DbTable("Drivers");
		List<AbstractEntity> carsList = new ArrayList<AbstractEntity>(), driversList = new ArrayList<AbstractEntity>();

		for (Object obj : _session.createQuery("from Cars").list()) {
			carsList.add((AbstractEntity) obj);
		}
		for (Object obj : _session.createQuery("from Drivers").list()) {
			driversList.add((AbstractEntity) obj);
		}

		cars.addItems(carsList, this);
		cars.addItems(driversList, this);
		getTables().add(cars);
		getTables().add(drivers);
	}

	protected abstract SessionFactory createFactory();

	public Configuration getConfiguration() {
		return _configuration;
	}

	public Session getSession() {
		return _session;
	}
}
