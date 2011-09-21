package lab2.ui;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lab2.switcher.Util;

public class ConsoleUI {

	private static final String NEW_LINE_STRING = ">";
	private static final String LS = "ls";
	private static final String RM = "rm";
	private static final String RENAME = "rename";
	private static final String CD = "cd";

	private File _currentDirectory;

	public void start() {
		_currentDirectory = getRoot(new File(System.getProperty("user.dir")));
		while (true) {
			boolean curDirIsRoot = false;
			for (File root : File.listRoots()) {
				if (_currentDirectory.equals(root)) {
					curDirIsRoot = true;
					break;
				}
			}
			if (curDirIsRoot) {
				System.out
						.print((_currentDirectory.getPath() + NEW_LINE_STRING));
			} else {
				System.out
						.print((_currentDirectory.getName() + NEW_LINE_STRING));
			}

			String command = Util.readln();

			try {
				String[] commandArgs = command.split(" ");
				if (commandArgs.length > 0) {
					if (LS.equals(commandArgs[0])) {
						System.out.print(ls());
					} else if (RM.equals(commandArgs[0])) {
						if (commandArgs.length == 2) {
							System.out.print(rm(commandArgs[1]));
						} else {
							throw new Exception("Wrong arguments count");
						}
					} else if (RENAME.equals(commandArgs[0])) {
						if (commandArgs.length == 3) {
							System.out.print(rename(commandArgs[1],
									commandArgs[2]));
						} else {
							throw new Exception("Wrong arguments count");
						}
					} else if (CD.equals(commandArgs[0])) {
						if (commandArgs.length == 2) {
							cd(commandArgs[1]);
						} else {
							throw new Exception("Wrong arguments count");
						}
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}

	private String ls() {
		StringBuffer result = new StringBuffer();
		for (File f : _currentDirectory.listFiles()) {
			result.append("\t" + f.getName()
					+ System.getProperty("line.separator"));
		}
		return result.toString();
	}

	private String rm(String fileName) throws IOException {
		File file = new File(getAbsFile(fileName));
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found");
		} else {
			if (file.isDirectory()) {
				String absCurDir = _currentDirectory.getPath();
				String removingFPath = file.getPath();
				if (absCurDir.startsWith(removingFPath)) {
					_currentDirectory = file.getParentFile();
				}
			}
			deleteR(file);
		}
		return "File: " + fileName + " removed.";
	}

	private String rename(String fileName, String newName) throws IOException {
		File file = new File(getAbsFile(fileName));
		File newFile = null;
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found");
		} else {
			if (file.isDirectory()) {
				String absCurDir = _currentDirectory.getPath();
				String renamingFPath = file.getPath();
				if (absCurDir.startsWith(renamingFPath)) {

					_currentDirectory = new File(renamingFPath
							+ File.separatorChar
							+ newName
							+ _currentDirectory.getAbsolutePath().substring(
									renamingFPath.length()));
				}
			}
			newFile = new File(file.getParent() + File.separatorChar + newName);
			file.renameTo(newFile);
		}
		return "File " + fileName + " renamed to " + newName + "."
				+ System.getProperty("line.separator");
	}

	private void cd(String fileName) throws IOException {
		File file = new File(getAbsFile(fileName));
		if (!file.exists()) {
			throw new IllegalArgumentException("File not found");
		} else {
			_currentDirectory = file;
		}
	}

	private String getCDName() {
		return _currentDirectory.getName();
	}

	private void deleteR(File file) {
		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				deleteR(f);
			}
		}
		file.delete();
	}

	/**
	 * Возвращает абсолютный файл
	 * 
	 * @param fileName
	 *            Имя файла относительно текущего каталога, либо абсолютный путь
	 *            к файлу
	 * @return Абсолютный путь к файлу
	 * @throws IOException
	 */
	private String getAbsFile(String fileName) throws IOException {
		StringBuffer fileBuffer = new StringBuffer(fileName);

		// removing separators on end
		for (int i = fileName.length() - 1; i >= 0; i--) {
			if (fileName.charAt(i) == File.separatorChar) {
				fileBuffer.deleteCharAt(fileBuffer.length() - 1);
			} else {
				break;
			}
		}

		// patching file path
		if (!fileBuffer.toString().contains(String.valueOf(File.separatorChar))) {
			fileBuffer.insert(0, _currentDirectory.getCanonicalPath()
					+ File.separatorChar);
		}

		File file = new File(fileBuffer.toString());
		return NormalizeFile(file).getAbsolutePath();
		// return new File(file.toURI().normalize()).getAbsolutePath();
	}

	private File getRoot(File file) {
		while (true) {
			file = file.getParentFile();
			for (File root : File.listRoots()) {
				if (root.equals(file)) {
					return file;
				}
			}
		}

	}

	/**
	 * Возвращает файл с нормализованным абсолютным путем
	 * 
	 * @param inputFile
	 *            Файл с абсолютным путем (предполагается что такой файл
	 *            существует)
	 * @return Файл с нормализованным абсолютным путем
	 */
	private File NormalizeFile(File inputFile) {
		List<String> filePathLexems = new ArrayList<String>();

		for (String filePathLexem : inputFile.getPath().split(
				File.separator + File.separator, 0)) {
			if (filePathLexem.equals("..")) {
				if (filePathLexems.size() > 0) { // на всякий случай
					filePathLexems.remove(filePathLexems.size() - 1);
				}
			} else if (!filePathLexem.equals(".")) {
				filePathLexems.add(filePathLexem);
			}

		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < filePathLexems.size(); i++) {
			sb.append(filePathLexems.get(i) + File.separatorChar);
		}

		if (filePathLexems.size() == 0) {
			sb.append(inputFile.getPath().substring(0,
					inputFile.getPath().indexOf(File.separator)));
		}
		return new File(sb.toString());
	}
}
