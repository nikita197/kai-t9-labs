package org.courseworks.ris.widgets.viewers;

import java.lang.reflect.Field;
import java.util.List;

import org.courseworks.ris.mappings.AbstractEntity;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableViewer {

	private Table _tb;
	private Field[] _fields;
	private FindPanel _findP;
	private FilterPanel _filtP;

	public TableViewer(Composite parent, int style) {
		Composite _compt = new Composite(parent, SWT.NONE);
		_compt.setLayout(new GridLayout(2, false));
		tbInit(_compt, style);
		toolsPanelInit(_compt, style);
	}

	public void tbInit(Composite parent, int style) {
		_tb = new Table(parent, style);
		_tb.setLinesVisible(true);
		_tb.setHeaderVisible(true);
		GridData data = new GridData(SWT.FILL, SWT.TOP, true, true);
		data.heightHint = 200;
		_tb.setLayoutData(data);
	}

	public void fill(List<AbstractEntity> list)
			throws IllegalArgumentException, IllegalAccessException {
		createTbColumns(list.get(0).getClass());
		refreshTbFromObjects(list);
		packTb();
	}

	public void createTbColumns(Class<?> cls) {
		_fields = cls.getDeclaredFields();
		tbInit(_tb.getParent(), _tb.getStyle());
		for (Field fld : _fields) {
			TableColumn newColumn = new TableColumn(_tb, SWT.NONE);
			newColumn.setText(fld.getName());
		}
	}

	public void refreshTbFromObjects(List<AbstractEntity> list)
			throws IllegalArgumentException, IllegalAccessException {
		_tb.removeAll();
		for (Object obj : list) {
			TableItem newRow = new TableItem(_tb, SWT.NONE);
			int index = 0;
			for (Field fld : _fields) {
				newRow.setText(index, fld.get(obj).toString());
				index++;
			}
		}
	}

	public void packTb() {
		for (TableColumn tbc : _tb.getColumns()) {
			tbc.pack();
		}
	}

	public void toolsPanelInit(Composite parent, int style) {
		final Composite toolsPanel = new Composite(parent, SWT.NONE);
		toolsPanel.setLayout(new GridLayout(1, false));
		toolsPanel.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true));

		Composite mainPanel = new Composite(toolsPanel, SWT.NONE);
		mainPanel.setLayout(new GridLayout(3, false));

		Button filterButton = new Button(mainPanel, SWT.TOGGLE);
		Button findButton = new Button(mainPanel, SWT.TOGGLE);
		filterButton.setText("Filter");
		findButton.setText("Find");

		_filtP = new FilterPanel(toolsPanel, SWT.NONE, _tb);
		_findP = new FindPanel(toolsPanel, SWT.NONE, _tb);

		filterButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				filterButtonClick();
			}
		});
		findButton.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event arg0) {
				findButtonClick();
			}
		});
	}

	private void findButtonClick() {
		if (true == _findP.getVisible()) {
			_findP.setVisible(false);
		} else {
			GridData filterpGridData = ((GridData) _filtP.getLayoutData());
			GridData findpGridData = ((GridData) _findP.getLayoutData());
			filterpGridData.exclude = true;
			findpGridData.exclude = false;
			_findP.setVisible(true);
			_filtP.setVisible(false);
			// TODO ? toolsPanel.layout();
		}
	}

	private void filterButtonClick() {
		if (true == _filtP.getVisible()) {
			_filtP.setVisible(false);
		} else {
			GridData filterpGridData = ((GridData) _filtP.getLayoutData());
			filterpGridData.exclude = false;
			_filtP.setVisible(true);
			_findP.setVisible(false);
			// TODO ? toolsPanel.layout();
		}
	}
}