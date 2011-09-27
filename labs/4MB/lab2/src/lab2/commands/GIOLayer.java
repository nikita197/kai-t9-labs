package lab2.commands;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;

public class GIOLayer extends AbstractIOLayer {

    @Override
    public boolean requestRemoving(String fileName) {
        MessageBox msgBox = new MessageBox(Display.getDefault()
                .getActiveShell(), SWT.OK);
        msgBox.setMessage("Folder is not empty. Remove recursively?");
        msgBox.setText("Removing " + fileName);
        return ((msgBox.open() == SWT.OK) ? true : false);
    }

}
