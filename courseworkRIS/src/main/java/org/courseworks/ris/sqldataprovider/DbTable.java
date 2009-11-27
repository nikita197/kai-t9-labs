package org.courseworks.ris.sqldataprovider;

import java.util.ArrayList;
import java.util.List;

public class DbTable {

	private List<AbstractEntity> _items;
	private String _name;

	public DbTable(String name) {
		_items = new ArrayList<AbstractEntity>();
		_name = name;
	}

	public void addItems(List<AbstractEntity> items, Session sessionLink) {
		if (_items.size() > 0 && items.size() > 0) {
			if (!_items.get(0).getClass().equals(items.get(0).getClass())) {
				throw new IllegalArgumentException();
			}
		}
		for (AbstractEntity item : items) {
			item.setSessionLink(sessionLink);
			_items.add(item);
		}
	}

	public Class<?> getItemsType() {
		if (_items.size() > 0) {
			return _items.get(0).getClass();
		} else
			return null;
	}

	public String getTableName() {
		return _name;
	}
}
