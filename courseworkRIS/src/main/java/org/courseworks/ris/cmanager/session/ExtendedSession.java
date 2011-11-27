package org.courseworks.ris.cmanager.session;

import org.courseworks.ris.mappings.DatabaseLowerWorker;
import org.courseworks.ris.mappings.hprepair.Cars;
import org.courseworks.ris.mappings.hprepair.Drivers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ExtendedSession extends TableList {

	public static final int HPREPAIR_SESSION = 1;
	public static final int ORGELQUEUE_SESSION = 2;

	private SessionFactory _factory;
	protected Session session;
	protected Configuration _configuration;
	private int _type;

	public ExtendedSession(Configuration aConfiguration, int type) {
		_configuration = aConfiguration;
		_factory = createFactory();
		session = _factory.openSession();
		_type = type;
	}

	public void refreshTables() {
		tables.clear();
		for (String tableName : DatabaseLowerWorker.getEntities(_type)) {
			getTables().add(new DbTable(tableName));
		}

		for (DbTable table : tables) {
			table.fillItems(this);
		}
	}

	protected SessionFactory createFactory() {
		switch (_type) {
		case HPREPAIR_SESSION: {
			_configuration.addPackage("org.courseworks.ris.mappings.hprepair")
					.addAnnotatedClass(Cars.class)
					.addAnnotatedClass(Drivers.class);
			return _configuration.buildSessionFactory();
		}

		case ORGELQUEUE_SESSION: {
			_configuration.addPackage("org.courseworks.ris.mappings.hprepair")
					.addAnnotatedClass(Cars.class)
					.addAnnotatedClass(Drivers.class);
			return _configuration.buildSessionFactory();
		}
		default:
			return null;
		}
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	public Session getSession() {
		return session;
	}
}
