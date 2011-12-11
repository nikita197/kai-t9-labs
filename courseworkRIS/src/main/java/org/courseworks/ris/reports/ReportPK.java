package org.courseworks.ris.reports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.courseworks.ris.mappings.AbstractEntity;
import org.courseworks.ris.mappings.orgelqueue.Payment;
import org.courseworks.ris.widgets.ExtendedTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

public class ReportPK extends AbstractReport {

    private ExtendedTable _table;

    public ReportPK(ExtendedTable table) {
        super();

        _table = table;

        try {
            name = "Квитанция об оплате";
            params = new HashMap<String, Object>();
            reportPath = new File(".").getCanonicalPath()
                    + "/target/reports/informix/reportPK.jrxml";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public File compile() {
        params.clear();

        AbstractEntity entity = _table.getSelectedItem();
        if ((entity == null) || (!(entity instanceof Payment))
                || (((Payment) entity).cost == null)
                || (((Payment) entity).cost == 0)) {
            MessageBox msgBox = new MessageBox(_table.getShell(), SWT.OK
                    | SWT.ICON_ERROR);
            msgBox.setText("Ошибка");
            msgBox.setMessage("Необходимо выбрать оплаченную запись в таблице 'Платежи'");
            msgBox.open();
            return null;
        } else {
            params.put("payment_id", ((Payment) entity).id);
        }

        return super.compile();
    }
}
