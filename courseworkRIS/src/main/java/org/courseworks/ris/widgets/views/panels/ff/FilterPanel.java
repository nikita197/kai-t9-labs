package org.courseworks.ris.widgets.views.panels.ff;

import java.lang.reflect.Field;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.SC;
import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.widgets.typeblocks.filters.AbstractFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class FilterPanel extends AbstractPanel {
	private Combo _fieldCombo;
	private Composite _filterPlace;
	private AbstractFilter _filter;
	private Button _filterButton;
	private Button _clearButton;

	private boolean _filterActive;

	public FilterPanel(PanelsTab panelsView, String name, int style) {
		super(panelsView, name, style);
		setLayout(new GridLayout(3, false));
	}

	public void init() {
		// left part
		Composite left = new Composite(this, SWT.NONE);
		GridLayout leftLayout = new GridLayout();
		leftLayout.marginWidth = 0;
		leftLayout.marginHeight = 0;
		leftLayout.numColumns = 2;
		left.setLayout(leftLayout);
		left.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		Label header = new Label(left, SWT.NONE);
		header.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		header.setText(SC.FILTER_PANEL_HEADER);

		_fieldCombo = new Combo(left, SWT.BORDER | SWT.READ_ONLY);
		GridData gridData = new GridData(SWT.LEFT, SWT.CENTER, false, false);
		gridData.horizontalSpan = 2;
		_fieldCombo.setLayoutData(gridData);

		_filterButton = new Button(left, SWT.NONE);
		_filterButton.setText("Filter");

		_clearButton = new Button(left, SWT.NONE);
		_clearButton.setText("Clear filter");
		_clearButton.setEnabled(false);

		// separator
		Label separator = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		// right part
		_filterPlace = new Composite(this, SWT.NONE);
		_filterPlace.setLayout(new FillLayout());
		_filterPlace
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_fieldCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				initFilter();
			}
		});
		_filterButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				try {
					applyFilter();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		});
		_clearButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event event) {
				try {
					removeFilter();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}

		});
	}

	public void initType(EntitySet dbTable) {
		super.initType(dbTable);

		_fieldCombo.removeAll();
		for (TableColumn tbc : _visualTable.getColumns()) {
			_fieldCombo.add(tbc.getText());
		}
	}

	private void initFilter() {
		if (_filter != null) {
			_filter.dispose();
		}

		_filter = AbstractFilter
				.getInstance(_filterPlace, SWT.NONE, _dbTable
						.getViewableFields()[_fieldCombo.getSelectionIndex()]
						.getType());
		_filter.getParent().layout();
		_visualTable.getParent().layout();
	}

	private void applyFilter() throws IllegalAccessException {
		if (_fieldCombo.getSelectionIndex() == -1) {
			return;
		}

		if (_filterActive) {
			removeFilter();
		}

		final int filterColumn = _fieldCombo.getSelectionIndex();
		Field field = (Field) _visualTable.getColumn(filterColumn).getData();

		for (TableItem item : _visualTable.getItems()) {
			AbstractEntity object = (AbstractEntity) item.getData();
			Object fieldValue = field.get(object);
			if (!_filter.checkAgreement(fieldValue)) {
				item.dispose();
			}
		}

		setState(true);
	}

	private void removeFilter() throws IllegalArgumentException,
			IllegalAccessException {
		_visualTable.removeAll();
		_visualTable.fill(_dbTable);

		setState(false);
	}

	/**
	 * true - filtered, false - not filtered
	 * 
	 * @param state
	 */
	private void setState(boolean state) {
		_filterActive = state;
		_clearButton.setEnabled(state);
		_filterButton.setEnabled(!state);
		_fieldCombo.setEnabled(!state);
		if (_filter != null) {
			_filter.setEnabled(!state);
		}
	}

}
