package com.qount.common;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author mateen
 * @version 1.0 Jun 20th 2017
 */
public class Utilities {

	private static final Logger LOGGER = Logger.getLogger(Utilities.class); 
	
	public static final String MESSAGE = "message";

	public static Response constructResponse(String message, int statusHeader) throws JSONException {
		JSONObject responseJSON = new JSONObject();
		responseJSON.put(MESSAGE, message);
		return Response.status(statusHeader).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

	
	public static String getLtmUrl(String hostName, String portName) {
		String path = null;
		try {
			String internalLinkingAddress = null, internalLinkingPort = null;
			internalLinkingAddress = System.getenv(hostName);
			internalLinkingPort = System.getenv(portName);
			LOGGER.debug("internalLinkingAddress:"+internalLinkingAddress);
			LOGGER.debug("internalLinkingPort:"+internalLinkingPort);
			if (!StringUtils.isBlank(internalLinkingAddress) && !StringUtils.isBlank(internalLinkingPort)) {
				path = "http://" + internalLinkingAddress + ":" + internalLinkingPort + "/";
			}
			return path;
		} catch (Exception e) {
			LOGGER.error(CommonUtils.getErrorStackTrace(e));
		}
		return null;
	}
}
