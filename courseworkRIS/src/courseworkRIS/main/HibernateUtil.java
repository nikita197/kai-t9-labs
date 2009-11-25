package courseworkRIS.main;

import java.util.HashMap;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import courseworkRIS.sqlDataProvider.Cars;
import courseworkRIS.sqlDataProvider.Drivers;

public class HibernateUtil {

	private static Log log = LogFactory.getLog(HibernateUtil.class);
	private static HashMap<String, SessionFactory> sessionFactoryMap = new HashMap<String, SessionFactory>();

	public static final ThreadLocal<HashMap<String, Session>> sessionMapsThreadLocal = new ThreadLocal<HashMap<String, Session>>();

	public static Session currentSession(String key) throws HibernateException {

		HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
				.get();

		if (sessionMaps == null) {
			sessionMaps = new HashMap<String, Session>();
			sessionMapsThreadLocal.set(sessionMaps);
		}

		// Open a new Session, if this Thread has none yet
		Session s = (Session) sessionMaps.get(key);
		if (s == null) {
			s = ((SessionFactory) sessionFactoryMap.get(key)).openSession();
			sessionMaps.put(key, s);
		}

		return s;
	}

	public static Session currentSession() throws HibernateException {
		return currentSession("");
	}

	public static void closeSessions() throws HibernateException {
		HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
				.get();
		sessionMapsThreadLocal.set(null);
		if (sessionMaps != null) {
			for (Session session : sessionMaps.values()) {
				if (session.isOpen())
					session.close();
			}
			;
		}
	}

	public static void closeSession() {
		HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
				.get();
		sessionMapsThreadLocal.set(null);
		if (sessionMaps != null) {
			Session session = sessionMaps.get("");
			if (session != null && session.isOpen())
				session.close();
		}
	}

	public static void buildSessionFactories(HashMap<String, String> configs) {
		try {
			// Create the SessionFactory
			for (String key : configs.keySet()) {
				@SuppressWarnings("deprecation")
				SessionFactory sessionFactory = getCfg(configs.get(key))
						.buildSessionFactory();
				sessionFactoryMap.put(key, sessionFactory);
			}

		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);

		} // end of the try - catch block
	}

	public static void buildSessionFactory(String key, String connString) {
		try {
			// Create the SessionFactory
			@SuppressWarnings("deprecation")
			SessionFactory sessionFactory = getCfg(connString)
					.buildSessionFactory();
			sessionFactoryMap.put(key, sessionFactory);

		} catch (Throwable ex) {

			log.error("Initial SessionFactory creation failed.", ex);
			throw new ExceptionInInitializerError(ex);

		} // end of the try - catch block
	}

	public static void closeSession(String key) {
		HashMap<String, Session> sessionMaps = (HashMap<String, Session>) sessionMapsThreadLocal
				.get();
		if (sessionMaps != null) {
			Session session = sessionMaps.get(key);
			if (session != null && session.isOpen())
				session.close();
		}
	}

	private static Configuration getCfg(String connString) {
		return new Configuration()
				.addPackage("sqlDataProvider")
				.addAnnotatedClass(Cars.class)
				.addAnnotatedClass(Drivers.class)
				.setProperty("hibernate.dialect",
						"org.hibernate.dialect.SQLServerDialect")
				.setProperty("hibernate.connection.driver_class",
						"com.microsoft.sqlserver.jdbc.SQLServerDriver")
				.setProperty("hibernate.connection.url",
						"jdbc:sqlserver://" + connString)
				.setProperty("hibernate.connection.username", "deim")
				.setProperty("hibernate.connection.password", "123")
				.setProperty("hibernate.show_sql", "true")
				.setProperty("current_session_context_class", "thread")
				.setProperty("hibernate.hbm2ddl.auto", "update");
	}

	public static String[] getSessionFactoryMapKeys() throws HibernateException {
		return (String[]) sessionFactoryMap.keySet().toArray(new String[0]);
	}

} // end of the class
