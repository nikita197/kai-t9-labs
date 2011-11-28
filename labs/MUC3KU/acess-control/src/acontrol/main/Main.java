package acontrol.main;

import acontrol.policies.IPolicy;
import acontrol.policies.PolicyFactory;
import acontrol.utils.Util;

public class Main {

	public static void main(String[] args) {
		System.out.println("Enter type: ");
		System.out.println("1 - Discretionary");
		System.out.println("2 - Mandatory");

		String number = Util.readln();
		IPolicy policy;
		PolicyFactory factory = new PolicyFactory();
		if ("1".equals(number)) {
			policy = factory.createPolicy(PolicyFactory.TYPE_DISC);
		} else {
			policy = factory.createPolicy(PolicyFactory.TYPE_MAND);
		}

		UserHandler handler = new UserHandler();
		handler.start(policy);
	}
}
