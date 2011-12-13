package org.kai.CMV.lab4.cmanager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.hibernate.cfg.Configuration;

public class ConfigurationsManager {

	/** Папка конфигурационных файлов */
	private File _configFilesPath;

	/**
	 * Конструктор
	 * 
	 * @param aConfigFilesPath
	 *            Относительный путь к папке конфигурационных файлов соединений
	 * @throws URISyntaxException
	 */
	public ConfigurationsManager(String aConfigFilesPath)
			throws URISyntaxException {
		_configFilesPath = new File(ConfigurationsManager.class.getResource(
				aConfigFilesPath).toURI());

		if (!_configFilesPath.exists() || !_configFilesPath.isDirectory()) {
			throw new IllegalArgumentException("Folder is not exist");
		}
	}

	/**
	 * Получает конфигураций подключений
	 * 
	 * @return Возвращает список конфигураций подключений
	 * @throws IOException
	 */
	public List<Configuration> getConfigurations() throws IOException {
		List<Configuration> configurations = new ArrayList<Configuration>();
		Properties properties = new Properties();
		for (File f : _configFilesPath.listFiles()) {
			// loading from file
			FileInputStream is = new FileInputStream(f);
			BufferedInputStream bis = new BufferedInputStream(is);
			properties.load(bis);

			// put properties into configuration
			Configuration configuration = new Configuration();
			configuration.addProperties(properties);

			configurations.add(configuration);
		}
		return configurations;
	}

}
