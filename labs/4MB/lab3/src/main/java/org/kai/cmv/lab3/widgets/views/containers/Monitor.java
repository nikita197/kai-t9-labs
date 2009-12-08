package org.kai.cmv.lab3.widgets.views.containers;

import java.awt.Frame;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.media.CannotRealizeException;
import javax.media.MediaException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.awt.SWT_AWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.kai.cmv.lab3.data.GeneralListItem;
import org.kai.cmv.lab3.data.JMFPlayer;
import org.kai.cmv.lab3.main.GUIThread;

public class Monitor extends Composite {
	private Frame _videoFrame;
	private JMFPlayer _jmfPlayer;
	private Automat _automat;
	private Pult _pult;
	private Composite _videoFrameComposite;
	private Composite _panelOne;
	private Button _powerButton;

	public Monitor(Composite parent, int style) {
		super(parent, style);
		try {
			_jmfPlayer = new JMFPlayer();
			GridLayout monitorLayout = new GridLayout(2, false);
			monitorLayout.horizontalSpacing = -1;
			monitorLayout.verticalSpacing = -1;
			setLayout(monitorLayout);

			panelOneInit();

			_automat = new Automat(this, SWT.BORDER, this);
			_automat.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false,
					true));

			this.pack();
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void panelOneInit() {
		_panelOne = new Composite(this, SWT.BORDER);
		_panelOne.setLayout(new GridLayout(1, false));
		_panelOne.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_videoFrameComposite = new Composite(_panelOne, SWT.EMBEDDED
				| SWT.NO_BACKGROUND);
		_videoFrameComposite.setLayout(new GridLayout(1, false));
		_videoFrameComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				true, true));
		_videoFrameComposite.setBackground(getShell().getDisplay()
				.getSystemColor(SWT.COLOR_BLACK));

		_videoFrame = SWT_AWT.new_Frame(_videoFrameComposite);

		powerButtonInit();
		pultInit();
	}

	private void pultInit() {
		_pult = new Pult(_panelOne, SWT.BORDER, this);
		_pult.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true,
				false));
	}

	private void powerButtonInit() {
		_powerButton = new Button(_panelOne, SWT.NONE);
		GridData powerButtonData = new GridData(SWT.RIGHT, SWT.BOTTOM, true,
				false);
		powerButtonData.widthHint = 40;
		_powerButton.setLayoutData(powerButtonData);
		_powerButton.setText("ВКЛ");

		_powerButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				if (_powerButton.getText().equals("ВКЛ")) {
					_automat.playCurrentChanel();
				} else {
					disposePlayer();
				}
			}
		});
	}

	public void createPlayer(GeneralListItem item)
			throws MalformedURLException, CannotRealizeException, IOException,
			MediaException {
		_jmfPlayer.stop();
		_jmfPlayer.init(_videoFrame, _pult.getControlsFrame(), item);
	}

	public void startPlayer() {
		_jmfPlayer.start();
		_videoFrameComposite.setSize(_videoFrameComposite.getSize().x - 1,
				_videoFrameComposite.getSize().y);
		_videoFrameComposite.setSize(_videoFrameComposite.getSize().x + 1,
				_videoFrameComposite.getSize().y);
		_pult.getControlsFarmeComposite().setSize(_pult.getControlsFarmeComposite().getSize().x - 1,
				_pult.getControlsFarmeComposite().getSize().y);
		_pult.getControlsFarmeComposite().setSize(_pult.getControlsFarmeComposite().getSize().x + 1,
				_pult.getControlsFarmeComposite().getSize().y);
		_powerButton.setText("ВЫКЛ");
	}

	public void stopPlayer() {
		if (_jmfPlayer != null) {
			_jmfPlayer.stop();
			_powerButton.setText("ВКЛ");
		}
	}

	public void disposePlayer() {
		if (_jmfPlayer != null) {
			_jmfPlayer.dispose();
			if (!GUIThread.getShell().isDisposed()) {
				_powerButton.setText("ВКЛ");
			}
		}
	}

	public Automat getAuto() {
		return _automat;
	}
}
