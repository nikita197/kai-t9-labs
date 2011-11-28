package org.courseworks.ris.widgets.viewers;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class FilterPanel extends Composite {

	public FilterPanel(Composite parent, int style, Table tb) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		this.setVisible(false);

		initContent(tb);
	}

	private void initContent(Table tb) {
		// TODO выбор поля, задание фильтрации по этому полю в соответствии с
		// его типом.
	}
}
