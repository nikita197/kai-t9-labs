package org.kai.CMV.lab4.reports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ReportAllAuto extends AbstractReport {

    public ReportAllAuto() {
        super();

        try {
            name = "Отчет о парковочных местах";
            params = new HashMap<String, Object>();
            reportPath = new File(".").getCanonicalPath()
                    + "/target/reports/informix/reportAllAuto.jrxml";
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
