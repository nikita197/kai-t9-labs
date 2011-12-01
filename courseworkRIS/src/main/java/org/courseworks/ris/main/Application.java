package org.courseworks.ris.main;

import java.io.IOException;
import java.net.URISyntaxException;

import javassist.NotFoundException;

import org.courseworks.ris.cmanager.ConfigurationsManager;
import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.cmanager.session.GeneralTableList;
import org.courseworks.ris.gui.ProgWin;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application {

    public static final String IMAGE = "/images/gnumeric-splash-screen.png";

    private static Shell _shell;

    private static SplashScreen _splash;

    private static GeneralTableList _dB;

    public static void main(String[] args) throws IllegalAccessException,
            URISyntaxException, IllegalArgumentException, NotFoundException {
        _splash = new SplashScreen();
        _splash.setRunnable(new InfromixDBInitialization());
        _splash.open();

        initGUI();
    }

    private static void initGUI() throws IllegalArgumentException,
            IllegalAccessException, NotFoundException {
        final Point shellSize = new Point(1000, 600);
        Display display = new Display();
        _shell = new Shell(display);
        _shell.setLayout(new FillLayout());
        new ProgWin(_dB, _shell);

        _shell.setSize(shellSize);
        _shell.open();
        while (!_shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        display.dispose();
    }

    static class InfromixDBInitialization implements Runnable {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            try {
                _splash.setSelection(3);
                String path = "/informix_servers/";
                ConnectionsManager.createSessions(
                        ExtendedSession.ORGELQUEUE_SESSION,
                        new ConfigurationsManager(path));
                _splash.setSelection(7);
                fillingSessionsTables();
                _splash.setSelection(10);
                _dB = new GeneralTableList(ExtendedSession.ORGELQUEUE_SESSION);
                _dB.refreshTables();
                _splash.setSelection(15);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

    }

    static class MSSqlDBInitialization implements Runnable {

        /**
         * {@inheritDoc}
         */
        @Override
        public void run() {
            try {
                _splash.setSelection(3);
                String path = "/mssql_servers/";
                ConnectionsManager.createSessions(
                        ExtendedSession.HPREPAIR_SESSION,
                        new ConfigurationsManager(path));
                _splash.setSelection(7);
                fillingSessionsTables();
                _splash.setSelection(10);
                _dB = new GeneralTableList(ExtendedSession.HPREPAIR_SESSION);
                _dB.refreshTables();
                _splash.setSelection(15);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }

        }

    }

    public static void fillingSessionsTables() {
        for (String sessionKey : ConnectionsManager.getSessionsNames()) {
            ExtendedSession currentSession =
                    ConnectionsManager.getSession(sessionKey);
            currentSession.refreshTables();
        }
    }

}
