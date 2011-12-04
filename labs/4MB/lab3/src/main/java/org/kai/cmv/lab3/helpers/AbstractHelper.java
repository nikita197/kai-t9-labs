package org.kai.cmv.lab3.helpers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class AbstractHelper extends Composite {
	private Button _helpButton;

	public AbstractHelper(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());

		_helpButton = new Button(this, style);
		_helpButton.setText("?");
		_helpButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.TOP, false,
				false));

		GridData gridData = (GridData) _helpButton.getLayoutData();

		gridData.widthHint = 20;
		gridData.heightHint = 20;

		gridData.verticalIndent = -5;
		gridData.horizontalIndent = -5;

	}
}
