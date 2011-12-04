package org.kai.cmv.lab3.data;

import java.util.ArrayList;
import java.util.List;

public class GeneralList {
	private List<GeneralListItem> _gli;

	public GeneralList() {
		_gli = new ArrayList<GeneralListItem>();
	}

	public GeneralList(List<GeneralListItem> gli) {
		_gli = gli;
	}

	public List<GeneralListItem> getItems() {
		return _gli;
	}

	public void addItem(GeneralListItem item) {
		_gli.add(item);
	}

	public void delItem(GeneralListItem item) {
		_gli.remove(item);
	}

	public void clear() {
		_gli.clear();
	}

	public GeneralListItem getItem(int index) {
		return _gli.get(index);
	}

	public boolean contains(GeneralListItem item) {
		for (GeneralListItem existItem : _gli) {
			if (existItem.getName().equals(item.getName())
					&& existItem.getURL().getFile()
							.equals(item.getURL().getFile())) {
				return true;
			}
		}
		return false;
	}

	public int getItemsCount() {
		return _gli.size();
	}

}
