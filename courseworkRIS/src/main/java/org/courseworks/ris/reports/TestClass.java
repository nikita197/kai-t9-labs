package org.courseworks.ris.reports;

import java.io.File;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class TestClass {
    public static void main(String[] args) {
        try {
            String path = new File(".").getCanonicalPath()
                    + "/target/reports/informix/testReport.xml";

            JasperReport jasperReport = JasperCompileManager
                    .compileReport(path);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, new HashMap(), new JREmptyDataSource());

            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "reports/hello_report.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
