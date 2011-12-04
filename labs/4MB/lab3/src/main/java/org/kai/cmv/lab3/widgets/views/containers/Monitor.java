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
import org.eclipse.swt.widgets.Composite;
import org.kai.cmv.lab3.data.GeneralListItem;
import org.kai.cmv.lab3.data.JMFPlayer;

public class Monitor extends Composite {
	private Frame _videoFrame;
	private JMFPlayer _jmfPlayer;
	private Automat _automat;
	private Pult _pult;
	private Composite _videoFrameComposite;

	public Monitor(Composite parent, int style) {
		super(parent, style);
		try {
			GridLayout monitorLayout = new GridLayout(2, false);
			monitorLayout.horizontalSpacing = -1;
			monitorLayout.verticalSpacing = -1;
			setLayout(monitorLayout);

			Composite panelOne = new Composite(this, SWT.BORDER);
			panelOne.setLayout(new GridLayout(1, false));
			panelOne.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

			_automat = new Automat(this, SWT.BORDER, this);
			_automat.setLayoutData(new GridData(SWT.RIGHT, SWT.FILL, false,
					true));

			_videoFrameComposite = new Composite(panelOne, SWT.EMBEDDED
					| SWT.NO_BACKGROUND);
			_videoFrameComposite.setLayout(new GridLayout(1, false));
			_videoFrameComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
					true, true));
			_videoFrameComposite.setBackground(getShell().getDisplay()
					.getSystemColor(SWT.COLOR_BLACK));

			_videoFrame = SWT_AWT.new_Frame(_videoFrameComposite);

			_jmfPlayer = new JMFPlayer();

			this.pack();
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createPlayer(GeneralListItem item)
			throws MalformedURLException, CannotRealizeException, IOException,
			MediaException {
		_jmfPlayer.stop();
		_jmfPlayer.init(_videoFrame, item);
	}

	public void startPlayer() {
		_jmfPlayer.start();
		_videoFrameComposite.setSize(_videoFrameComposite.getSize().x - 1,
				_videoFrameComposite.getSize().y);
		_videoFrameComposite.setSize(_videoFrameComposite.getSize().x + 1,
				_videoFrameComposite.getSize().y);
	}

	public void stopPlayer() {
		if (_jmfPlayer != null) {
			_jmfPlayer.stop();
		}
	}

	public void disposePlayer() {
		if (_jmfPlayer != null) {
			_jmfPlayer.dispose();
		}
	}

	public Automat getAuto() {
		return _automat;
	}
}
