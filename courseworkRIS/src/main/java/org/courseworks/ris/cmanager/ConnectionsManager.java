package org.courseworks.ris.cmanager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

public class ConnectionsManager {

	public static final String SESSION_NAME_KEY = "session.name";

	private static HashMap<String, ExtendedSession> sessions = new HashMap<String, ExtendedSession>();

	public static void createSessions(int type,
			ConfigurationsManager aConfigurationManager) throws IOException {
		for (Configuration configuration : aConfigurationManager
				.getConfigurations()) {
			String name = (String) configuration.getProperties().get(
					SESSION_NAME_KEY);
			sessions.put(name, new ExtendedSession(configuration, type));
		}
	}

	public static ExtendedSession getSession(String aName) {
		return sessions.get(aName);
	}

	public static String[] getSessionsNames() {
		return sessions.keySet().toArray(new String[0]);
	}

	public static String getName(Session session) {
		Iterator<Entry<String, ExtendedSession>> iterator = sessions.entrySet()
				.iterator();
		while (iterator.hasNext()) {
			Entry<String, ExtendedSession> entry = iterator.next();
			if (entry.getValue() == session) {
				return entry.getKey();
			}
		}

		return null;
	}

	public static ExtendedSession[] getSessions() {
		return sessions.values().toArray(new ExtendedSession[0]);
	}

}
