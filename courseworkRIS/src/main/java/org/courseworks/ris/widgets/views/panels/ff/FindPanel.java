package org.courseworks.ris.widgets.views.panels.ff;

import org.courseworks.ris.cmanager.session.DbTable;
import org.courseworks.ris.main.SC;
import org.courseworks.ris.widgets.typeblocks.finders.AbstractFinder;
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

public class FindPanel extends AbstractPanel {
	private Combo _fieldCombo;
	private Composite _finderPlace;
	private AbstractFinder _finder;

	public FindPanel(PanelsTab panelsView, String name, int style) {
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
		header.setText(SC.SEARCH_PANEL_HEADER);

		_fieldCombo = new Combo(left, SWT.READ_ONLY | SWT.BORDER);
		_fieldCombo.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 2, 1));

		Button findButton = new Button(left, SWT.NONE);
		findButton.setText("Find");

		// separator
		Label separator = new Label(this, SWT.SEPARATOR | SWT.VERTICAL);
		separator.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		// right part
		_finderPlace = new Composite(this, SWT.NONE);
		_finderPlace.setLayout(new FillLayout());
		_finderPlace
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_fieldCombo.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				initFinder();
			}
		});
		findButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				findValue();
			}
		});
	}

	public void initType(DbTable dbTable) {
		super.initType(dbTable);

		_fieldCombo.removeAll();
		for (TableColumn tbc : _visualTable.getColumns()) {
			_fieldCombo.add(tbc.getText());
		}
	}

	public void initFinder() {
		if (_finder != null) {
			_finder.dispose();
		}

		_finder = AbstractFinder.getInstance(_finderPlace, SWT.NONE,
				_dbTable.getFieldClass(_fieldCombo.getSelectionIndex()));
		_finder.getParent().layout();
		_visualTable.getParent().layout();
	}

	public void findValue() {
		Object object = _finder.getSearchValue();
		final int column = _fieldCombo.getSelectionIndex();
		for (TableItem item : _visualTable.getItems()) {
			if (object.toString().equals(item.getText(column))) {
				_visualTable.setSelection(item);
			}
		}
	}

}
