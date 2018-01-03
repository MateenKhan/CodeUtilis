package com.qount.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.qount.common.Constants;
import com.qount.common.Log4jLoder;
import com.qount.common.MySQLManager;
import com.qount.common.PropertiesLoader;
import com.qount.common.PropertyManager;

import io.swagger.jaxrs.config.BeanConfig;

/**
 * 
 * @author mateen
 * @version 1.0 JUne 20th 2017
 */
public class ConfigurationLoaderServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * this method is called while the server startup or on deploying of the
	 * application to the server
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		loadSwaggerConfiguration(config);
		Log4jLoder.getLog4jLoder().initilializeLogging();
		PropertiesLoader.getPropertiesLoader().loadProjectProperties();
		if (PropertyManager.getProperty("create.db.connection.onload").equals("true")) {
			try {
				Class.forName(MySQLManager.class.getName());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void loadSwaggerConfiguration(ServletConfig config) {
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion(Constants.SWAGGER_API_SPEC_VERSION);
		beanConfig.setSchemes(new String[] { Constants.SWAGGER_API_HTTP });
		beanConfig.setBasePath(config.getServletContext().getContextPath());
		beanConfig.setResourcePackage(Constants.SWAGGER_API_PACKAGE);
		beanConfig.setScan(true);
	}

}