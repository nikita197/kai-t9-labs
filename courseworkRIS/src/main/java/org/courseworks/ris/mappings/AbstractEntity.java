package org.courseworks.ris.mappings;

import org.courseworks.ris.cmanager.session.ExtendedSession;

public class AbstractEntity extends Object {
	private ExtendedSession _session;

	public ExtendedSession getSessionLink() {
		return _session;
	}

	public void setSessionLink(ExtendedSession sessionLink) {
		_session = sessionLink;
	}
}
