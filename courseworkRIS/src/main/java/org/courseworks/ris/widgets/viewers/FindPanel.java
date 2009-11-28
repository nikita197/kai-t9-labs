package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.main.Application;
import org.courseworks.ris.widgets.viewers.finders.AbstractFinder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FindPanel extends Composite {
	private Combo _fieldCombo;
	private Table _table;
	private AbstractFinder _finder;
	private DbTable _dbTable;

	public FindPanel(Composite parent, int style, Table table) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		this.setVisible(false);

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
				_finder = AbstractFinder.getInstance(FindPanel.this, SWT.BORDER,
						_dbTable.getFieldType(_fieldCombo.getText()));
			}
		});
		findButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				Object object = _finder.getSearchValue();
				final int column = _fieldCombo.getSelectionIndex();
				for(TableItem item : _table.getItems()) {
					if (object.toString().equals(item.getText(column))) {
						_table.setSelection(item);
					}
 				}
				//_table.getItems()[0].getText(index)
			}
		});
	}

	private void find(Table tb, String findText) {
		for (TableItem tbI : tb.getItems()) {
			for (int i = 0; i < tb.getColumnCount(); i++) {
				if (tbI.getText(i).equals(findText)) {
					tb.setSelection(tbI);
					tb.setFocus();
					return;
				}
			}
		}
		new MessageBox(Application.getShell()).setMessage("Text not found.");
	}

}
