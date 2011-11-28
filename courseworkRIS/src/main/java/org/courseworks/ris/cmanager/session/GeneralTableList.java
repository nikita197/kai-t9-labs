package org.courseworks.ris.cmanager.session;

import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.mappings.DatabaseLowlevelProcessor;

public class GeneralTableList extends TableList {

	private int _type;

	public GeneralTableList(int type) {
		_type = type;
	}

	@Override
	public void refreshTables() {
		tables.clear();
		for (String tableName : DatabaseLowlevelProcessor.getEntities(_type)) {
			tables.add(new DbTable(tableName));
		}

		for (ExtendedSession session : ConnectionsManager.getSessions()) {
			for (DbTable sessionTable : session.getTables()) {
				getTable(sessionTable.getName()).addItems(
						sessionTable.getItems());
			}
		}
	}

}
