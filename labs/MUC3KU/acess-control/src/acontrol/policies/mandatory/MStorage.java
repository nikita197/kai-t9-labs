package acontrol.policies.mandatory;

import acontrol.policies.AbstractObject;
import acontrol.policies.AbstractSubject;

public class MStorage {

	private AbstractObject[] _objects;

	private AbstractSubject[] _subjects;

	public MStorage() {
		_objects = new MObject[0];
		_subjects = new MSubject[0];
	}

	public void addObject(MObject object) {
		MObject[] newObjects = new MObject[_objects.length + 1];
		System.arraycopy(_objects, 0, newObjects, 0, _objects.length);
		newObjects[_objects.length] = object;
		_objects = newObjects;
	}

	public void addSubject(MSubject object) {
		MSubject[] newObjects = new MSubject[_subjects.length + 1];
		System.arraycopy(_subjects, 0, newObjects, 0, _subjects.length);
		newObjects[_subjects.length] = object;
		_subjects = newObjects;
	}

	public AbstractSubject[] getSubjects() {
		return _subjects;
	}

	public AbstractObject[] getObjects() {
		return _objects;
	}

}
