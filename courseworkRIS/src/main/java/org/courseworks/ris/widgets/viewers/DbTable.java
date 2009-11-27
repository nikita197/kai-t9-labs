package org.courseworks.ris.widgets.viewers;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.main.AbstractEntity;

public class DbTable {

	private List<AbstractEntity> _items;
	private String _name;

	public DbTable(String name) {
		_items = new ArrayList<AbstractEntity>();
		_name = name;
	}

	public void addItems(List<AbstractEntity> items, ExtendedSession sessionLink) {
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

	public List<AbstractEntity> getItems() {
		return _items;
	}
}
