package org.kai.cmv.lab3.data;

import java.awt.Component;
import java.awt.Frame;

import javax.media.Controller;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.Manager;
import javax.media.Player;
import javax.media.StopAtTimeEvent;

public class JMFPlayer {
	private Player _player;
	private Component _visualComponent;
	private Frame _frame;

	public void init(Frame frame, GeneralListItem item)
			throws java.io.IOException, java.net.MalformedURLException,
			javax.media.MediaException, javax.media.CannotRealizeException {
		_frame = frame;
		_frame.removeAll();
		try {
			_player = Manager.createRealizedPlayer(item.getURL());
			_visualComponent = _player.getVisualComponent();
			frame.add(_visualComponent);
			addListeners();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void start() {
		if (_player != null && _player.getState() != Controller.Started) {
			_player.start();
		}
	}

	public void stop() {
		if (_player != null && _player.getState() == Controller.Started) {
			_player.stop();
		}
	}

	public void dispose() {
		if (_player != null) {
			_player.close();
		}
	}

	public void addListeners() {
		_player.addControllerListener(new ControllerListener() {

			@Override
			public void controllerUpdate(ControllerEvent cEvent) {
				if (cEvent instanceof StopAtTimeEvent) {
					System.out.println("End.");
					// _frame.firePropertyChange(propertyName, oldValue,
					// newValue)
				}
			}
		});
	}
}