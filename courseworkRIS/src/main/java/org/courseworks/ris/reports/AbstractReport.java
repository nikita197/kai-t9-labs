package org.courseworks.ris.reports;

import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.UUID;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.courseworks.ris.cmanager.SessionsManager;

public abstract class AbstractReport {

    private static final String OUTPUT_FOLDER = "/home/aidar/downloads/reports";

    protected String name;
    protected Connection connection;
    protected String reportPath;
    protected HashMap<String, Object> params;

    @SuppressWarnings("deprecation")
    public AbstractReport() {
        this.connection = SessionsManager.getSessions()[0].getHBSession()
                .connection();
    }

    public File compile() {
        try {
            JasperReport jasperReport = JasperCompileManager
                    .compileReport(reportPath);
            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    jasperReport, params, connection);

            File file = new File(OUTPUT_FOLDER + File.separatorChar
                    + UUID.randomUUID() + ".html");
            file.createNewFile();

            JasperExportManager.exportReportToHtmlFile(jasperPrint,
                    file.getAbsolutePath());
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void open(File file) {
        try {
            Runtime.getRuntime().exec(
                    new String[] { "/bin/bash", "-c",
                            "firefox " + file.toURI().toURL().toString() });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

}
