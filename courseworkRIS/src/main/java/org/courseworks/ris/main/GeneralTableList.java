package org.courseworks.ris.main;

import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.cmanager.session.TableList;
import org.courseworks.ris.widgets.viewers.DbTable;

public class GeneralTableList extends TableList {

	@Override
	protected void fillTables() {
		DbTable cars = new DbTable("Cars");
		DbTable drivers = new DbTable("Drivers");
		getTables().add(cars);
		getTables().add(drivers);
		
		for (String sessionKey : ConnectionsManager.getSessions()) {
			ExtendedSession currentSession = ConnectionsManager
					.getSession(sessionKey);
			for (DbTable sessionTable : currentSession.getTables()) {
				getTable(sessionTable.getTableName()).addItems(
						sessionTable.getItems(), currentSession);
			}
		}
	}

}
