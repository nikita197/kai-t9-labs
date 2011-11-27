package org.courseworks.ris.cmanager.session;

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

	protected abstract SessionFactory createFactory();

	public Configuration getConfiguration() {
		return _configuration;
	}

	public Session getSession() {
		return _session;
	}
}
