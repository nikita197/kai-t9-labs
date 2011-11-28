package org.courseworks.ris.cmanager.session;

import java.util.ArrayList;
import java.util.List;


public abstract class TableList {
	protected List<DbTable> tables;

	public TableList() {
		tables = new ArrayList<DbTable>();
	}

	abstract protected void refreshTables();

	public List<DbTable> getTables() {
		return tables;
	}

	public DbTable getTable(String name) {
		for (DbTable table : tables) {
			if (table.getName().equals(name)) {
				return table;
			}
		}
		return null;
	}
}
