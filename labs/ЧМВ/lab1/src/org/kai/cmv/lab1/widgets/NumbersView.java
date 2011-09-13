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
				SWT.COLOR_DARK_YELLOW));
		_cNamesList.add("COLOR_DARK_YELLOW");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_GRAY));
		_cNamesList.add("COLOR_GRAY");

		_colorsList.add(Display.getDefault().getSystemColor(SWT.COLOR_GREEN));
		_cNamesList.add("COLOR_GREEN");

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

	private Thread _showNumbersThread;

	private List<GFNumber> _allNumbers;

	private List<GFNumber> _showNumbers;

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
		_countSP.setMaximum(MAX_NUMBERS_COUNT - 1);
		_countSP.setMinimum(1);
		_countSP.setSelection(MAX_NUMBERS_COUNT / 2);

		new Label(controlPComposite, SWT.NONE).setText("Выбрите длительность"
				+ " показа (в секундах):");

		_timeSP = new Spinner(controlPComposite, SWT.READ_ONLY);
		_timeSP.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, true, false));
		_timeSP.setMaximum(20);
		_timeSP.setMinimum(1);
		_timeSP.setSelection(2);
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
		GridLayout dLayout = new GridLayout();
		dLayout.numColumns = 2;
		downComposite.setLayout(dLayout);

		_startButton = new Button(downComposite, SWT.PUSH);
		_startButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false));
		_startButton.setText("Старт");
		_startButton.addListener(SWT.MouseUp, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				final int style = _styleCmb.getSelectionIndex();
				final Color foreground = _colorsList.get(_colorCmb
						.getSelectionIndex());
				final int showNumbersCount = _countSP.getSelection();
				final int waitTime = _timeSP.getSelection() * 1000;

				// Выключаем компоненты
				setEnabled(false);
				_startButton.setEnabled(false);

				_showNumbersThread = new Thread(new Runnable() {

					@Override
					public void run() {
						showNumbers(style, foreground, showNumbersCount,
								waitTime);
					}

				});
				_showNumbersThread.start();
			}

		});

		_finishButton = new Button(downComposite, SWT.PUSH);
		_finishButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false));
		_finishButton.setEnabled(false);
		_finishButton.setText("Финиш");
		_finishButton.addListener(SWT.MouseUp, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				testUser();
			}
		});
		// ---------------------------------------------------------------------
		_mainComposite.layout();
	}

	public void showNumbers(int style, final Color foreground,
			int showNumbersCount, int waitTime) {
		// Создание набора общих и выводимых цифр
		_allNumbers = new ArrayList<GFNumber>();
		_showNumbers = new ArrayList<GFNumber>();
		final List<Integer> freeNumbers = new ArrayList<Integer>();

		// Генерация порядка общего набора цифр
		for (int i = 1; i <= MAX_NUMBERS_COUNT; i++) {
			freeNumbers.add(i);
		}

		for (int i = 0; i < MAX_NUMBERS_COUNT; i++) {
			int numberIndex = (int) (Math.random() * (freeNumbers.size()) - 0.1);
			_allNumbers.add(new GFNumber(freeNumbers.get(numberIndex),
					((style == 0) ? GFNumber.ROMAN : GFNumber.WORD)));
			freeNumbers.remove(numberIndex);
		}

		// Генерация порядка набора цифр для запоминания
		for (int i = 1; i <= MAX_NUMBERS_COUNT; i++) {
			freeNumbers.add(i);
		}

		for (int i = 0; i < showNumbersCount; i++) {
			int numberIndex = (int) (Math.random() * (freeNumbers.size()) - 0.1);
			_showNumbers.add(new GFNumber(freeNumbers.get(numberIndex),
					((style == 0) ? GFNumber.ROMAN : GFNumber.WORD)));
			freeNumbers.remove(numberIndex);
		}

		// Показ чисел для запоминания
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				if (Display.getDefault().isDisposed()) {
					return;
				} else {
					_numChooser.show(_showNumbers, foreground, false);
				}
			}
		});

		// Ожидание
		try {
			Thread.sleep(waitTime);
		} catch (InterruptedException e) {
			System.out.println("Show numbers thread interrupted");
			return;
		}

		// Показ всех чисел
		Display.getDefault().syncExec(new Runnable() {

			@Override
			public void run() {
				if (Display.getDefault().isDisposed()) {
					return;
				} else {
					_numChooser.show(_allNumbers, foreground, true);
					// Включаем элементы
					setEnabled(true);
					_finishButton.setEnabled(true);

					_statusLabel.setText("Выберите показанные числа "
							+ "и нажмите кнопку 'Финиш':");
				}
			}
		});

	}

	public void testUser() {
		boolean[] selectedChes = _numChooser.getSelection();
		boolean selected;
		boolean contain;

		for (int i = 0; i < selectedChes.length; i++) {
			selected = selectedChes[i];
			contain = false;

			for (GFNumber number : _showNumbers) {
				if (number.getText().equals(_allNumbers.get(i).getText())) {
					contain = true;
					break;
				}
			}

			if ((contain && !selected) || (!contain && selected)) {
				_numChooser.setWrong(i);
			}
		}

		_finishButton.setEnabled(false);
		_startButton.setEnabled(true);

		_statusLabel.setText("Пожалуйста, выберите параметры запуска и "
				+ "нажмите старт: ");
	}

	public void dispose() {
		if ((_showNumbersThread != null) && (_showNumbersThread.isAlive())) {
			_showNumbersThread.interrupt();
		}
	}

	private void setEnabled(boolean enabled) {
		_mainComposite.setEnabled(enabled);
	}
}
