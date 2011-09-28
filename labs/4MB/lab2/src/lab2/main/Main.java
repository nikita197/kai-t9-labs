package lab2.main;

import lab2.switcher.Switcher;
import lab2.switcher.Util;

public class Main {

    public static void main(String[] args) {
        System.out.println("Выберите тип интерфейса: ");
        System.out.println("1 - Командный язык(default)");
        System.out.println("2 - Меню");
        System.out.println("3 - Нестандартное меню");
        System.out.println("4 - Прямое манипулирование");
        String sType = Util.readln();

        try {
            int type = Integer.valueOf(sType);
            Switcher switcher = new Switcher();
            switcher.start(type);
        } catch (Exception e) {
            System.out.println("Неправильный ввод");
            System.exit(0);
        }
    }

}
