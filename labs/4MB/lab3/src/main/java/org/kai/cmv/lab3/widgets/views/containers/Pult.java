package org.kai.cmv.lab3.widgets.views.containers;

import java.awt.Frame;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

public class Pult extends AbstractDynamicSplit {
	Frame _controlsFrame;
	Button _prevChannelButton;
	Button _nextChannelButton;
	Monitor _monitor;

	public Pult(Composite parent, int style, Monitor monitor) {
		super(parent, AbstractDynamicSplit.MINIMIZED, SWT.BOTTOM);
		_monitor = monitor;
	}

	@Override
	public void initContent() {
		Composite mainComp = getComp();
		GridLayout layout = new GridLayout(3, false);
		layout.marginHeight = 0;
		mainComp.setLayout(layout);
		((GridData) mainComp.getLayoutData()).heightHint = 25;

		Composite controlsFrameComposite = new Composite(mainComp, SWT.EMBEDDED
				| SWT.NO_BACKGROUND);
		controlsFrameComposite.setLayout(new GridLayout(1, false));
		controlsFrameComposite.setLayoutData(new GridData(SWT.LEFT, SWT.FILL,
				false, true));
		((GridData) controlsFrameComposite.getLayoutData()).widthHint = 300;

		_controlsFrame = SWT_AWT.new_Frame(controlsFrameComposite);

		_prevChannelButton = new Button(mainComp, SWT.NONE);
		_prevChannelButton.setText("Пред. канал");
		_nextChannelButton = new Button(mainComp, SWT.NONE);
		_nextChannelButton.setText("След. канал");

		addListeners();
	}

	private void addListeners() {
		_prevChannelButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				_monitor.getAuto().prevChannel(); 
			}});
		_nextChannelButton.addListener(SWT.Selection, new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				_monitor.getAuto().nextChannel();
			}});
	}

	public Frame getControlsFrame() {
		return _controlsFrame;
	}
}
