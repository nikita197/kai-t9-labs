package org.courseworks.ris.cmanager.session.concrete;

import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.mappings.hprepair.Cars;
import org.courseworks.ris.mappings.hprepair.Drivers;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ExtendedOrgElQueueSession extends ExtendedSession {

	public ExtendedOrgElQueueSession(Configuration aConfiguration) {
		super(aConfiguration);
	}

	@Override
	protected SessionFactory createFactory() {
		_configuration.addPackage("org.courseworks.ris.mappings.hprepair")
				.addAnnotatedClass(Cars.class).addAnnotatedClass(Drivers.class);
		return _configuration.buildSessionFactory();
	}

}
