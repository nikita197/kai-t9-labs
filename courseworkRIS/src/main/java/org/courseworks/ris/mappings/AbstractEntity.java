package org.courseworks.ris.mappings;

import org.courseworks.ris.cmanager.session.ExtendedSession;

public abstract class AbstractEntity extends Object {

	private ExtendedSession _session;

	public abstract String getName();

	public ExtendedSession getSession() {
		return _session;
	}

	public void setSession(ExtendedSession session) {
		_session = session;
	}

	public abstract String getFieldPresent(String fieldName);
}
