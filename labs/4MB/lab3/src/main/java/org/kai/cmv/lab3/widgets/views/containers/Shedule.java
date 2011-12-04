package org.kai.cmv.lab3.widgets.views.containers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.kai.cmv.lab3.data.GeneralListItem;
import org.kai.cmv.lab3.main.GUIThread;

public class Shedule extends Composite {
	private List _list;
	private Button _addButton;
	private Button _deleteButton;
	private Button _clearButton;
	private Button _toFavButton;
	private Screen _screen;

	public Shedule(Composite parent, int style, Screen screen) {
		super(parent, style);
		setLayout(new GridLayout(1, false));
		_screen = screen;

		Composite listPanel = new Composite(this, SWT.NONE);
		listPanel.setLayout(new GridLayout(1, false));
		listPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_list = new List(listPanel, SWT.BORDER);
		_list.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		fillList();

		Composite buttonsPanel = new Composite(this, SWT.NONE);
		buttonsPanel.setLayout(new GridLayout(4, false));
		((GridLayout) buttonsPanel.getLayout()).horizontalSpacing = 0;
		buttonsPanel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false,
				false));

		_addButton = new Button(buttonsPanel, SWT.NONE);
		_addButton.setText("Добавить");

		_deleteButton = new Button(buttonsPanel, SWT.NONE);
		_deleteButton.setText("Удалить");

		_toFavButton = new Button(buttonsPanel, SWT.NONE);
		_toFavButton.setText("В избранное");

		addListeners();
	}

	private void addListeners() {
		_addButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				String newItem = GUIThread.getDataProvider().linkingFile();
				if (newItem != null) {
					_list.add(newItem);
					_list.setSelection(_list.getItemCount() - 1);
				}
			}
		});

		_toFavButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				int selectedIndex = _list.getSelectionIndex();
				GeneralListItem gli = GUIThread.getDataProvider().getData()
						.getItem(selectedIndex);
				Favorites fav = _screen.getScFavorites();
				if (!fav.containsFavList(gli)) {
					fav.addFavListItem(gli);
				}
			}
		});

		_deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (_list.getItemCount() > 0) {
					int selectedIndex = _list.getSelectionIndex();
					GeneralListItem gli = GUIThread.getDataProvider().getData()
							.getItem(selectedIndex);
					GUIThread.getDataProvider().unlinkingFile(gli);
					_list.remove(selectedIndex);

					Favorites fav = _screen.getScFavorites();
					Automat automat = _screen.getScMonitor().getAuto();
					if (fav.containsFavList(gli)) {
						fav.delFavListItem(gli);
					}
					if (automat.containsAutomatList(gli)) {
						automat.delAutomatListItem(gli);
					}
				}
			}
		});
	}

	private void fillList() {
		_list.removeAll();
		for (GeneralListItem item : GUIThread.getDataProvider().getData()
				.getItems()) {
			_list.add(item.getName());
		}
		if (_list.getItemCount() > 0) {
			_list.setSelection(0);
		}
	}
}
