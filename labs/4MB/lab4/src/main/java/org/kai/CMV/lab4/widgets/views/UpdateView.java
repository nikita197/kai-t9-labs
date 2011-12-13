package org.kai.CMV.lab4.widgets.views;

import org.eclipse.swt.widgets.Shell;

public class UpdateView extends AbstractView {

	public UpdateView(Shell shell, String name) {
		super(shell, name);
		_headerLabel.setText("Изменение полей записи:");
	}

	@Override
	protected void okButtonPressed() {
		_actionPerformed = true;
		_shell.close();
	}

	@Override
	protected void cancelButtonPressed() {
		_actionPerformed = false;
		_shell.close();
	}

	@Override
	protected void fillContent() {
	}

}
