package org.courseworks.ris.widgets.viewers;

import org.courseworks.ris.main.Application;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class FindPanel extends Composite {

	public FindPanel(Composite parent, int style, Table tb) {
		super(parent, style);
		this.setLayout(new GridLayout());
		this.setLayoutData(new GridData());
		this.setVisible(false);

		initContent(tb);
	}

	private void initContent(final Table tb) {
		final Text findText = new Text(this, SWT.NONE);
		Button findButton = new Button(this, SWT.NONE);
		findButton.setText("Find");
		findButton.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				find(tb, findText.getText());
			}
		});
	}

	private void find(Table tb, String findText) {
		for (TableItem tbI : tb.getItems()) {
			for (int i = 0; i < tb.getColumnCount(); i++) {
				if (tbI.getText(i).equals(findText)) {
					tb.setSelection(tbI);
					tb.setFocus();
					return;
				}
			}
		}
		new MessageBox(Application.getShell()).setMessage("Text not found.");
	}

}
