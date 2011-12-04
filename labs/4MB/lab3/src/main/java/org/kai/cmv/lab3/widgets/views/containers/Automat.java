package org.kai.cmv.lab3.widgets.views.containers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import javax.media.CannotRealizeException;
import javax.media.MediaException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Listener;
import org.kai.cmv.lab3.data.GeneralListItem;

public class Automat extends AbstractDynamicSplit {
	private List _automatList;
	private java.util.List<GeneralListItem> _automatItems;
	private Monitor _monitor;
	private Button _deleteButton;

	public Automat(Composite parent, int style, Monitor monitor) {
		super(parent, AbstractDynamicSplit.MINIMIZED, SWT.RIGHT);
		_monitor = monitor;
		_automatItems = new ArrayList<GeneralListItem>();
	}

	public void addAutomatListItem(GeneralListItem newItem) {
		_automatList.add(newItem.getName());
		_automatItems.add(newItem);
		_monitor.layout();
	}

	public void delAutomatListItem(GeneralListItem item) {
		_monitor.disposePlayer();
		int remItemIndex = _automatItems.indexOf(item);
		_automatItems.remove(item);
		_automatList.remove(remItemIndex);
		if (_automatList.getItemCount() > 0) {
			if (remItemIndex > 1) {
				_automatList.setSelection(remItemIndex - 1);
			} else {
				_automatList.setSelection(0);
			}
		}
	}

	public void delAutomatListItem(int index) {
		_monitor.disposePlayer();
		_automatItems.remove(index);
		_automatList.remove(index);
		if (_automatList.getItemCount() > 0) {
			if (index > 1) {
				_automatList.setSelection(index - 1);
			} else {
				_automatList.setSelection(0);
			}
		}
	}

	public boolean containsAutomatList(GeneralListItem item) {
		if (_automatItems.contains(item)) {
			return true;
		} else {
			return false;
		}
	}

	private void addListeners() {
		_automatList.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent arg0) {
			}

			@Override
			public void mouseDown(MouseEvent arg0) {
			}

			@Override
			public void mouseDoubleClick(MouseEvent arg0) {
				try {
					if (_automatList.getItemCount() > 0
							&& _automatList.getSelectionIndex() > -1) {
						_monitor.createPlayer(_automatItems.get(_automatList
								.getSelectionIndex()));
						_monitor.startPlayer();
					}
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (CannotRealizeException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (MediaException e) {
					e.printStackTrace();
				}
			}
		});

		_deleteButton.addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				if (_automatList.getItemCount() > 0) {
					_monitor.disposePlayer();
					int index = _automatList.getSelectionIndex();
					if (index > -1) {
						delAutomatListItem(index);
					}
				}
			}
		});
	}

	@Override
	public void initContent() {
		Composite mainComposite = getComp();
		GridLayout layout = new GridLayout(1, false);
		layout.marginHeight = 0;
		layout.marginWidth = 0;
		mainComposite.setLayout(layout);

		Composite listPanel = new Composite(mainComposite, SWT.BORDER);
		listPanel.setLayout(new GridLayout(1, false));
		((GridLayout) listPanel.getLayout()).marginHeight = -1;
		((GridLayout) listPanel.getLayout()).marginWidth = -1;

		listPanel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		_automatList = new List(listPanel, SWT.NONE);
		_automatList
				.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		Composite buttonsPanel = new Composite(mainComposite, SWT.NONE);
		buttonsPanel.setLayout(new GridLayout(4, false));
		((GridLayout) buttonsPanel.getLayout()).horizontalSpacing = 0;
		buttonsPanel.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, false,
				false));

		_deleteButton = new Button(buttonsPanel, SWT.NONE);
		_deleteButton.setText("Удалить");

		addListeners();
	}
}
