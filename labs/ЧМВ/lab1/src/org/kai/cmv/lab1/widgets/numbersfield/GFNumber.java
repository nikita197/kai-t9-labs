package org.kai.cmv.lab1.widgets.numbersfield;

import java.util.HashMap;

public class GFNumber {

	public static final int ROMAN = 1;

	public static final int WORD = 2;

	private static HashMap<String, String> _romanMap;

	private static HashMap<String, String> _wordMap;

	static {
		_romanMap = new HashMap<String, String>();
		_wordMap = new HashMap<String, String>();

		_romanMap.put("0", "");
		_romanMap.put("1", "I");
		_romanMap.put("2", "II");
		_romanMap.put("3", "III");
		_romanMap.put("4", "IV");
		_romanMap.put("5", "V");
		_romanMap.put("6", "VI");
		_romanMap.put("7", "VII");
		_romanMap.put("8", "VIII");
		_romanMap.put("9", "IX");

		_wordMap.put("0", "ноль");
		_wordMap.put("1", "один");
		_wordMap.put("2", "два");
		_wordMap.put("3", "три");
		_wordMap.put("4", "четыре");
		_wordMap.put("5", "пять");
		_wordMap.put("6", "шесть");
		_wordMap.put("7", "семь");
		_wordMap.put("8", "восемь");
		_wordMap.put("9", "девять");
	}

	private final int _number;

	private final int _type;

	/**
	 * Конструктор
	 * 
	 * @param number
	 *            Цифра
	 * 
	 * @param type
	 *            Тип SWT.
	 */
	public GFNumber(int number, int type) {
		if ((number > 9) || (number < 0)) {
			throw new IllegalArgumentException("Число должно быть от 0 до 9");
		}

		_number = number;
		_type = type;
	}

	/**
	 * Возвращает текст для текущей цифры
	 * 
	 * @return Массив, состоящий из текста цифры и его цвета
	 */
	public String getText() {
		return getTextForNumber();
	}

	private String getTextForNumber() {
		HashMap<String, String> symbMap = null;
		switch (_type) {
		default:
		case ROMAN:
			symbMap = _romanMap;
			break;
		case WORD:
			symbMap = _wordMap;
			break;
		}

		return symbMap.get(String.valueOf(_number));
	}
}
