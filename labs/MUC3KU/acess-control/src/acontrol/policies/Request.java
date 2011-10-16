package acontrol.policies;

public class Request {

	public static final int ACCESS_WRITE = 0;
	public static final int ACCESS_READ = 1;
	public static final int ACCESS_DELEGATE = 2;

	protected AbstractObject _object;
	protected AbstractSubject _subject;
	protected int _accessType;

	public Request(AbstractObject object, AbstractSubject subject,
			int acessType) {
		_object = object;
		_subject = subject;
		_accessType = acessType;
	}

	public AbstractObject getAccessObject() {
		return _object;
	}

	public AbstractSubject getAccessSubject() {
		return _subject;
	}

	public int getAccessType() {
		return _accessType;
	}

}
