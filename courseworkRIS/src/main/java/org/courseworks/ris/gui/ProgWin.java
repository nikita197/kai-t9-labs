package org.courseworks.ris.gui;

import javassist.NotFoundException;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.cmanager.session.GeneralTableList;
import org.courseworks.ris.widgets.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

public class ProgWin {

	private final GeneralTableList _dbase;

	private final TableViewer _tableViewer;

	private final Menu _menuBar;

	public ProgWin(GeneralTableList dbase, Composite composite)
			throws IllegalArgumentException, IllegalAccessException,
			NotFoundException {
		_dbase = dbase;

		_tableViewer = new TableViewer(composite, SWT.NONE);
		// _tableViewer.fill(_dbase.getTable("Plane"));

		_menuBar = new Menu(composite.getShell(), SWT.BAR);
		MenuItem tables = new MenuItem(_menuBar, SWT.CASCADE);
		tables.setText("&Tables");

		Menu tablesMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
		tables.setMenu(tablesMenu);

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				MenuItem item = (MenuItem) aEvent.widget;
				DbTable table = (DbTable) item.getData();
				try {
					_tableViewer.fill(table);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		};

		for (DbTable table : dbase.getTables()) {
			MenuItem item = new MenuItem(tablesMenu, SWT.PUSH);
			item.setData(table);
			item.setText(table.getName());
			item.addListener(SWT.Selection, listener);
		}

		composite.getShell().setMenuBar(_menuBar);

		// Show first table
		Event event = new Event();
		event.widget = tablesMenu.getItem(0);
		listener.handleEvent(event);
	}
}
