package org.courseworks.ris.main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;

import javassist.NotFoundException;

import org.courseworks.ris.cmanager.ConfigurationsManager;
import org.courseworks.ris.cmanager.ConnectionsManager;
import org.courseworks.ris.cmanager.session.ExtendedSession;
import org.courseworks.ris.cmanager.session.GeneralTableList;
import org.courseworks.ris.gui.ProgWin;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application {
	private static Shell _shell;
	private static GeneralTableList _dB;

	public static void main(String[] args) throws IllegalAccessException {
		try {
			initDB();
			initGUI();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// TODO async computing
		// Tests.classTest();
		// Tests.listTest();
		// Tests.hashMapTest();
	}

	private static void initGUI() throws IllegalArgumentException,
			IllegalAccessException, NotFoundException {
		final Point shellSize = new Point(800, 600);
		Display display = new Display();
		_shell = new Shell(display);
		_shell.setLayout(new GridLayout());
		_shell.setLayoutData(new GridData());
		new ProgWin(_shell);

		_shell.setSize(shellSize);
		_shell.open();
		while (!_shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
	}

	public static void initDB() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			InvocationTargetException, NoSuchMethodException, IOException,
			URISyntaxException {
		try {
			String path = "/mssql_servers/";
			ConnectionsManager.createSessions(ExtendedSession.HPREPAIR_SESSION,
					new ConfigurationsManager(path));
			fillingSessionsTables();
			_dB = new GeneralTableList(ExtendedSession.HPREPAIR_SESSION);
			_dB.refreshTables();
		} finally {
			// TODO close all sessions
		}
	}

	public static void fillingSessionsTables() {
		for (String sessionKey : ConnectionsManager.getSessionsNames()) {
			ExtendedSession currentSession = ConnectionsManager
					.getSession(sessionKey);
			currentSession.refreshTables();
		}
	}

	public static Shell getShell() {
		return _shell;
	}

	public static GeneralTableList getDb() {
		return _dB;
	}
}