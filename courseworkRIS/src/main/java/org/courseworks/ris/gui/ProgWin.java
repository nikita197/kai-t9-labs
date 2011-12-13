package org.courseworks.ris.gui;

import java.io.File;

import javassist.NotFoundException;

import org.courseworks.ris.cmanager.session.EntitySet;
import org.courseworks.ris.cmanager.session.GeneralSession;
import org.courseworks.ris.reports.AbstractReport;
import org.courseworks.ris.reports.ReportAllAuto;
import org.courseworks.ris.reports.ReportDMonth;
import org.courseworks.ris.reports.ReportPK;
import org.courseworks.ris.reports.ReportSC;
import org.courseworks.ris.rqueries.RQuery;
import org.courseworks.ris.widgets.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

public class ProgWin {

    private Shell _shell;

    private final GeneralSession _generalSession;

    private final Label _tableLabel;

    private final TableViewer _tableViewer;

    private final Menu _menuBar;

    public ProgWin(GeneralSession dbase, Composite composite)
            throws IllegalArgumentException, IllegalAccessException,
            NotFoundException {
        _generalSession = dbase;

        _shell = composite.getShell();

        _tableLabel = new Label(composite, SWT.NONE);
        _tableLabel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
                false));
        _tableLabel
                .setFont(new Font(null, new FontData("Tahoma", 13, SWT.BOLD)));

        _tableViewer = new TableViewer(composite, SWT.NONE);
        _tableViewer
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

        _menuBar = new Menu(composite.getShell(), SWT.BAR);
        MenuItem tables = new MenuItem(_menuBar, SWT.CASCADE);
        tables.setText("&Таблицы");

        Menu tablesMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
        tables.setMenu(tablesMenu);

        // Reports
        MenuItem reports = new MenuItem(_menuBar, SWT.CASCADE);
        reports.setText("&Отчеты");

        Menu reportsMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
        reports.setMenu(reportsMenu);

        Listener reportListener = new Listener() {

            @Override
            public void handleEvent(Event event) {
                MenuItem item = (MenuItem) event.widget;
                AbstractReport report = (AbstractReport) item.getData();
                File file = report.compile();
                if (file != null) {
                    report.open(file);
                }
            }

        };

        MenuItem reportAllAuto = new MenuItem(reportsMenu, SWT.PUSH);
        ReportAllAuto report1 = new ReportAllAuto();
        reportAllAuto.setData(report1);
        reportAllAuto.setText(report1.getName());
        reportAllAuto.addListener(SWT.Selection, reportListener);

        MenuItem reportDMonth = new MenuItem(reportsMenu, SWT.PUSH);
        ReportDMonth report2 = new ReportDMonth();
        reportDMonth.setData(report2);
        reportDMonth.setText(report2.getName());
        reportDMonth.addListener(SWT.Selection, reportListener);

        MenuItem reportPK = new MenuItem(reportsMenu, SWT.PUSH);
        ReportPK report3 = new ReportPK(_tableViewer.getTable());
        reportPK.setData(report3);
        reportPK.setText(report3.getName());
        reportPK.addListener(SWT.Selection, reportListener);

        MenuItem reportSC = new MenuItem(reportsMenu, SWT.PUSH);
        ReportSC report4 = new ReportSC(_tableViewer.getTable());
        reportSC.setData(report4);
        reportSC.setText(report4.getName());
        reportSC.addListener(SWT.Selection, reportListener);

        // RQueries
        MenuItem rqueries = new MenuItem(_menuBar, SWT.CASCADE);
        rqueries.setText("&Распределенный запрос");

        Menu rqueriesMenu = new Menu(composite.getShell(), SWT.DROP_DOWN);
        rqueries.setMenu(rqueriesMenu);

        MenuItem queries = new MenuItem(rqueriesMenu, SWT.PUSH);
        queries.setText("&Распределенный запрос 1");
        queries.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                System.out.println("Rquery");
                new RQuery(_shell).open();
            }

        });

        Listener listener = new Listener() {

            @Override
            public void handleEvent(Event aEvent) {
                MenuItem item = (MenuItem) aEvent.widget;
                EntitySet table = (EntitySet) item.getData();
                try {
                    _tableViewer.fill(table);
                    _tableLabel.setText(table.getViewName());
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };

        for (EntitySet table : _generalSession.getTables()) {
            MenuItem item = new MenuItem(tablesMenu, SWT.RADIO);
            item.setData(table);
            item.setText(table.getViewName());
            item.addListener(SWT.Selection, listener);
        }

        composite.getShell().setMenuBar(_menuBar);

        // Show first table
        Event event = new Event();
        event.widget = tablesMenu.getItem(0);
        listener.handleEvent(event);
        tablesMenu.getItem(0).setSelection(true);
    }
}
