package org.kai.cmv.lab1.this_package_is_UNNAMED;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kai.cmv.lab1.widgets.NumbersView;
import org.kai.cmv.lab1.widgets.numbersfield.GFNumber;

@XmlRootElement(name = "ЧМВ.ЛАБОРАТОРНАЯ_РАБОТА_N1")
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

    private static final List<XmlMarshaller> INSTANCES_LIST = new ArrayList<XmlMarshaller>();

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

    public void set_chooseStyle(String _chooseStyle) {
        this._chooseStyle = _chooseStyle;
    }

    public void set_allNumbers(List<GFNumber> _allNumbers) {
        StringBuffer sb = new StringBuffer();
        for (GFNumber el : _allNumbers) {
            sb.append(el.getText() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        this._allNumbers = sb.toString();
    }

    public void set_allNumbers(String _allNumbers) {
        this._allNumbers = _allNumbers;
    }

    public void set_showNumbers(List<GFNumber> _showNumbers) {
        StringBuffer sb = new StringBuffer();
        for (GFNumber el : _showNumbers) {
            sb.append(el.getText() + ",");
        }
        sb.deleteCharAt(sb.length() - 1);
        this._showNumbers = sb.toString();
    }

    public void set_showNumbers(String _showNumbers) {
        this._showNumbers = _showNumbers;
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

    public void set_chosedNumbers(String _chosedNumbers) {
        this._chosedNumbers = _chosedNumbers;
    }

    public void set_numbersColor(String _numbersColor) {
        this._numbersColor = _numbersColor;
    }

    public static void appendToDiagData(XmlMarshaller xm) {
        INSTANCES_LIST.add(xm);
    }

    public static void write(File f) throws JAXBException, IOException {
        float percent;
        int errCount;
        File table = new File("outputTables" + File.separatorChar + "Table");
        PrintWriter tableStream = new PrintWriter(table);
        tableStream.write("Style\tColor\tCount\tTime\tErrorPercent\n");
        for (int i = 0; i < INSTANCES_LIST.size(); i++) {
            errCount = 0;
            List<String> showNums = new ArrayList<String>();
            List<String> chosedNums = new ArrayList<String>();
            fillList(chosedNums,
                    INSTANCES_LIST.get(i)._chosedNumbers.split(","));
            fillList(showNums, INSTANCES_LIST.get(i)._showNumbers.split(","));
            for (String Num : showNums) {
                if (!chosedNums.contains(Num)) {
                    errCount++;
                }
            }
            for (String Num : chosedNums) {
                if (!showNums.contains(Num)) {
                    errCount++;
                }
            }
            percent = 1 - (float) (errCount)
                    / ((NumbersView.MAX_NUMBERS_COUNT - showNums.size()) * 2);
            tableStream.write(INSTANCES_LIST.get(i)._chooseStyle + "\t"
                    + INSTANCES_LIST.get(i)._numbersColor + "\t"
                    + INSTANCES_LIST.get(i)._countSP + "\t"
                    + INSTANCES_LIST.get(i)._timeSP + "\t" + percent + "\t\t\t"
                    + errCount + "\n");
        }
        tableStream.close();
    }

    private static void fillList(List<String> list, String[] array) {
        for (String string : array) {
            list.add(string);
        }
    }
}
