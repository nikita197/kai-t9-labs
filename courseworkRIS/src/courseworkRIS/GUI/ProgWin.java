package courseworkRIS.GUI;

import javassist.NotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import courseworkRIS.main.Application;
import courseworkRIS.sqlDataProvider.Tableviewer;

public class ProgWin {

	public ProgWin(Composite composite) throws IllegalArgumentException, IllegalAccessException, NotFoundException {
		new Tableviewer(composite, SWT.NONE).fill(Application.getDContainer()
				.getObject("Cars"));

	}

}
