package org.courseworks.ris.cmanager.session;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.widgets.viewers.DbTable;

public abstract class TableList {
	private List<DbTable> _tables;

	public TableList() {
		_tables = new ArrayList<DbTable>();
	}

	abstract protected void fillTables();

	public List<DbTable> getTables() {
		return _tables;
	}

	public DbTable getTable(String name) {
		for (DbTable table : _tables) {
			if (table.getTableName().equals(name)) {
				return table;
			}
		}
		return null;
	}
}
