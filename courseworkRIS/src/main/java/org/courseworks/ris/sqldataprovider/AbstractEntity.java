package org.courseworks.ris.sqldataprovider;

public class AbstractEntity extends Object {
	private Session _session;

	public Session getSessionLink() {
		return _session;
	}

	public void setSessionLink(Session sessionLink) {
		_session = sessionLink;
	}
}
