package courseworkRIS.main;

import java.lang.reflect.InvocationTargetException;

import javassist.NotFoundException;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import courseworkRIS.GUI.ProgWin;

public class Application {
	private static DatabaseContainer _dContainer;
	private static Shell _shell;

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
			InvocationTargetException, NoSuchMethodException {
		try {
			String[] connStrings = new String[] {
					"localhost:49172;databaseName=db1OnServ1;"
							+ "instanceName=SQLEXPRESSREAL1;integratedSecurity=true;",
					"localhost:49173;databaseName=db1OnServ2;"
							+ "instanceName=SQLEXPRESSREAL2;integratedSecurity=true;" };
			String[] sessionKeys = new String[] { "49172", "49173" };
			DatabaseConnector dConnector = new DatabaseConnector(sessionKeys,
					connStrings);
			_dContainer = new DatabaseContainer(dConnector);
			_dContainer.fillThis();
		} finally {
			HibernateUtil.closeSessions();
		}
	}

	public static DatabaseContainer getDContainer() {
		return _dContainer;
	}

	public static Shell getShell() {
		return _shell;
	}
}