package org.kai.CMV.lab4.main;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Locale;

import javassist.NotFoundException;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.kai.CMV.lab4.cmanager.ConfigurationsManager;
import org.kai.CMV.lab4.cmanager.SessionsManager;
import org.kai.CMV.lab4.cmanager.session.ExtendedSession;
import org.kai.CMV.lab4.cmanager.session.GeneralSession;
import org.kai.CMV.lab4.gui.ProgWin;

public class Application {

	public static final String IMAGE = "/images/gnumeric-splash-screen.png";

	private static Shell _shell;

	private static SplashScreen _splash;

	private static GeneralSession _generalSession;

	public static void main(String[] args) {
		System.out.println("CLIENT LOCALE ----------"
				+ System.getenv("CLIENT_LOCALE"));

		_splash = new SplashScreen();
		_splash.setRunnable(new InfromixDBInitialization());
		_splash.open();

		try {
			initGUI();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void initGUI() throws IllegalArgumentException,
			IllegalAccessException, NotFoundException {
		final Point shellSize = new Point(1000, 600);
		Display display = new Display();

		_shell = new Shell(display);
		_shell.setLayout(new GridLayout());
		_shell.setText(SC.APP_HEADER);

		new ProgWin(_generalSession, _shell);

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

				Locale.setDefault(Locale.getAvailableLocales()[107]);
				_splash.setSelection(3);

				String path = "/informix_servers";

				SessionsManager.initType(SessionsManager.ORGELQUEUE_SESSION);
				SessionsManager.createSessions(new ConfigurationsManager(path));

				_splash.setSelection(7);

				fillingSessionsTables();

				_splash.setSelection(10);

				_generalSession = SessionsManager.getGeneralSession();

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

				String path = "/mssql_servers";
				SessionsManager.initType(SessionsManager.HPREPAIR_SESSION);
				SessionsManager.createSessions(new ConfigurationsManager(path));

				_splash.setSelection(7);

				fillingSessionsTables();

				_splash.setSelection(10);

				_generalSession = SessionsManager.getGeneralSession();

				_splash.setSelection(15);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		}

	}

	public static void fillingSessionsTables() {
		for (String sessionKey : SessionsManager.getSessionsNames()) {
			ExtendedSession currentSession = SessionsManager
					.getSession(sessionKey);
			currentSession.fillTables();
		}
	}

	public static GeneralSession getGenTables() {
		return _generalSession;
	}

}
