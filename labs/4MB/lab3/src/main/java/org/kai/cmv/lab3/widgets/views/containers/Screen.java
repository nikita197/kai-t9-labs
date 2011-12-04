package org.kai.cmv.lab3.widgets.views.containers;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class Screen extends Composite {
	private Shedule _shedule;
	private Favorites _favorites;
	private Monitor _monitor;
	public final static int SHEDULE = 1, FAVORITES = 2, MONITOR = 3;

	public Screen(Composite parent, int style) {
		super(parent, style);
		GridLayout screenLayout = new GridLayout(1, false);
		screenLayout.marginHeight = -6;
		screenLayout.marginWidth = -6;
		setLayout(screenLayout);

		_shedule = new Shedule(this, SWT.NONE, this);
		_favorites = new Favorites(this, SWT.NONE, this);
		_monitor = new Monitor(this, SWT.NONE);
		setComponentData(_monitor);
		setComponentData(_shedule);
		setComponentData(_favorites);
	}

	private void setComponentData(Composite composite) {
		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.exclude = true;
		composite.setLayoutData(data);
	}

	public void show(int element) {
		switch (element) {
		case SHEDULE:
			_monitor.stopPlayer();
			((GridData) _shedule.getLayoutData()).exclude = false;
			((GridData) _favorites.getLayoutData()).exclude = true;
			((GridData) _monitor.getLayoutData()).exclude = true;
			_shedule.setVisible(true);
			_favorites.setVisible(false);
			_monitor.setVisible(false);
			break;
		case FAVORITES:
			_monitor.stopPlayer();
			((GridData) _shedule.getLayoutData()).exclude = true;
			((GridData) _favorites.getLayoutData()).exclude = false;
			((GridData) _monitor.getLayoutData()).exclude = true;
			_shedule.setVisible(false);
			_favorites.setVisible(true);
			_monitor.setVisible(false);
			break;
		case MONITOR:
			((GridData) _shedule.getLayoutData()).exclude = true;
			((GridData) _favorites.getLayoutData()).exclude = true;
			((GridData) _monitor.getLayoutData()).exclude = false;
			_shedule.setVisible(false);
			_favorites.setVisible(false);
			_monitor.setVisible(true);
			break;
		}
		this.layout();
	}

	public Monitor getScMonitor() {
		return _monitor;
	}

	public Shedule getScShedule() {
		return _shedule;
	}

	public Favorites getScFavorites() {
		return _favorites;
	}
}
