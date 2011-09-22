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

    private static final String EXIT = "exit";

    private boolean _programRunned = true;

    private File _currentDirectory;

    /**
     * Начало работы программы
     */
    public void start() {
        _currentDirectory = getRoot(new File(System.getProperty("user.dir")));
        while (_programRunned) {
            System.out.print(getCurrentDPrefix());
            String command = Util.readln();
            try {
                String[] commandArgs = command.split(" ");
                System.out.print(executeCommand(commandArgs));
            } catch (Exception e) {
                // e.printStackTrace();
                System.out.println(e.getMessage());
            }
        }
    }

    // ----------------------------------- Commands ----------------------------

    private String ls() {
        StringBuffer result = new StringBuffer();
        File[] files = _currentDirectory.listFiles();
        File file;
        // Запись папок
        for (int i = 0; i < files.length; i++) {
            file = files[i];
            if (file.isDirectory()) {
                result.append("\t[D]\t" + file.getName()
                        + System.getProperty("line.separator"));
            }
        }
        // Запись файлов
        for (int i = 0; i < files.length; i++) {
            file = files[i];
            if (!file.isDirectory()) {
                result.append("\t[f]\t" + file.getName()
                        + System.getProperty("line.separator"));
            }
        }

        result.setLength(result.length()
                - System.getProperty("line.separator").length());
        return result.toString();
    }

    private String rm(String fileName) throws IOException {
        File file = new File(getAbsFile(fileName));
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        if (file.isDirectory()) {
            if (file.listFiles().length != 0) {
                System.out
                        .println("Folder is not empty. Remove recursively? [Y/N]");
                String answer = Util.readln();
                if ((answer.length() != 1)
                        || (answer.toLowerCase().charAt(0) != 'y')) {
                    return "File removing cancelled";
                }
            }

            String absCurDir = _currentDirectory.getPath();
            String removingFPath = file.getPath();
            if (absCurDir.startsWith(removingFPath)) {
                _currentDirectory = file.getParentFile();
            }
        }
        delete(file);
        return "File '" + fileName + "' removed.";
    }

    private String rename(String fileName, String newName) throws IOException {
        File file = new File(getAbsFile(fileName));
        File newFile = null;

        // Проверка существования файла
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found");
        }

        // Проверка имени файла
        String os = System.getProperty("os.name");
        String invalidChars;
        if (os.toLowerCase().contains("windows")) {
            invalidChars = "\\/:*?\"<>|";
        } else if (os.toLowerCase().contains("mac")) {
            invalidChars = "/:";
        } else {
            invalidChars = "/";
        }
        for (char c : invalidChars.toCharArray()) {
            if (newName.contains(String.valueOf(c))) {
                throw new IllegalArgumentException("Invalid new file name");
            }
        }

        // Процерка на переименование родительской директории текущей
        // директории (какого либо уровня)
        if (file.isDirectory()) {
            String absCurDir = _currentDirectory.getPath();
            String renamingFPath = file.getPath();
            if (absCurDir.startsWith(renamingFPath)) {
                _currentDirectory =
                        new File(file.getParent()
                                + File.separatorChar
                                + newName
                                + _currentDirectory.getAbsolutePath()
                                        .substring(renamingFPath.length()));
            }
        }

        newFile = new File(file.getParent() + File.separatorChar + newName);
        file.renameTo(newFile);
        return "File '" + fileName + "' renamed to '" + newName + "'.";
    }

    private void cd(String fileName) throws IOException {
        File file = new File(getAbsFile(fileName));
        if (!file.exists()) {
            throw new IllegalArgumentException("File not found");
        }
        _currentDirectory = file;
    }

    private String exit() {
        _programRunned = false;
        return "exit";
    }

    // ----------------------------------- !Commands ---------------------------

    /**
     * Возвращает префикс, расположенный в начале строки
     * 
     * @return "Имя текущей директории"+NEW_LINE_STRING
     */
    private String getCurrentDPrefix() {
        boolean curDirIsRoot = false;
        for (File root : File.listRoots()) {
            if (_currentDirectory.equals(root)) {
                curDirIsRoot = true;
                break;
            }
        }
        if (curDirIsRoot) {
            return _currentDirectory.getPath() + NEW_LINE_STRING;
        }
        return _currentDirectory.getName() + NEW_LINE_STRING;
    }

    /**
     * Рекурсивное удаление
     * 
     * @param file Файл(каталог либо одиночный файл)
     */
    private void delete(File file) {
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                delete(f);
            }
        }
        file.delete();
    }

    /**
     * Возвращает абсолютный файл
     * 
     * @param fileName Имя файла относительно текущего каталога, либо абсолютный
     *            путь к файлу
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

    /**
     * Получение корневой директории файла
     * 
     * @param file Файл
     * @return Корневая директория файла
     */
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
     * @param inputFile Файл с абсолютным путем (предполагается что такой файл
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
                    inputFile.getPath().indexOf(File.separator))
                    + File.separatorChar);
        }
        return new File(sb.toString());
    }

    /**
     * Выполнение команды
     * 
     * @param args Аргументы
     * @throws IllegalArgumentException Ошибка, возникающая при неверном наборе
     *             аттрибутов
     * @throws IOException
     */
    private String executeCommand(String[] args)
            throws IllegalArgumentException, IOException {
        String sep = System.getProperty("line.separator");
        if (args.length > 0) {
            if (LS.equals(args[0])) {
                if (args.length != 1) {
                    throw new IllegalArgumentException("Wrong arguments count");
                }
                return ls() + sep;
            } else if (RM.equals(args[0])) {
                if (args.length != 2) {
                    throw new IllegalArgumentException("Wrong arguments count");
                }
                return rm(args[1]) + sep;
            } else if (RENAME.equals(args[0])) {
                if (args.length != 3) {
                    throw new IllegalArgumentException("Wrong arguments count");
                }
                return rename(args[1], args[2]) + sep;
            } else if (CD.equals(args[0])) {
                if (args.length != 2) {
                    throw new IllegalArgumentException("Wrong arguments count");
                }
                cd(args[1]);
                return "";
            } else if (EXIT.equals(args[0])) {
                if (args.length != 1) {
                    throw new IllegalArgumentException("Wrong arguments count");
                }
                return exit() + sep;
            } else {
                throw new IllegalArgumentException("Command not supported");
            }
        }
        return "";
    }
}
