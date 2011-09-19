import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

	public static final int OFFSET = 5;

	public static final int AL_SIZE = 256;

	public static void main(String[] args) {
		System.out.println("Select input file: ");
		String inFile = readln();
		System.out.println("Select output file: ");
		String outFile = readln();
		System.out.println("Select operation: E or D");
		String operation = readln();

		try {
			if ("E".equals(operation)) {
				encrypt(inFile, outFile);
			} else if ("D".equals(operation)) {
				decrypt(inFile, outFile);
			} else {
				System.out.println("Wrong complete");
			}

			System.out.println("Operation complete");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void encrypt(String infile, String outfile)
			throws IOException {
		FileInputStream fis = new FileInputStream(infile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		FileOutputStream fos = new FileOutputStream(outfile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		int c;
		while ((c = bis.read()) != -1) {
			bos.write((c + OFFSET) % AL_SIZE);
		}

		bis.close();
		bos.close();
	}

	public static void decrypt(String infile, String outfile)
			throws IOException {
		FileInputStream fis = new FileInputStream(infile);
		BufferedInputStream bis = new BufferedInputStream(fis);

		FileOutputStream fos = new FileOutputStream(outfile);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		int c;
		int x;
		while ((c = bis.read()) != -1) {
			x = c - OFFSET;
			if (x < 0) {
				x += AL_SIZE;
			}
			bos.write(x);
		}

		bis.close();
		bos.close();
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
