package courseworkRIS.main;

import java.util.HashMap;

import org.hibernate.HibernateException;

public class DatabaseConnector {

	public DatabaseConnector(String[] keys, String[] connStrings)
			throws HibernateException {
		if (keys.length != connStrings.length) {
			throw new HibernateException(
					"Incorrect input parameters for servers connection.");
		}

		HashMap<String, String> configs = new HashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			configs.put(keys[i], connStrings[i]);
		}
		HibernateUtil.buildSessionFactories(configs);
	}
}
