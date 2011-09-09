package org.kai.cmv.lab1.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Spinner;
import org.kai.cmv.lab1.widgets.numbersfield.GFChoose;
import org.kai.cmv.lab1.widgets.numbersfield.GFNumber;

public class NumbersView {

    private static final int MAX_NUMBERS_COUNT = 9;

    private Composite _mainComposite;

    private Spinner _timeSP;

    private Spinner _countSP;

    private Label _statusLabel;

    private Label _timeLabel;

    private Button _finishButton;

    private Button _startButton;

    private GFChoose _numChooser;

    public NumbersView(Composite composite, int style) {
        createContent(composite);
    }

    private void createContent(Composite composite) {
        _mainComposite = new Composite(composite, SWT.NONE);
        _mainComposite.setLayout(new GridLayout());

        // ---------------------------------------------------------------------
        Composite controlPComposite = new Composite(_mainComposite, SWT.BORDER);
        controlPComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        GridLayout layout0 = new GridLayout();
        layout0.numColumns = 2;
        controlPComposite.setLayout(layout0);

        new Label(controlPComposite, SWT.NONE).setText("Выбрите количество"
                + " чисел:");

        _countSP = new Spinner(controlPComposite, SWT.READ_ONLY);
        _countSP.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
        _countSP.setMaximum(9);
        _countSP.setMinimum(1);
        _countSP.setSelection(9);

        new Label(controlPComposite, SWT.NONE).setText("Выбрите длительность"
                + " показа (в секундах):");

        _timeSP = new Spinner(controlPComposite, SWT.READ_ONLY);
        _timeSP.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
        _timeSP.setMaximum(20);
        _timeSP.setMinimum(1);
        _timeSP.setSelection(5);
        // ---------------------------------------------------------------------
        Composite statusComposite = new Composite(_mainComposite, SWT.BORDER);
        statusComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        GridLayout layout1 = new GridLayout();
        layout1.numColumns = 2;
        statusComposite.setLayout(layout1);

        _statusLabel = new Label(statusComposite, SWT.NONE);
        _statusLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        _statusLabel.setText("Пожалуйста, выберите параметры запуска и "
                + "нажмите старт: ");

        _timeLabel = new Label(statusComposite, SWT.NONE);
        _timeLabel
                .setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false));
        // ---------------------------------------------------------------------
        _numChooser =
                new GFChoose(_mainComposite, MAX_NUMBERS_COUNT, SWT.BORDER);
        _numChooser.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
        // ---------------------------------------------------------------------
        Composite downComposite = new Composite(_mainComposite, SWT.BORDER);
        downComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
                false));
        downComposite.setLayout(new GridLayout());

        _startButton = new Button(downComposite, SWT.PUSH);
        _startButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
                false));
        _startButton.setText("Старт");

        _finishButton = new Button(downComposite, SWT.PUSH);
        _finishButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
                false));
        ((GridData) _finishButton.getLayoutData()).exclude = true;
        _finishButton.setText("Финиш");
        // ---------------------------------------------------------------------
        _mainComposite.layout();
    }

    public void doTest() {
        final int colorMaxRGB = 200;

        // Создание набора общих и выводимых цифр
        List<GFNumber> allNumbers = new ArrayList<GFNumber>();

        List<Integer> freeNumbers = new ArrayList<Integer>();
        for (int i = 0; i < MAX_NUMBERS_COUNT; i++) {
            freeNumbers.add(i);
        }

        for (int i = 0; i < MAX_NUMBERS_COUNT; i++) {
            int number = (int) (Math.random() * (freeNumbers.size() - 1));
            int style = (int) (Math.random());
            Color color =
                    new Color(null, (int) Math.random() * colorMaxRGB,
                            (int) Math.random() * colorMaxRGB,
                            (int) Math.random() * colorMaxRGB);

            allNumbers.add(new GFNumber(freeNumbers.get(number), color,
                    ((style == 0) ? GFNumber.ROMAN : GFNumber.WORD)));
        }

        for (int i = 0; i < _countSP.getSelection(); i++) {
        }
    }
}
