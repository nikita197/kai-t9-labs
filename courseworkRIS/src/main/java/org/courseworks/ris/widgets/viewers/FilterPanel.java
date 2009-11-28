package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.cmanager.session.DbTable;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class FilterPanel extends Composite {
	Table _table;

	public FilterPanel(Composite parent, int style, Table table) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		this.setVisible(false);

		_table = table;
	}

	public void initContent(DbTable dbTable) {
		// TODO выбор поля, задание фильтрации по этому полю в соответствии с
		// его типом.
	}
}
