package com.qount.common;

import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * @author Mateen
 * @version 1.0
 */
public class PropertiesLoader {
	
	private static Logger logger = Logger.getLogger(PropertiesLoader.class);
	
	private static PropertiesLoader propertiesLoader = new PropertiesLoader();
	
	public static PropertiesLoader getPropertiesLoader(){
		return propertiesLoader;
	}
	/**
	 * method used to load project.properties file
	 * @return boolean result value loading project.properties file
	 */
	public boolean loadProjectProperties(){
		boolean result=false;
		try {
			InputStream inputStream=this.getClass().getResourceAsStream("/project.properties");
			logger.debug("project.properties file loaded");
			PropertyManager.getPropertyManager().getProperties().load(inputStream);
			result=true;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("error loading project.properties file",e);
		}
		return result;
	}

}
