package courseworkRIS.main;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import javassist.NotFoundException;

public class DatabaseContainer {
	private HashMap<String, List<Object>> _objects;
	private DatabaseConnector _dConnector;

	public DatabaseContainer(DatabaseConnector dConnector) {
		set_dConnector(dConnector);
		_objects = new HashMap<String, List<Object>>();
	}

	public void fillThis() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			InvocationTargetException, NoSuchMethodException {
		Session currentSession;
		for (String sessionKey : HibernateUtil.getSessionFactoryMapKeys()) {
			currentSession = HibernateUtil.currentSession(sessionKey);
			List<Object> carsList = new ArrayList<Object>(), driversList = new ArrayList<Object>();
			for (Object obj : currentSession.createQuery("from Cars").list()) {
				carsList.add(obj);
			}
			for (Object obj : currentSession.createQuery("from Drivers").list()) {
				driversList.add(obj);
			}
			addDatabaseObject("Cars", carsList);
			addDatabaseObject("Drivers", driversList);
		}
	}

	public void addDatabaseObject(String objName, List<Object> objs)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException, InvocationTargetException,
			NoSuchMethodException {
		if (!_objects.containsKey(objName)) {
			_objects.put(objName, objs);
		} else {
			List<Object> existObjs = _objects.get(objName);
			for (Object obj : objs) {
				if (!existObjs.contains(obj)) {
					for (Object exObj : existObjs) {
						if (exObj
								.getClass()
								.getDeclaredField("id")
								.get(exObj)
								.toString()
								.equals(obj.getClass().getDeclaredField("id")
										.get(obj).toString())) {
							// выдача команды на замену exObj obj`ом - в
							// зависимости от
							// значение поля dtInsert (TODO)
							// пока допустим, что запись obj всегда новее
							// существующая
							int index = existObjs.indexOf(exObj);
							existObjs.remove(exObj);
							existObjs.add(index, obj);
							break;
						}
					}
					existObjs.add(obj);
				}
			}
			_objects.put(objName, existObjs);
		}
	}

	public List<Object> getObject(String objName) throws NotFoundException {
		return _objects.get(objName);
	}

	public DatabaseConnector get_dConnector() {
		return _dConnector;
	}

	public void set_dConnector(DatabaseConnector _dConnector) {
		this._dConnector = _dConnector;
	}

}
