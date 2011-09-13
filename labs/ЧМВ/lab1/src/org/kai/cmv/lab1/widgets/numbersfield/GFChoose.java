package org.kai.cmv.lab1.widgets.numbersfield;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

public class GFChoose {

	private Composite _mainComposite;

	private final List<Label> _labels;

	private final List<Button> _checkButtons;

	public GFChoose(Composite composite, int count, int style) {
		_labels = new ArrayList<Label>();
		_checkButtons = new ArrayList<Button>();

		createContent(composite, count, style);
	}

	private void createContent(Composite composite, int count, int style) {
		_mainComposite = new Composite(composite, style);
		_mainComposite.setLayout(new GridLayout());

		Button button;
		Label label;
		for (int i = 0; i < count; i++) {
			button = new Button(_mainComposite, SWT.CHECK);
			button.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
			button.setVisible(false);
			_checkButtons.add(button);

			label = new Label(_mainComposite, SWT.NONE);
			label.setLayoutData(new GridData(SWT.LEFT, SWT.FILL, false, false));
			_labels.add(label);
		}

		_mainComposite.layout();
	}

	public void show(List<GFNumber> numbers, Color foreground, boolean checking) {
		if (numbers.size() > _labels.size()) {
			throw new IllegalArgumentException(
					"Несоответствие количества чисел");
		}

		Label label;
		GFNumber number;
		Button checkButton;
		for (int i = 0; i < numbers.size(); i++) {
			label = _labels.get(i);
			checkButton = _checkButtons.get(i);
			number = numbers.get(i);

			if (checking) {
				checkButton.setVisible(true);
				label.setVisible(false);
				((GridData) checkButton.getLayoutData()).exclude = false;
				((GridData) label.getLayoutData()).exclude = true;

				checkButton.setForeground(foreground);
				checkButton.setText(number.getText());
			} else {
				label.setVisible(true);
				checkButton.setVisible(false);
				((GridData) label.getLayoutData()).exclude = false;
				((GridData) checkButton.getLayoutData()).exclude = true;

				label.setForeground(foreground);
				label.setText(number.getText());
			}

			checkButton.setSelection(false);
			checkButton.setBackground(null);
		}

		for (int i = numbers.size(); i < _labels.size(); i++) {
			checkButton = _checkButtons.get(i);
			label = _labels.get(i);
			checkButton.setVisible(false);
			label.setVisible(false);
			((GridData) label.getLayoutData()).exclude = true;
			((GridData) checkButton.getLayoutData()).exclude = true;
			checkButton.setSelection(false);
			checkButton.setBackground(null);
			label.setText("");
			checkButton.setText("");
		}

		_mainComposite.layout();
	}

	public boolean[] getSelection() {
		boolean[] result = new boolean[_checkButtons.size()];

		for (int i = 0; i < _checkButtons.size(); i++) {
			Button button = _checkButtons.get(i);
			result[i] = button.getSelection();
		}

		return result;
	}

	public void setWrong(int index) {
		_checkButtons.get(index).setBackground(
				Display.getCurrent().getSystemColor(SWT.COLOR_RED));
	}

	public void setLayoutData(Object layoutData) {
		_mainComposite.setLayoutData(layoutData);
	}

}
