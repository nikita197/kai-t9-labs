package org.courseworks.ris.reports;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class Reports {

    private static final String REP_FOLDER = "/reports/informix";

    private Shell _parent;
    private Shell _current;
    private Composite _composite;

    private Listener _clickListener;

    private List<File> _reports;
    private Button _close;

    public Reports(Shell shell) {
        _reports = new ArrayList<File>();

        _parent = shell;
        _current = new Shell(_parent);
        _current.setText("Отчеты");
        _current.setLayout(new GridLayout());

        _composite = new Composite(_current, SWT.BORDER);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        _composite.setLayout(layout);

        _close = new Button(_current, SWT.PUSH);
        _close.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
        _close.setText("Cancel");
        _close.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                _current.close();
            }

        });

        _clickListener = new Listener() {

            @Override
            public void handleEvent(Event event) {
                reportShow((File) event.widget.getData());
            }

        };

        try {
            reportsScan(REP_FOLDER);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        _current.pack();
    }

    private void reportsScan(String reportsFolder) throws URISyntaxException {
        File folder = new File(getClass().getResource(reportsFolder).toURI());

        for (File file : folder.listFiles()) {
            _reports.add(file);

            Text text = new Text(_composite, SWT.READ_ONLY | SWT.BORDER);
            text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
            text.setText(file.getName());

            Button button = new Button(_composite, SWT.PUSH);
            button.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
                    false));
            button.setText("Показать");
            button.setData(file);
            button.addListener(SWT.Selection, _clickListener);
        }

    }

    private void reportShow(File report) {
        System.out.println("PIZDTIYU OT4ET : " + report.getAbsolutePath());
    }

    public void open() {
        _current.open();
    }

}
