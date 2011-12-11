package org.courseworks.ris.widgets.views.panels.ff;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

/**
 * Абстрактная панель
 */
public abstract class AbstractPanel extends Composite {
	ExtendedTable _visualTable;
	protected EntitySet _dbTable;
	protected String _name;

	public AbstractPanel(PanelsTab panelsTab, String name, int style) {
		super(panelsTab._container, style);

		_name = name;
		panelsTab.addPanel(this);

		init();
	}

	public void setLayoutData(Object layoutData) {
		if (!(layoutData instanceof GridData)) {
			throw new IllegalArgumentException("layout data must be GridData");
		}
		super.setLayoutData(layoutData);
	}

	/**
	 * Установка состояния панели
	 * 
	 * @param state
	 *            Состояние <code>true</code> - видимое состояние,
	 *            <code>false</code> - невидимое состояние
	 */
	public void setVisualState(boolean state) {
		GridData layoutData = (GridData) getLayoutData();
		layoutData.exclude = !state;
		setVisible(state);
	}

	public abstract void init();

	public void initType(EntitySet dbTable) {
		_dbTable = dbTable;
	}

	public String getPanelName() {
		return _name;
	}

}
