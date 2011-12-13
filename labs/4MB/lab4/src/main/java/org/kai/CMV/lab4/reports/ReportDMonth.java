package org.kai.CMV.lab4.reports;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.kai.CMV.lab4.widgets.typeblocks.editors.DateEditor;

public class ReportDMonth extends AbstractReport {

    protected Calendar date1;
    protected Calendar date2;

    public ReportDMonth() {
        super();

        try {
            name = "Отчет о доходах";
            params = new HashMap<String, Object>();
            reportPath = new File(".").getCanonicalPath()
                    + "/target/reports/informix/reportDMonth.jrxml";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File compile() {
        final Shell current = new Shell(Display.getDefault().getActiveShell());
        current.setText("Выбор периода");
        current.setLayout(new GridLayout(2, false));

        Label label = new Label(current, SWT.None);
        label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
        label.setText("Выберите период:");

        Label label1 = new Label(current, SWT.None);
        label1.setText("С :");

        final DateEditor editor1 = new DateEditor(current, SWT.BORDER, null,
                false);
        editor1.setValue(Calendar.getInstance());

        Label label2 = new Label(current, SWT.None);
        label2.setText("По :");

        final DateEditor editor2 = new DateEditor(current, SWT.BORDER, null,
                false);
        editor2.setValue(Calendar.getInstance());

        Button button = new Button(current, SWT.PUSH);
        button.setText("Печать отчета");
        button.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                date1 = (Calendar) editor1.getValue();
                date2 = (Calendar) editor2.getValue();

                if (date1 == null) {
                    date1 = Calendar.getInstance();
                }

                if (date2 == null) {
                    date2 = Calendar.getInstance();
                }

                current.close();
            }

        });

        current.pack();
        current.open();

        Display display = current.getDisplay();
        while (!current.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }

        params.clear();
        params.put("date1", date1.getTime());
        params.put("date2", date2.getTime());

        File file = super.compile();
        return file;
    }
}
