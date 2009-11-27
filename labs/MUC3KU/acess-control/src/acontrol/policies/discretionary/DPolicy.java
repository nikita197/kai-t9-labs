package acontrol.policies.discretionary;

import acontrol.policies.AbstractObject;
import acontrol.policies.AbstractSubject;
import acontrol.policies.IPolicy;
import acontrol.policies.Request;

public class DPolicy implements IPolicy {

	private DStorage _storage;

	public DPolicy(DStorage storage) {
		_storage = storage;
	}

	@Override
	public boolean getAccess(Request request) {
		DObject object = null;
		DSubject subject = null;

		if (request.getAccessObject() instanceof DObject) {
			object = (DObject) request.getAccessObject();
		}
		if (request.getAccessSubject() instanceof DSubject) {
			subject = (DSubject) request.getAccessSubject();
		}

		int accessFlag = _storage.getAcessFlag(object, subject);
		if (accessFlag == -1) {
			throw new IllegalArgumentException("Object or Subject is not exist");
		}

		switch (request.getAccessType()) {
		case Request.ACCESS_READ: {
			return ((accessFlag & 0x04) != 0);
		}

		case Request.ACCESS_WRITE: {
			return ((accessFlag & 0x02) != 0);
		}

		case Request.ACCESS_DELEGATE: {
			return ((accessFlag & 0x01) != 0);
		}
		default:
			return false;
		}
	}

	public int getAcces(DObject object, DSubject subject) {
		return _storage.getAcessFlag(object, subject);
	}

	public DStorage getStorage() {
		return _storage;
	}

	@Deprecated
	@Override
	public boolean changeAccessLevel(AbstractSubject subject, int newAccessLevel) {
		return false;
	}

	@Override
	public boolean delegateAccess(AbstractSubject subject1,
			AbstractSubject subject2, AbstractObject object, int accessType) {
		return _storage.delegate((DSubject) subject1, (DSubject) subject2,
				(DObject) object, accessType);
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
}
