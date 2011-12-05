package org.courseworks.ris.widgets;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.widgets.views.panels.actions.ActionsPanel;
import org.courseworks.ris.widgets.views.panels.actions.AddItemAction;
import org.courseworks.ris.widgets.views.panels.actions.DeleteItemAction;
import org.courseworks.ris.widgets.views.panels.actions.UpdateItemAction;
import org.courseworks.ris.widgets.views.panels.ff.FilterPanel;
import org.courseworks.ris.widgets.views.panels.ff.FindPanel;
import org.courseworks.ris.widgets.views.panels.ff.PanelsTab;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class TableViewer {

    private final Composite _container;

    private PanelsTab _panelsTab;

    private ExtendedTable _table;

    private ActionsPanel _actionsPanel;

    public TableViewer(Composite parent, int style) {
        _container = new Composite(parent, style);
        _container.setLayout(new GridLayout(1, false));
        init(_container);
    }

    public void init(Composite parent) {
        _panelsTab = new PanelsTab(parent, SWT.BORDER);
        _panelsTab.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));

        new FilterPanel(_panelsTab, "Фильтрация", SWT.NONE);
        new FindPanel(_panelsTab, "Поиск", SWT.NONE);

        _table = new ExtendedTable(parent, SWT.MULTI | SWT.BORDER);
        _table.setLinesVisible(true);
        _table.setHeaderVisible(true);
        _table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        _actionsPanel = new ActionsPanel(parent, SWT.BORDER);
        _actionsPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        new AddItemAction(this, _table, _actionsPanel, "Добавить", null);
        new UpdateItemAction(this, _table, _actionsPanel, "Изменить", null);
        new DeleteItemAction(this, _table, _actionsPanel, "Удалить", null);

        _panelsTab.setTable(_table);
    }

    public void fill(EntitySet dbTable) throws IllegalArgumentException,
            IllegalAccessException {
        _table.initType(dbTable);
        _panelsTab.initType(dbTable);
        _actionsPanel.setTable(dbTable);

        _table.fill(dbTable);
        _table.pack();
    }

    public void removeFilter() {
        _panelsTab.initType(_table.getTable());
    }

    public void setLayoutData(Object layoutData) {
        _container.setLayoutData(layoutData);
    }

}
