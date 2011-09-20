package lab2.switcher;

import lab2.ui.ConsoleUI;
import lab2.ui.HMenu;
import lab2.ui.SMan;
import lab2.ui.SMenu;

public class Switcher {

    public static final int CONSOLE_TYPE = 1;
    public static final int SMENU_TYPE = 2;
    public static final int HMENU_TYPE = 3;
    public static final int SMAN_TYPE = 4;

    public void start(int type) {
        switch (type) {
        default:
        case CONSOLE_TYPE: {
            ConsoleUI cUI = new ConsoleUI();
            cUI.start();
            break;
        }

        case SMENU_TYPE: {
            SMenu smUI = new SMenu();
            smUI.start();
            break;
        }

        case HMENU_TYPE: {
            HMenu hmUI = new HMenu();
            hmUI.start();
            break;
        }

        case SMAN_TYPE: {
            SMan smnUI = new SMan();
            smnUI.start();
            break;
        }

        }
    }

}
