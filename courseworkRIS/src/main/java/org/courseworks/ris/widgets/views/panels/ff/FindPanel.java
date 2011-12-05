package org.courseworks.ris.widgets.views.panels.ff;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.main.SC;
import org.courseworks.ris.widgets.typeblocks.editors.AbstractFieldEditor;
import org.eclipse.swt.SWT;
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
    private Label _headerLabel;
    private AbstractFieldEditor _finder;

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
                findValue();
            }
        });
    }

    public void initType(EntitySet dbTable) {
        super.initType(dbTable);

        if (_finder != null) {
            _finder.dispose();
            _headerLabel.dispose();
        }

        _fieldCombo.removeAll();
        for (TableColumn tbc : _visualTable.getColumns()) {
            _fieldCombo.add(tbc.getText());
        }
    }

    public void initFinder() {
        if (_finder != null) {
            _finder.dispose();
            _headerLabel.dispose();
        }

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

    public void findValue() {
        Object object = _finder.getValue();
        final int column = _fieldCombo.getSelectionIndex();
        for (TableItem item : _visualTable.getItems()) {
            if (object.toString().equals(item.getText(column))) {
                _visualTable.setSelection(item);
            }
        }
    }

}
