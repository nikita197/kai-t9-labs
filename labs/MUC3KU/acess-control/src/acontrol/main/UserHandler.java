package acontrol.main;

import acontrol.policies.AbstractObject;
import acontrol.policies.AbstractSubject;
import acontrol.policies.IPolicy;
import acontrol.policies.Request;
import acontrol.policies.discretionary.DObject;
import acontrol.policies.discretionary.DPolicy;
import acontrol.policies.discretionary.DSubject;
import acontrol.policies.mandatory.MObject;
import acontrol.policies.mandatory.MPolicy;
import acontrol.policies.mandatory.MSubject;
import acontrol.utils.Util;

public class UserHandler {

	private static final String CMD_READ = "read";
	private static final String CMD_WRITE = "write";
	private static final String CMD_LIST = "ls";
	private static final String CMD_WHOAMI = "whoami";
	private static final String CMD_LOGOUT = "logout";
	private static final String CMD_EXIT = "exit";

	// Policy specific commands
	private static final String CMD_GRANT = "grant";
	private static final String CMD_CHANGE = "change";

	private boolean finish = false;
	private IPolicy _policy;
	private AbstractSubject _currentSubject;

	public void start(IPolicy policy) {
		_policy = policy;
		String command;
		while (!finish) {
			while (!finish && _currentSubject == null) {
				System.out.print("Login: ");
				Util.clear();
				command = Util.readln();
				try {
					executePreLCommand(command.split(" "));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getMessage());
				}
			}

			if (finish) {
				break;
			}

			System.out.print("/>");
			Util.clear();
			command = Util.readln();
			try {
				executeCommand(command.split(" "));
			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	public void executePreLCommand(String[] command) {
		int length = command.length;
		if (length > 0) {
			if (CMD_LIST.equals(command[0])) {
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				AbstractSubject[] subjects = _policy.getSubjects();
				StringBuffer result = new StringBuffer();
				result.append("Subjects:"
						+ System.getProperty("line.separator"));
				for (int i = 0; i < subjects.length; i++) {
					result.append("\t" + subjects[i].getName());
					result.append(System.getProperty("line.separator"));
				}
				System.out.println(result);
			} else if (CMD_EXIT.equals(command[0])) {
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}
				finish = true;
			} else if ((length == 1)
					&& (_policy.getSubject(command[0]) != null)) {
				_currentSubject = _policy.getSubject(command[0]);
			} else {
				throw new IllegalArgumentException("Unknown command");
			}
		}
	}

	public void executeCommand(String[] command) {
		int length = command.length;
		if (length > 0) {
			if (CMD_READ.equals(command[0])) {
				if (length != 2) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				AbstractObject object = _policy.getObject(command[1]);
				if (object == null) {
					System.out.println("Object is not exist");
				} else {
					if (_policy.getAccess(new Request(object, _currentSubject,
							Request.ACCESS_READ))) {
						System.out.println("Operation complete");
					} else {
						System.out.println("Operation is not allowed");
					}
				}
			} else if (CMD_WRITE.equals(command[0])) {
				if (length != 2) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				AbstractObject object = _policy.getObject(command[1]);
				if (object == null) {
					System.out.println("Object is not exist");
				} else {
					if (_policy.getAccess(new Request(object, _currentSubject,
							Request.ACCESS_WRITE))) {
						System.out.println("Operation complete");
					} else {
						System.out.println("Operation is not allowed");
					}
				}
			} else if (CMD_LOGOUT.equals(command[0])) {
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}
				_currentSubject = null;
			} else if (CMD_LIST.equals(command[0])) {
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				StringBuffer result = new StringBuffer();
				if (_policy instanceof DPolicy) {
					DPolicy policy = (DPolicy) _policy;
					AbstractSubject[] subjects = policy.getSubjects();
					AbstractObject[] objects = policy.getObjects();

					for (int i = -1; i < subjects.length; i++) {
						if (i == -1) {
							result.append("\t");
							for (int j = 0; j < objects.length; j++) {
								result.append(objects[j].getName() + "\t");
							}
						} else {
							for (int j = -1; j < objects.length; j++) {
								if (j == -1) {
									result.append(subjects[i].getName() + "\t");
								} else {
									result.append(Integer.toBinaryString(policy
											.getAcces((DObject) objects[j],
													(DSubject) subjects[i]))
											+ "\t");
								}
							}
						}
						result.append(System.getProperty("line.separator"));
					}
				} else {
					AbstractSubject[] subjects = _policy.getSubjects();
					AbstractObject[] objects = _policy.getObjects();
					result.append("\tSubjects:\t\t\tObjects:"
							+ System.getProperty("line.separator"));
					for (int i = 0; i < Math.max(subjects.length,
							objects.length); i++) {
						if (i < subjects.length) {
							result.append("\t"
									+ subjects[i].getName()
									+ " : "
									+ MPolicy
											.getLevelName(((MSubject) subjects[i])
													.getAccessLevel())
									+ "\t\t\t");
						} else {
							result.append("\t\t\t\t\t");
						}

						if (i < objects.length) {
							result.append(objects[i].getName()
									+ " : "
									+ MPolicy
											.getLevelName(((MObject) objects[i])
													.getAccessLevel())
									+ System.getProperty("line.separator"));
						} else {
							result.append(System.getProperty("line.separator"));
						}
					}
				}

				System.out.println(result);
			} else if (CMD_WHOAMI.equals(command[0])) {
				if (length != 1) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}
				if (_currentSubject instanceof MSubject) {
					System.out.println("User name: "
							+ _currentSubject.getName()
							+ " Access level: "
							+ MPolicy.getLevelName(((MSubject) _currentSubject)
									.getAccessLevel()));
				} else {
					System.out.println("User name: "
							+ _currentSubject.getName());
				}
			} else if ((CMD_GRANT.equals(command[0]))
					&& (_policy instanceof DPolicy)) {
				if (length != 4) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				if (!(CMD_WRITE.equals(command[1]))
						&& !(CMD_READ.equals(command[1]))) {
					throw new IllegalArgumentException("Incorrect access type");
				}

				if (_policy.getObject(command[2]) == null) {
					throw new IllegalArgumentException("Object not exist");
				}

				if (_policy.getSubject(command[3]) == null) {
					throw new IllegalArgumentException("Subject not exist");
				}

				int type;
				if (CMD_WRITE.equals(command[1])) {
					type = Request.ACCESS_WRITE;
				} else {
					type = Request.ACCESS_READ;
				}

				if (!_policy.getAccess(new Request(_policy
						.getObject(command[2]), _currentSubject,
						Request.ACCESS_DELEGATE))) {
					throw new IllegalArgumentException(
							"Operation is not allowed");
				}

				boolean result = _policy.delegateAccess(_currentSubject,
						_policy.getSubject(command[3]),
						_policy.getObject(command[2]), type);

				if (result) {
					System.out.println("Operation complete");
				} else {
					System.out.println("Operation is not allowed");
				}

			} else if ((CMD_CHANGE.equals(command[0]))
					&& (_policy instanceof MPolicy)) {
				if (length != 3) {
					throw new IllegalArgumentException(
							"Incorrect arguments count");
				}

				try {
					int value = Integer.valueOf(command[2]);
					_policy.changeAccessLevel(_currentSubject,
							((MSubject) _currentSubject).getAccessLevel()
									+ value);
					if ("+".equals(command[1])) {
						_policy.changeAccessLevel(_currentSubject,
								((MSubject) _currentSubject).getAccessLevel()
										+ value);
					} else if ("-".equals(command[1])) {
						boolean result = _policy.changeAccessLevel(
								_currentSubject,
								((MSubject) _currentSubject).getAccessLevel()
										- value);
						if (result) {
							System.out.println("Operation complete");
						} else {
							System.out.println("Operation is not allowed");
						}
					} else {
						throw new IllegalArgumentException(
								"Incorrect arguments type");
					}
				} catch (NumberFormatException e) {
					throw new IllegalArgumentException(
							"Incorrect arguments type");
				}

			} else {
				throw new IllegalArgumentException("Unknown command");
			}
		}
	}
}
