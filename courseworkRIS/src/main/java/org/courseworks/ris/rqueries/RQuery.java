package org.courseworks.ris.rqueries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class RQuery {

    public static String R_QUERY_1 = "/queries/query_informix.sql";

    private Shell _parent;
    private Shell _current;

    private Text _text;
    private Button _close;

    public RQuery(Shell shell) {
        _parent = shell;

        _current = new Shell(_parent);
        _current.setText("Распределенный запрос");

        GridLayout layout = new GridLayout();
        _current.setLayout(layout);

        _text = new Text(_current, SWT.READ_ONLY | SWT.MULTI);
        _text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        try {
            loadFrom(R_QUERY_1);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        _close = new Button(_current, SWT.PUSH);
        _close.setText("Cancel");
        _close.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false));
        _close.addListener(SWT.Selection, new Listener() {

            @Override
            public void handleEvent(Event event) {
                _current.close();
            }

        });

        _current.pack();
        _current.setSize(350, _current.getSize().y);
    }

    private void loadFrom(String resource) throws URISyntaxException,
            IOException {
        File file = new File(this.getClass().getResource(resource).toURI());
        BufferedReader a = new BufferedReader(new FileReader(file));

        String line;
        while ((line = a.readLine()) != null) {
            _text.append(line + System.getProperty("line.separator"));
        }
        a.close();
    }

    public void open() {
        _current.open();
        _close.setFocus();
        Display display = _parent.getDisplay();

        while ((!_parent.isDisposed()) && (!_current.isDisposed())) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

}
