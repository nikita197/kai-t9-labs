import java.io.IOException;
import java.net.URISyntaxException;

import org.courseworks.ris.cmanager.ConfigurationsManager;

public class TestConfigurationManager {

	public static void main(String[] args) {
		ConfigurationsManager man;
		try {
			man = new ConfigurationsManager("/informix_servers/");
			man.getConfigurations();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
