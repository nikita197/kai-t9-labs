import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Анализ частотности
 */
public class Main {

    private static StringBuffer rusSymbols;

    private static StringBuffer decSymbols;

    static {
        rusSymbols = new StringBuffer();
        decSymbols = new StringBuffer();
        rusSymbols.append(new String(" оеаинтсрвлкмдпуязыбгчйьхёжшюцъщэс"));
    }

    public static void main(String[] args) {
        System.out.println("Select encrypted file:");
        String inFile = readln();

        File f = new File(".");
        System.out.println(f.toURI().normalize().toString());

        System.out.println("Select output file:");
        String outFile = readln();

        try {
            decryptFile(inFile, outFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void decryptFile(String infile, String outFile)
            throws IOException {
        Object[] fr = getFreq(infile);

        // Сортировка
        boolean t = true;
        while (t) {
            t = false;
            for (int i = fr.length - 1; i > 0; i--) {
                if (((Integer) fr[i]) > ((Integer) fr[i - 1])) {
                    char tmpC = decSymbols.charAt(i);
                    decSymbols.setCharAt(i, decSymbols.charAt(i - 1));
                    decSymbols.setCharAt(i - 1, tmpC);

                    int tmpI = (Integer) fr[i];
                    fr[i] = fr[i - 1];
                    fr[i - 1] = tmpI;

                    t = true;
                }
            }
        }

        // Получение выходного файла
        FileInputStream fis = new FileInputStream(infile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        PrintWriter pw = new PrintWriter(outFile);

        int c;
        m: while ((c = bis.read()) != -1) {
            for (int i = 0; i < rusSymbols.length(); i++) {
                if (c == decSymbols.charAt(i)) {
                    pw.write(rusSymbols.charAt(i));
                    System.out.print(rusSymbols.charAt(i));
                    continue m;
                }
            }
            pw.write(c);
        }

        bis.close();

    }

    /**
     * Анализ частотности символов
     * 
     * @param infile Входной файл
     * @return Массив частот для символов строки <code>rusSymbols</code>
     * @throws IOException
     */
    private static Object[] getFreq(String infile) throws IOException {
        List<Integer> resul = new ArrayList<Integer>();

        FileInputStream fis = new FileInputStream(infile);
        BufferedInputStream bis = new BufferedInputStream(fis);

        int c;
        String s;
        m: while ((c = bis.read()) != -1) {
            s = new String(String.valueOf((char) c).toLowerCase().getBytes(),
                    "UTF-8");
            for (int i = 0; i < decSymbols.length(); i++) {
                if (s.charAt(0) == (decSymbols.charAt(i))) {
                    resul.set(i, resul.get(i) + 1);
                    continue m;
                }
            }

            decSymbols.append(s);
            resul.add(0);
        }

        bis.close();
        return resul.toArray();
    }

    private static String readln() {
        try {
            byte[] number = new byte[1024];
            System.in.read(number);
            StringBuffer buffer = new StringBuffer();
            for (byte b : number) {
                if ((b == '\r') || (b == '\n')) {
                    break;
                }
                buffer.append((char) b);
            }
            return buffer.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
