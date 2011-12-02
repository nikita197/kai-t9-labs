package org.courseworks.ris.mappings;

import java.util.List;

import org.courseworks.ris.cmanager.session.ExtendedSession;

public class DatabaseLowlevelProcessor {

	@SuppressWarnings("unchecked")
	public static List<AbstractEntity> selectFromTable(ExtendedSession session,
			Class<?> entity) {
		return session.getHBSession()
				.createQuery("from " + entity.getSimpleName()).list();
	}

}
