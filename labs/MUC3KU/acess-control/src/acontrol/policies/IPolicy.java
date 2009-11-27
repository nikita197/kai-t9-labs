package acontrol.policies;

public interface IPolicy {

	public AbstractSubject[] getSubjects();

	public AbstractObject[] getObjects();

	public AbstractSubject getSubject(String subject);

	public AbstractObject getObject(String object);

	public boolean getAccess(Request request);

	public boolean changeAccessLevel(AbstractSubject subject, int newAccessLevel);

	public boolean delegateAccess(AbstractSubject subject1,
			AbstractSubject subject2, AbstractObject object, int accessType);
}
