package acontrol.policies.discretionary;

import acontrol.policies.AbstractObject;
import acontrol.policies.AbstractSubject;
import acontrol.policies.Request;

public class DStorage {

	private AbstractObject[] _objects;
	private AbstractSubject[] _subjects;
	private int[][] _accessRights;

	public DStorage() {
		_objects = new DObject[0];
		_subjects = new DSubject[0];
		_accessRights = new int[0][0];
	}

	public boolean delegate(DSubject subject1, DSubject subject2,
			DObject object, int type) {
		int accessFlag1 = getAcessFlag((DObject) object, (DSubject) subject1);
		int accessFlag2 = getAcessFlag((DObject) object, (DSubject) subject2);

		int objectIndex = indexOf(_objects, object);
		int subjectIndex2 = indexOf(_subjects, subject2);

		switch (type) {
		case Request.ACCESS_READ: {
			if ((accessFlag1 & 0x04) != 0) {
				if ((accessFlag2 & 0x04) == 0) {
					_accessRights[objectIndex][subjectIndex2] = accessFlag2 | 0x04;
				}
				return true;
			} else {
				return false;
			}
		}

		case Request.ACCESS_WRITE: {
			if ((accessFlag1 & 0x02) != 0) {
				if ((accessFlag2 & 0x02) == 0) {
					_accessRights[objectIndex][subjectIndex2] = accessFlag2 | 0x02;
				}
				return true;
			} else {
				return false;
			}

		}
		default:
			return false;
		}
	}

	public void add(DObject object, DSubject subject, int accessRights) {
		int objectsCount = _objects.length;
		int subjectsCount = _subjects.length;

		int objectIndex = indexOf(_objects, object);
		int subjectIndex = indexOf(_subjects, subject);

		if (objectIndex == -1) {
			int[][] newArray = new int[objectsCount + 1][subjectsCount];
			System.arraycopy(_accessRights, 0, newArray, 0, objectsCount);
			_accessRights = newArray;

			DObject[] newDObject = new DObject[_objects.length + 1];
			System.arraycopy(_objects, 0, newDObject, 0, _objects.length);
			_objects = newDObject;

			objectIndex = _objects.length - 1;
			objectsCount++;
		}

		if (subjectIndex == -1) {
			int[][] newArray = new int[objectsCount][subjectsCount + 1];
			for (int i = 0; i < _accessRights.length; i++) {
				int[] line = _accessRights[i];
				System.arraycopy(line, 0, newArray[i], 0, line.length);
			}
			_accessRights = newArray;

			DSubject[] newDSubject = new DSubject[_subjects.length + 1];
			System.arraycopy(_subjects, 0, newDSubject, 0, _subjects.length);
			_subjects = newDSubject;

			subjectIndex = _subjects.length - 1;
			subjectsCount++;
		}

		_accessRights[objectIndex][subjectIndex] = accessRights;
		_objects[objectIndex] = object;
		_subjects[subjectIndex] = subject;
	}

	public int getAcessFlag(DObject object, DSubject subject) {
		int objectIndex = indexOf(_objects, object);
		int subjectIndex = indexOf(_subjects, subject);

		if ((objectIndex == -1) || (subjectIndex == -1)) {
			return -1;
		}

		return _accessRights[objectIndex][subjectIndex];
	}

	public AbstractObject[] getObjects() {
		return _objects;
	}

	public AbstractSubject[] getSubjects() {
		return _subjects;
	}

	private int indexOf(Object[] array, Object object) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == object) {
				return i;
			}
		}

		return -1;
	}
}
