package acontrol.policies.mandatory;

import acontrol.policies.AbstractObject;
import acontrol.policies.Request;
import acontrol.policies.AbstractSubject;

public class MRequest extends Request {

	public MRequest(AbstractObject object, AbstractSubject subject,
			int acessType) {
		super(object, subject, acessType);
	}

}
