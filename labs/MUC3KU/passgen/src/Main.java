import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        final int lenght = 4;
        System.out.print("Insert count: ");
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
            int count = Integer.valueOf(buffer.toString());
            String[] passws = genPass(lenght, count);

            System.out.println("Passwords:");
            for (int i = 0; i < count; i++) {
                System.out.println(passws[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[] genPass(int length, int count) {
        final char a = 'a';
        final char z = 'z';
        final char n1 = '0';
        final char n9 = '9';
        String[] result = new String[count];
        StringBuffer buffer = new StringBuffer();
        for (int j = 0; j < count; j++) {
            for (int i = 0; i < length; i++) {
                int c = (int) (Math.random() * (z - a + (n9 - n1 + 2))) + a;
                if (c > z) {
                    c = n1 + (c - z) - 1;
                }
                buffer.append((char) c);
            }
            result[j] = buffer.toString();
            buffer.delete(0, buffer.length());
        }

        return result;
    }
}
