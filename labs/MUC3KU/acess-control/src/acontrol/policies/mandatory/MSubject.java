package acontrol.policies.mandatory;

import acontrol.policies.AbstractSubject;

public class MSubject extends AbstractSubject {
	private int _initAccessLevel = -1;
	private int _accessLevel;

	public void setAccessLevel(int accessLevel) {
		if (accessLevel < 0) {
			_accessLevel = 0;
		} else if (accessLevel > MPolicy.MAX_AL) {
			_accessLevel = MPolicy.MAX_AL;
		} else {
			_accessLevel = accessLevel;
		}

		if (_initAccessLevel == -1) {
			_initAccessLevel = _accessLevel;
		}
	}

	public int getAccessLevel() {
		return _accessLevel;
	}

	public int getInitAccessLevel() {
		return _initAccessLevel;
	}

}
