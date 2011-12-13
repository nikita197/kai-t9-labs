package org.kai.cmv.lab3.main;

public class Application {

	public static void main(String[] args) throws InterruptedException,
			org.eclipse.swt.SWTException {
		final GUIThread gui = new GUIThread();
		final Thread main = new Thread(gui);
		main.start();

		Thread.sleep(1000);

		while (!GUIThread.getShell().isDisposed()) {
			GUIThread.getShell().getDisplay().syncExec(new Runnable() {
				public void run() {
					gui.setWatch();
				}
			});
			Thread.sleep(1000);
		}
	}
}
