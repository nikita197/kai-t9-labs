package org.courseworks.ris.reports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.orgelqueue.Parking;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class ReportSC extends AbstractReport {

    private ExtendedTable _table;

    public ReportSC(ExtendedTable table) {
        super();

        _table = table;

        try {
            name = "Справка о поcтановке на парковку";
            params = new HashMap<String, Object>();
            reportPath = new File(".").getCanonicalPath()
                    + "/target/reports/informix/reportSC.jrxml";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File compile() {
        params.clear();

        AbstractEntity entity = _table.getSelectedItem();
        if ((entity == null) || (!(entity instanceof Parking))) {
            MessageBox msgBox = new MessageBox(_table.getShell(), SWT.OK
                    | SWT.ICON_ERROR);
            msgBox.setText("Ошибка");
            msgBox.setMessage("Необходимо выбрать запись в таблице 'Парковка'");
            msgBox.open();
            return null;
        } else {
            params.put("parking_id", ((Parking) entity).id);
        }

        return super.compile();
    }

}
