package lab2.ui;

import java.io.File;
import java.io.IOException;

import lab2.commands.AbstractIOLayer;
import lab2.commands.ConsoleIOLayer;
import lab2.switcher.Util;

public class ConsoleUI {

    private static final String NEW_LINE_STRING = ">";

    private static final String LS = "ls";

    private static final String RM = "rm";

    private static final String RENAME = "rename";

    private static final String CD = "cd";

    private static final String EXIT = "exit";

    private AbstractIOLayer _ioLayer;

    private boolean _programRunned = true;

    public ConsoleUI() {
        _ioLayer = new ConsoleIOLayer();
    }

    /**
     * Начало работы программы
     */
    public void start() {
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
            if (_ioLayer.getCurrentDirectory().equals(root)) {
                curDirIsRoot = true;
                break;
            }
        }
        if (curDirIsRoot) {
            return _ioLayer.getCurrentDirectory().getPath() + NEW_LINE_STRING;
        }
        return _ioLayer.getCurrentDirectory().getName() + NEW_LINE_STRING;
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
                return _ioLayer.ls() + sep;
            } else
                if (RM.equals(args[0])) {
                    if (args.length != 2) {
                        throw new IllegalArgumentException(
                                "Wrong arguments count");
                    }
                    return _ioLayer.rm(args[1]) + sep;
                } else
                    if (RENAME.equals(args[0])) {
                        if (args.length != 3) {
                            throw new IllegalArgumentException(
                                    "Wrong arguments count");
                        }
                        return _ioLayer.rename(args[1], args[2]) + sep;
                    } else
                        if (CD.equals(args[0])) {
                            if (args.length != 2) {
                                throw new IllegalArgumentException(
                                        "Wrong arguments count");
                            }
                            _ioLayer.cd(args[1]);
                            return "";
                        } else
                            if (EXIT.equals(args[0])) {
                                if (args.length != 1) {
                                    throw new IllegalArgumentException(
                                            "Wrong arguments count");
                                }
                                return exit() + sep;
                            } else
                                if (!"".equals(args[0])) {
                                    throw new IllegalArgumentException(
                                            "Command not supported");
                                }
        }
        return "";
    }
}
