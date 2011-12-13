package org.kai.CMV.lab4.cmanager.session;

import java.util.ArrayList;
import java.util.List;

import org.kai.CMV.lab4.cmanager.SessionsManager;

public class GeneralSession {

	protected List<EntitySet> tables;

	public GeneralSession() {
		tables = new ArrayList<EntitySet>();

		for (Class<?> entity : SessionsManager.getEntities()) {
			tables.add(new EntitySet(entity));
		}
	}

	public List<EntitySet> getTables() {
		return tables;
	}

	public EntitySet getTable(String name) {
		for (EntitySet table : tables) {
			if (table.getName().equals(name)) {
				return table;
			}
		}
		return null;
	}

	public EntitySet getTable(Class<?> entity) {
		for (EntitySet table : tables) {
			if (table.getContentType().equals(entity)) {
				return table;
			}
		}
		return null;
	}

}
