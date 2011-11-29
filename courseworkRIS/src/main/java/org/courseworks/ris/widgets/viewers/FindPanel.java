package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.widgets.viewers.finders.AbstractFinder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FindPanel extends Composite {
	private Combo _fieldCombo;
	private ExtendedTable _table;
	private AbstractFinder _finder;
	private DbTable _dbTable;
	private Composite _parent;

	public FindPanel(Composite parent, int style, ExtendedTable table) {
		super(parent, style);
		_parent = parent;
		_table = table;
	}

	public void initContent(DbTable dbTable) {
		_dbTable = dbTable;
		_fieldCombo = new Combo(this, SWT.BORDER);
		for (TableColumn tbc : _table.getColumns()) {
			_fieldCombo.add(tbc.getText());
		}

		Button findButton = new Button(this, SWT.NONE);
		findButton.setText("Find");

		_fieldCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				_finder = AbstractFinder.getInstance(FindPanel.this,
						SWT.BORDER,
						_dbTable.getFieldType(_fieldCombo.getText()));
				// _finder.setLayout(new GridLayout());
				_finder.setLayoutData(new GridData(SWT.TOP, SWT.TOP, true, true));
				_parent.layout();
			}
		});
		findButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				Object object = _finder.getSearchValue();
				final int column = _fieldCombo.getSelectionIndex();
				for (TableItem item : _table.getItems()) {
					if (object.toString().equals(item.getText(column))) {
						_table.setSelection(item);
					}
				}
				// _table.getItems()[0].getText(index)
			}
		});
	}
}
