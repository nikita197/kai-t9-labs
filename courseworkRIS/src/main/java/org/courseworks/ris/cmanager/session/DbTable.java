package org.courseworks.ris.cmanager.session;

import java.util.ArrayList;
import java.util.List;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.DatabaseLowlevelProcessor;

public class DbTable {

	private List<AbstractEntity> _items;
	private String _name;

	public DbTable(String name) {
		_items = new ArrayList<AbstractEntity>();
		_name = name;
	}

	public void fillItems(ExtendedSession session) {
		for (AbstractEntity obj : DatabaseLowlevelProcessor.selectFromTable(session,
				_name)) {
			obj.setSessionLink(session);
			_items.add(obj);
		}
	}

	public void addItems(List<AbstractEntity> items) {
		_items.addAll(items);
	}

	public void clear() {
		_items.clear();
	}

	public Class<?> getType() {
		if (_items.size() > 0) {
			return _items.get(0).getClass();
		} else
			return null;
	}

	public String getName() {
		return _name;
	}

	public List<AbstractEntity> getItems() {
		return _items;
	}
}
