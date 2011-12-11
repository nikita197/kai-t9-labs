package lab2.switcher;

import java.io.IOException;

public class Util {

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
