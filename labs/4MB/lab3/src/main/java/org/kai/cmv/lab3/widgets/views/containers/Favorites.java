package org.kai.cmv.lab3.widgets.views.containers;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.kai.cmv.lab3.data.GeneralListItem;

public class Favorites extends Composite {
	private List _favList;
	private java.util.List<GeneralListItem> _favItems;
	private Button _deleteButton;
	private Button _toAutomat;
	private Screen _screen;

	public Favorites(Composite parent, int style, Screen screen) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		_screen = screen;

		Composite listPanel = new Composite(this, SWT.NONE);
		listPanel.setLayout(new GridLayout(1, false));
		listPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_favItems = new ArrayList<GeneralListItem>();

		_favList = new List(listPanel, SWT.BORDER);
		_favList.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite buttonsPanel = new Composite(this, SWT.NONE);
		buttonsPanel.setLayout(new GridLayout(4, false));
		((GridLayout) buttonsPanel.getLayout()).horizontalSpacing = 0;
		buttonsPanel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false,
				false));

		_deleteButton = new Button(buttonsPanel, SWT.NONE);
		_deleteButton.setText("Удалить");

		_toAutomat = new Button(buttonsPanel, SWT.NONE);
		_toAutomat.setText("В автомат");

		addListeners();
	}

	public void addFavListItem(GeneralListItem newItem) {
		_favList.add(newItem.getName());
		_favItems.add(newItem);
		_favList.setSelection(_favList.getItemCount() - 1);
	}

	public void delFavListItem(GeneralListItem item) {
		int remItemIndex = _favItems.indexOf(item);
		_favItems.remove(item);
		_favList.remove(remItemIndex);
		if (_favList.getItemCount() > 0) {
			if (remItemIndex > 1) {
				_favList.setSelection(remItemIndex - 1);
			} else {
				_favList.setSelection(0);
			}
		}
	}

	public void delFavListItem(int index) {
		_favItems.remove(index);
		_favList.remove(index);
		if (_favList.getItemCount() > 0) {
			if (index > 1) {
				_favList.setSelection(index - 1);
			} else {
				_favList.setSelection(0);
			}
		}
	}

	public boolean containsFavList(GeneralListItem item) {
		if (_favItems.contains(item)) {
			return true;
		} else {
			return false;
		}
	}

	private void addListeners() {
		_toAutomat.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int selectedIndex = _favList.getSelectionIndex();
				if (selectedIndex > -1) {
					Automat automat = _screen.getScMonitor().getAuto();
					GeneralListItem item = _favItems.get(selectedIndex);
					if (!automat.containsAutomatList(item)) {
						automat.addAutomatListItem(item);
					}
				}
			}
		});

		_deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int index = _favList.getSelectionIndex();
				if (index > -1) {
					delFavListItem(index);
				}
			}
		});
	}
}
