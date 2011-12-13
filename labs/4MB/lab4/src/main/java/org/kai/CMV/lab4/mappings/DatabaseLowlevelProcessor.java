package org.kai.CMV.lab4.mappings;

import java.util.List;

import org.kai.CMV.lab4.cmanager.session.ExtendedSession;

public class DatabaseLowlevelProcessor {

	@SuppressWarnings("unchecked")
	public static List<AbstractEntity> selectFromTable(ExtendedSession session,
			Class<?> entity) {
		return session.getHBSession()
				.createQuery("from " + entity.getSimpleName()).list();
	}

}
