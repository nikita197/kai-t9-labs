package acontrol.policies.discretionary;

import acontrol.policies.AbstractObject;
import acontrol.policies.Request;
import acontrol.policies.AbstractSubject;

public class DRequest extends Request {

	public DRequest(AbstractObject object, AbstractSubject subject,
			int acessType) {
		super(object, subject, acessType);
	}
}
