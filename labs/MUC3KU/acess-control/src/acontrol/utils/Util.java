package acontrol.utils;

import java.io.IOException;

public class Util {

	public static void clear() {
		try {
			System.in.skip(System.in.available());
		} catch (IOException e) {
			System.out.println("Ошибка очистки ввода командной строки.");
		}
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
			System.out.println("Ошибка чтения командной строки.");
			return null;
		}
	}
}
