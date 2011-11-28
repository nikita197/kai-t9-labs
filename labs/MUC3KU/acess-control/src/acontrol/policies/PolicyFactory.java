package acontrol.policies;

import acontrol.policies.discretionary.DObject;
import acontrol.policies.discretionary.DPolicy;
import acontrol.policies.discretionary.DStorage;
import acontrol.policies.discretionary.DSubject;
import acontrol.policies.mandatory.MObject;
import acontrol.policies.mandatory.MPolicy;
import acontrol.policies.mandatory.MStorage;
import acontrol.policies.mandatory.MSubject;

public class PolicyFactory {

	public static final int TYPE_DISC = 0;
	public static final int TYPE_MAND = 1;

	private static final int objectsCount = 4;
	private static final int subjectsCount = 7;

	public IPolicy createPolicy(int type) {
		if (type == TYPE_DISC) {
			return createDPolicy();
		} else if (type == TYPE_MAND) {
			return createMPolicy();
		} else {
			throw new IllegalArgumentException("incorrect type");
		}
	}

	private DPolicy createDPolicy() {
		// Storage creation
		DStorage matrix = new DStorage();
		DObject[] objects = new DObject[objectsCount];
		for (int i = 0; i < objectsCount; i++) {
			objects[i] = new DObject();
			objects[i].setName("File" + i);
		}

		DSubject[] subjects = new DSubject[subjectsCount];
		for (int i = 0; i < subjectsCount; i++) {
			subjects[i] = new DSubject();
			subjects[i].setName("User" + i);
		}

		int accessRight;
		for (int i = 0; i < objectsCount; i++) {
			for (int j = 0; j < subjectsCount; j++) {
				if (j != subjectsCount - 1) {
					accessRight = (int) (Math.random() * 8 - 0.1);
				} else {
					accessRight = 7;
				}
				matrix.add(objects[i], subjects[j], accessRight);
			}
		}

		// Policy creation
		DPolicy policy = new DPolicy(matrix);

		return policy;
	}

	private MPolicy createMPolicy() {
		// Storage creation
		int accessLevel;

		MStorage matrix = new MStorage();

		MObject object;
		for (int i = 0; i < objectsCount; i++) {
			accessLevel = (int) (Math.random() * (MPolicy.MAX_AL + 1) - 0.1);

			object = new MObject();
			object.setName("File" + i);
			object.setAccessLevel(accessLevel);
			matrix.addObject(object);
		}

		MSubject subject;
		for (int i = 0; i < subjectsCount; i++) {
			if (i != subjectsCount - 1) {
				accessLevel = (int) (Math.random() * (MPolicy.MAX_AL + 1) - 0.1);
			} else {
				accessLevel = MPolicy.MAX_AL;
			}

			subject = new MSubject();
			subject.setName("User" + i);
			subject.setAccessLevel(accessLevel);
			matrix.addSubject(subject);
		}

		// Policy creation
		MPolicy policy = new MPolicy(matrix);

		return policy;
	}
}
