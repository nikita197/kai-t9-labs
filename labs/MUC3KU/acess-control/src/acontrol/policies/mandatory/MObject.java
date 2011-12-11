package acontrol.policies.mandatory;

import acontrol.policies.AbstractObject;

public class MObject extends AbstractObject {

	private int _accessLevel;

	public void setAccessLevel(int accessLevel) {
		if (accessLevel < 0) {
			_accessLevel = 0;
		} else if (accessLevel > MPolicy.MAX_AL) {
			_accessLevel = MPolicy.MAX_AL;
		} else {
			_accessLevel = accessLevel;
		}
	}

	public int getAccessLevel() {
		return _accessLevel;
	}

}
