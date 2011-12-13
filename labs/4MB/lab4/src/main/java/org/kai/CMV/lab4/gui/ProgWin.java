package org.kai.CMV.lab4.gui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.kai.CMV.lab4.widgets.TableViewer;

public class ProgWin {

	private final Label _tableLabel;

	private final TableViewer _tableViewer;

	private final Menu _menuBar;

	public ProgWin(Composite composite) throws IllegalArgumentException,
			IllegalAccessException {
		_tableLabel = new Label(composite, SWT.NONE);
		_tableLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false));
		_tableLabel
				.setFont(new Font(null, new FontData("Tahoma", 13, SWT.BOLD)));

		_tableViewer = new TableViewer(composite, SWT.NONE);
		_tableViewer
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_menuBar = new Menu(composite.getShell(), SWT.BAR);
		MenuItem tables = new MenuItem(_menuBar, SWT.CASCADE);
		tables.setText("&Склады");

		Menu tablesMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
		tables.setMenu(tablesMenu);

		Listener listener = new Listener() {

			@Override
			public void handleEvent(Event aEvent) {
				_tableViewer.init();
			}
		};

		MenuItem item = new MenuItem(tablesMenu, SWT.RADIO);
		item.setText("Склад №1");
		item.addListener(SWT.Selection, listener);

		composite.getShell().setMenuBar(_menuBar);

		// Show first table
		Event event = new Event();
		event.widget = tablesMenu.getItem(0);
		listener.handleEvent(event);
		tablesMenu.getItem(0).setSelection(true);
	}
}
