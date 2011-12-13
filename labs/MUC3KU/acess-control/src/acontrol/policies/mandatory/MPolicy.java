package acontrol.policies.mandatory;

import acontrol.policies.AbstractObject;
import acontrol.policies.AbstractSubject;
import acontrol.policies.IPolicy;
import acontrol.policies.Request;
import acontrol.policies.discretionary.DObject;
import acontrol.policies.discretionary.DSubject;

public class MPolicy implements IPolicy {

	public static final int AL_NON_CONF = 0;
	public static final int AL_CONF = 1;
	public static final int AL_SECRET = 2;
	public static final int AL_TOP_SECRET = 3;
	public static final int MAX_AL = 3;

	private MStorage _storage;

	public MPolicy(MStorage storage) {
		_storage = storage;
	}

	@Override
	public boolean getAccess(Request request) {
		MObject object = null;
		MSubject subject = null;

		if (request.getAccessObject() instanceof DObject) {
			object = (MObject) request.getAccessObject();
		}
		if (request.getAccessSubject() instanceof DSubject) {
			subject = (MSubject) request.getAccessSubject();
		}

		switch (request.getAccessType()) {
		case Request.ACCESS_READ: {
			return (subject.getAccessLevel() >= object.getAccessLevel());
		}

		case Request.ACCESS_WRITE: {
			return (subject.getAccessLevel() <= object.getAccessLevel());
		}

		case Request.ACCESS_DELEGATE: {
			throw new IllegalArgumentException("inocorrect acces type");
		}
		default:
			return false;
		}
	}

	public boolean changeAccessLevel(AbstractSubject subject, int accessLevel) {
		if (subject instanceof MSubject) {
			if (((MSubject) subject).getInitAccessLevel() >= accessLevel) {
				((MSubject) subject).setAccessLevel(accessLevel);
				return true;
			}
		}
		return false;
	}

	public MStorage getStorage() {
		return _storage;
	}

	@Deprecated
	@Override
	public boolean delegateAccess(AbstractSubject subject1,
			AbstractSubject subject2, AbstractObject object, int accessType) {
		return false;
	}

	@Override
	public AbstractSubject getSubject(String subject) {
		AbstractSubject[] subjects = _storage.getSubjects();
		for (AbstractSubject cSubject : subjects) {
			if (cSubject.getName().equals(subject)) {
				return cSubject;
			}
		}

		return null;
	}

	@Override
	public AbstractObject getObject(String object) {
		AbstractObject[] objects = _storage.getObjects();
		for (AbstractObject cObject : objects) {
			if (cObject.getName().equals(object)) {
				return cObject;
			}
		}

		return null;
	}

	@Override
	public AbstractSubject[] getSubjects() {
		return _storage.getSubjects();
	}

	@Override
	public AbstractObject[] getObjects() {
		return _storage.getObjects();
	}

	public static String getLevelName(int level) {
		switch (level) {
		case AL_NON_CONF: {
			return "NON_CONFIDENTIAL";
		}
		case AL_CONF: {
			return "CONFIDENTIAL";
		}
		case AL_SECRET: {
			return "SECRET";
		}
		case AL_TOP_SECRET: {
			return "TOP_SECRET";
		}
		}
		return "unlnown";
	}
}
