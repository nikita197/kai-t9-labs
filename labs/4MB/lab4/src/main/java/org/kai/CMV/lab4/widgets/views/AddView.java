package org.kai.CMV.lab4.widgets.views;

import org.eclipse.swt.widgets.Shell;

public class AddView extends AbstractView {

	public AddView(Shell shell, String name) throws InstantiationException,
			IllegalAccessException {
		super(shell, name);
		_headerLabel.setText("Заполните поля добавляемого объекта:");
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
	protected void fillContent() throws IllegalAccessException {
		// for (AbstractFieldEditor editor : _fieldEditors) {
		// editor.setValue("");
		// }
	}

}
