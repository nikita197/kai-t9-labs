package org.kai.cmv.lab3.widgets.views.containers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.kai.cmv.lab3.main.GUIThread;

public class Watch extends AbstractDynamicSplit {
	Text _timeText;

	public Watch(Composite parent, int style) {
		super(parent, AbstractDynamicSplit.MAXIMIZED, SWT.TOP);
	}

	@Override
	public void initContent() {
		Composite mainComposite = getComp();
		GridLayout layout = new GridLayout();

		layout.horizontalSpacing = 1;
		layout.verticalSpacing = 1;
		layout.marginHeight = 2;
		layout.marginWidth = 1;
		mainComposite.setLayout(layout);

		_timeText = new Text(mainComposite, SWT.NONE);
		_timeText.setEnabled(false);
		_timeText
				.setLayoutData(new GridData(SWT.CENTER, SWT.FILL, false, false));
	}

	public void setTime(String time) {
		if (!GUIThread.getShell().isDisposed()) {
			_timeText.setText(time);
		}
	}
}
