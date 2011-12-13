import java.io.IOException;

public class Main {

    static int minCode = 'A';
    static int maxCode = 'Z';

    public static void main(String[] args) {
        String text;

        System.out.print("Input open text: ");
        text = readln();
        while (true) {
            System.out.print("Input key: ");
            String key = readln();
            text = encode(text, key);
            System.out.append(" Cl text: " + text + "\r\n");
        }
    }

    private static String encode(String text, String key) {
        StringBuffer result = new StringBuffer();

        int i = 0;
        for (char c : text.toCharArray()) {
            result.append(decChar(c, key.charAt(i)));
            i++;
            if (i >= key.length()) {
                i = 0;

            }
        }
        return result.toString();
    }

    private static char decChar(char oC, char keyC) {
        char r;
        oC = (char) (oC - minCode);
        keyC = (char) (keyC - minCode);
        r = (char) (oC + keyC);
        r = (char) (r % (maxCode - minCode + 1));
        return (char) (r + minCode);
    }

    public static String readln() {
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
