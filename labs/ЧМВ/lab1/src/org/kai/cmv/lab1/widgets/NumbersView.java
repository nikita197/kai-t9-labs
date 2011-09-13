package org.kai.cmv.lab1.widgets;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Spinner;
import org.kai.cmv.lab1.widgets.numbersfield.GFChoose;
import org.kai.cmv.lab1.widgets.numbersfield.GFNumber;

public class NumbersView {

	private static final int MAX_NUMBERS_COUNT = 9;

	private static List<Color> _colorsList;
	private static List<String> _cNamesList;

	{
		_colorsList = new ArrayList<Color>();
		_cNamesList = new ArrayList<String>();

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_BLACK));
		_cNamesList.add("COLOR_BLACK");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_BLUE));
		_cNamesList.add("COLOR_BLUE");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_CYAN));
		_cNamesList.add("COLOR_CYAN");

		_colorsList.add(Display.getDefault()
				.getSystemColor(SWT.COLOR_DARK_BLUE));
		_cNamesList.add("COLOR_DARK_BLUE");

		_colorsList.add(Display.getDefault()
				.getSystemColor(SWT.COLOR_DARK_CYAN));
		_cNamesList.add("COLOR_DARK_CYAN");

		_colorsList.add(Display.getDefault()
				.getSystemColor(SWT.COLOR_DARK_GRAY));
		_cNamesList.add("COLOR_DARK_GRAY");

		_colorsList.add(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_GREEN));
		_cNamesList.add("COLOR_DARK_GREEN");

		_colorsList.add(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_MAGENTA));
		_cNamesList.add("COLOR_DARK_MAGENTA");

		_colorsList
				.add(Display.getDefault().getSystemColor(SWT.COLOR_DARK_RED));
		_cNamesList.add("COLOR_DARK_RED");

		_colorsList.add(Display.getDefault().getSystemColor(
				SWT.COLOR_DARK_YELLOW));
		_cNamesList.add("COLOR_DARK_YELLOW");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		_cNamesList.add("COLOR_GRAY");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		_cNamesList.add("COLOR_GREEN");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_RED));
		_cNamesList.add("COLOR_RED");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_MAGENTA));
		_cNamesList.add("COLOR_MAGENTA");
	}

	private Composite _mainComposite;

	private Combo _styleCmb;

	private Combo _colorCmb;

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

		new Label(controlPComposite, SWT.NONE)
				.setText("Выбрите форму представления:");

		_styleCmb = new Combo(controlPComposite, SWT.READ_ONLY);
		_styleCmb.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		_styleCmb.add("Римские цифры");
		_styleCmb.add("Арабские цифры");
		_styleCmb.select(0);

		new Label(controlPComposite, SWT.NONE).setText("Выбрите цвет цифр:");

		_colorCmb = new Combo(controlPComposite, SWT.READ_ONLY);
		_colorCmb.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		for (String colorName : _cNamesList) {
			_colorCmb.add(colorName);
		}
		_colorCmb.select(0);

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
		_numChooser = new GFChoose(_mainComposite, MAX_NUMBERS_COUNT,
				SWT.BORDER);
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
		_startButton.addListener(SWT.MouseUp, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				doTest();
			}

		});

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

		for (int i = 1; i <= MAX_NUMBERS_COUNT; i++) {
			freeNumbers.add(i);
		}

		for (int i = 0; i < MAX_NUMBERS_COUNT; i++) {
			int numberIndex = (int) (Math.random() * (freeNumbers.size()) - 0.1);
			allNumbers.add(new GFNumber(freeNumbers.get(numberIndex),
					((_styleCmb.getSelectionIndex() == 0) ? GFNumber.ROMAN
							: GFNumber.WORD)));
			freeNumbers.remove(numberIndex);
		}

		_numChooser.show(allNumbers,
				_colorsList.get(_colorCmb.getSelectionIndex()), true);

		for (int i = 0; i < _countSP.getSelection(); i++) {
		}
	}
}
