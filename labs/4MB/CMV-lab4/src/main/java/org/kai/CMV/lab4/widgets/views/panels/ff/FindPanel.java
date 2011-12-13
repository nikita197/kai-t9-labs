package org.kai.CMV.lab4.widgets.views.panels.ff;

import java.lang.reflect.Field;
import java.util.Calendar;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.TableItem;
import org.kai.CMV.lab4.cmanager.session.EntitySet;
import org.kai.CMV.lab4.main.SC;
import org.kai.CMV.lab4.widgets.typeblocks.editors.AbstractFieldEditor;

public class FindPanel extends AbstractPanel {
	private Combo _fieldCombo;
	private Composite _finderPlace;
	private Label _headerLabel;
	private AbstractFieldEditor _finder;
	private int index;

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
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		_finderPlace.setLayout(layout);
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
				try {
					findValue();
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

		if (_finder != null) {
			_finder.dispose();
			_headerLabel.dispose();
		}

		index = -1;

		_fieldCombo.removeAll();
		for (Field field : dbTable.getViewableFields()) {
			_fieldCombo.add(dbTable.getFieldPresentation(field));
		}
	}

	public void initFinder() {
		if (_finder != null) {
			_finder.dispose();
			_headerLabel.dispose();
		}

		index = -1;

		_headerLabel = new Label(_finderPlace, SWT.NONE);
		_headerLabel
				.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		_headerLabel.setText(SC.SEARCH_HEADER);

		_finder = AbstractFieldEditor.getInstance(_finderPlace, SWT.NONE,
				_dbTable.getViewableFields()[_fieldCombo.getSelectionIndex()],
				AbstractFieldEditor.FIND, false);
		_finder.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		_finder.getParent().layout();
		_visualTable.getParent().layout();
	}

	public void findValue() throws IllegalArgumentException,
			IllegalAccessException {
		if ((_finder != null) && (_finder.getValue() != null)) {
			Object object = _finder.getValue();

			// PATCH for date
			if (_finder.getClass().equals(DateEditor.class)) {
				object = AbstractEntity.formateDate((Calendar) object);
			}
			// --------------

			final int column = _fieldCombo.getSelectionIndex();

			TableItem[] items = _visualTable.getItems();
			if (index < items.length - 1) {
				for (int i = index + 1; i < items.length; i++) {
					TableItem item = items[i];
					if (item.getText(column).contains(object.toString())) {
						_visualTable.setSelection(item);
						index = i;
						return;
					}
				}
			}

			MessageBox msgBox = new MessageBox(_visualTable.getShell(), SWT.OK
					| SWT.ICON_INFORMATION);
			msgBox.setText("Поиск завершен");
			msgBox.setMessage("Поиск завершен");
			msgBox.open();
		}

		index = -1;
	}
}
