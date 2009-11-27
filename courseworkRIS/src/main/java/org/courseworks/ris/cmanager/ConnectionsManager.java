package org.courseworks.ris.cmanager;

import java.io.IOException;
import java.util.HashMap;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.cmanager.session.concrete.ExtendedHPRepairSession;
import org.courseworks.ris.cmanager.session.concrete.ExtendedOrgElQueueSession;
import org.hibernate.cfg.Configuration;

public class ConnectionsManager {

	public static final int HPREPAIR_SESSION = 1;
	public static final int ORGELQUEUE_SESSION = 2;

	public static final String SESSION_NAME_KEY = "session.name";

	private static HashMap<String, ExtendedSession> sessions = new HashMap<String, ExtendedSession>();

	public static void createSessions(int type,
			ConfigurationsManager aConfigurationManager) throws IOException {
		for (Configuration configuration : aConfigurationManager
				.getConfigurations()) {
			String name = (String) configuration.getProperties().get(
					SESSION_NAME_KEY);

			switch (type) {
			case HPREPAIR_SESSION: {
				sessions.put(name, new ExtendedHPRepairSession(configuration));
				break;
			}

			case ORGELQUEUE_SESSION: {
				sessions.put(name, new ExtendedOrgElQueueSession(configuration));
				break;
			}
			}
		}
	}

	public static ExtendedSession getSession(String aName) {
		return sessions.get(aName);
	}

	public static String[] getSessions() {
		return sessions.keySet().toArray(new String[0]);
	}

}
