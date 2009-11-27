package org.courseworks.ris.main;

import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.cmanager.session.TableList;
import org.courseworks.ris.mappings.hprepair.DatabaseLowerWorker;
import org.courseworks.ris.widgets.viewers.DbTable;

public class GeneralTableList extends TableList {

	@Override
	protected void fillTables() {
		for (String tableName : DatabaseLowerWorker.getEntities()) {
			getTables().add(new DbTable(tableName));
		}

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
