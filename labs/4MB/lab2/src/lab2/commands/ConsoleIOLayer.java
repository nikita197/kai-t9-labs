package lab2.commands;

import lab2.switcher.Util;

public class ConsoleIOLayer extends AbstractIOLayer {

    @Override
    public boolean requestRemoving(String fileName) {
        System.out.println("Folder is not empty. Remove recursively? [Y/N]");
        String answer = Util.readln();
        if ((answer.length() != 1) || (answer.toLowerCase().charAt(0) != 'y')) {
            return false;
        }
        return true;
    }
}
