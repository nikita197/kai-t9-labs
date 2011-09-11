package org.kai.cmv.lab1.widgets.numbersfield;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

public class GFChoose {

    private Composite _mainComposite;

    private List<GFNumber> _numbers;

    private final List<Label> _labels;

    private final List<Button> _checkButtons;

    public GFChoose(Composite composite, int count, int style) {
        _labels = new ArrayList<Label>();
        _checkButtons = new ArrayList<Button>();

        createContent(composite, count, style);
    }

    private void createContent(Composite composite, int count, int style) {
        _mainComposite = new Composite(composite, style);
        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        _mainComposite.setLayout(layout);

        Button button;
        Label label;
        for (int i = 0; i < count; i++) {
            button = new Button(_mainComposite, SWT.CHECK);
            button.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
                    false));
            button.setVisible(false);
            _checkButtons.add(button);

            label = new Label(_mainComposite, SWT.NONE);
            label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false));
            _labels.add(label);
        }

        _mainComposite.layout();
    }

    public void show(List<GFNumber> numbers, boolean checking) {
        if (numbers.size() > _labels.size()) {
            throw new IllegalArgumentException(
                    "Не соответствие количества чисел");
        }

        Label label;
        GFNumber number;
        for (int i = 0; i < numbers.size(); i++) {
            label = _labels.get(i);
            number = _numbers.get(i);

            _checkButtons.get(i).setVisible(checking);

            Object[] text = number.getText();
            label.setForeground((Color) text[1]);
            label.setText((String) text[1]);
        }

        for (int i = numbers.size(); i < _labels.size(); i++) {
            _checkButtons.get(i).setVisible(false);
            _labels.get(i).setText("");
        }
    }

    public boolean[] getSelection() {
        boolean[] result = new boolean[_checkButtons.size()];

        for (int i = 0; i < _checkButtons.size(); i++) {
            Button button = _checkButtons.get(i);
            result[i] = button.getSelection();
        }

        return result;
    }

    public void setLayoutData(Object layoutData) {
        _mainComposite.setLayoutData(layoutData);
    }

}
