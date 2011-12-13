package org.kai.CMV.lab4.cmanager.session;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.kai.CMV.lab4.cmanager.SessionsManager;

public class ExtendedSession {

	private final List<DbTable> _tables;

	private final SessionFactory _factory;

	protected Session session;

	protected Configuration _configuration;

	public ExtendedSession(Configuration aConfiguration,
			GeneralSession generalSession) {
		_tables = new ArrayList<DbTable>();
		_configuration = aConfiguration;
		_factory = createFactory();
		session = _factory.openSession();

		for (Class<?> entity : SessionsManager.getEntities()) {
			_tables.add(new DbTable(entity, this, generalSession
					.getTable(entity)));
		}
	}

	public void fillTables() {
		for (DbTable table : _tables) {
			table.fillTable(this);
		}
	}

	protected SessionFactory createFactory() {
		Class<?>[] entities = SessionsManager.getEntities();
		String packageName = entities[0].getPackage().getName();

		_configuration.addPackage(packageName);
		for (Class<?> entity : entities) {
			_configuration.addAnnotatedClass(entity);
		}

		return _configuration.buildSessionFactory();
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	public Session getHBSession() {
		return session;
	}

	public List<DbTable> getTables() {
		return _tables;
	}

	public DbTable getEqualTable(EntitySet table) {
		for (DbTable cTable : _tables) {
			if (cTable.getContentType().equals(table.getContentType())) {
				return cTable;
			}
		}
		return null;
	}
}
