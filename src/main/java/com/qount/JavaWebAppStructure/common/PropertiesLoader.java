package com.qount.JavaWebAppStructure.common;

import java.io.InputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * @author mateen
 * @version 1.0 Jun 20th 2017
 */
public class PropertiesLoader {

	private static Logger logger = Logger.getLogger(PropertiesLoader.class);

	private static PropertiesLoader propertiesLoader = new PropertiesLoader();

	public static PropertiesLoader getPropertiesLoader() {
		return propertiesLoader;
	}

	/**
	 * method used to load project.properties file
	 * 
	 * @return boolean result value loading project.properties file
	 */
	public boolean loadProjectProperties() {
		boolean result = false;
		try {
			loadProjectEnvironmentMode();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error loading project.properties file");
			logger.error(e);
			// System.exit(0);
		}
		return result;
	}

	/**
	 * below method used to load the project environment mode (development |
	 * production) SERVER_INSTANCE_MODE=PRODUCTION
	 * SERVER_INSTANCE_MODE=DEVELOPMENT
	 */
	private void loadProjectEnvironmentMode() {
		String mode = System.getenv("SERVER_INSTANCE_MODE");
		// String mode = Constants.PRODUCTION;
		if (StringUtils.isBlank(mode)) {
			logger.error("stopping build, unable to load the environment variable SERVER_INSTANCE_MODE");
			// System.exit(1);
		}
		try {
			InputStream inputStream = null;
			if (mode.equalsIgnoreCase("PRODUCTION")) {
				inputStream = this.getClass().getResourceAsStream("/project_production.properties");
			}
			if (mode.equalsIgnoreCase("DEVELOPMENT")) {
				inputStream = this.getClass().getResourceAsStream("/project_development.properties");
			}
			PropertyManager.getPropertyManager().getProperties().load(inputStream);
		} catch (Exception e) {
			logger.error("stopping build, exception while environment variable SERVER_INSTANCE_MODE");
			logger.error(e);
			// System.exit(2);
		}
	}
}
