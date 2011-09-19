package org.kai.cmv.lab1.this_package_is_UNNAMED;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kai.cmv.lab1.widgets.numbersfield.GFNumber;

@XmlRootElement(name = "ЧМВ.ЛАБОРАТОРНАЯ_РАБОТА_№1")
@XmlType(propOrder = { "_chooseStyle", "_allNumbers", "_showNumbers",
		"_chosedNumbers", "_numbersColor", "_timeSP", "_countSP" })
public class XmlMarshaller {
	private String _chooseStyle;
	private String _allNumbers;
	private String _showNumbers;
	private String _chosedNumbers;
	private String _numbersColor;
	private int _timeSP;
	private int _countSP;

	@XmlElement(name = "тип")
	public String get_chooseStyle() {
		return _chooseStyle;
	}

	@XmlElement(name = "все_числа")
	public String get_allNumbers() {
		return _allNumbers;
	}

	@XmlElement(name = "показанные_числа")
	public String get_showNumbers() {
		return _showNumbers;
	}

	@XmlElement(name = "выбранные_числа")
	public String get_chosedNumbers() {
		return _chosedNumbers;
	}

	@XmlElement(name = "цвет_чисел")
	public String get_numbersColor() {
		return _numbersColor;
	}

	@XmlElement(name = "время_показа")
	public int get_timeSP() {
		return _timeSP;
	}

	@XmlElement(name = "количество_чисел")
	public int get_countSP() {
		return _countSP;
	}

	public void set_chooseStyle(int _chooseStyle) {
		if (_chooseStyle == 0)
			this._chooseStyle = "Римские цифры";
		else
			this._chooseStyle = "Словесная форма";
	}

	public void set_allNumbers(List<GFNumber> _allNumbers) {
		StringBuffer sb = new StringBuffer();
		for (GFNumber el : _allNumbers) {
			sb.append(el.getText() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this._allNumbers = sb.toString();
	}

	public void set__showNumbers(List<GFNumber> _showNumbers) {
		StringBuffer sb = new StringBuffer();
		for (GFNumber el : _showNumbers) {
			sb.append(el.getText() + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		this._showNumbers = sb.toString();
	}

	public void set_timeSP(int _timeSP) {
		this._timeSP = _timeSP;
	}

	public void set_countSP(int _countSP) {
		this._countSP = _countSP;
	}

	public void set_chosedNumbers(List<GFNumber> _chosedNumbers) {
		StringBuffer sb = new StringBuffer();
		if (_chosedNumbers.size() > 0) {
			for (GFNumber el : _chosedNumbers) {
				sb.append(el.getText() + ",");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		this._chosedNumbers = sb.toString();
	}

	public void set_numbersColor(String _numbersColor) {
		this._numbersColor = _numbersColor;
	}

}
