package org.courseworks.ris.gui;

import javassist.NotFoundException;

import org.courseworks.ris.main.Application;
import org.courseworks.ris.widgets.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;


public class ProgWin {

	public ProgWin(Composite composite) throws IllegalArgumentException, IllegalAccessException, NotFoundException {
		new TableViewer(composite, SWT.NONE).fill(Application.getDContainer()
				.getObject("Cars"));

	}

}
