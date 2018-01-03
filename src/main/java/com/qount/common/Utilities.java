package com.qount.common;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * @author mateen
 * @version 1.0 Jun 20th 2017
 */
public class Utilities {

	public static final String MESSAGE = "message";

	public static Response constructResponse(String message, int statusHeader) throws JSONException {
		JSONObject responseJSON = new JSONObject();
		responseJSON.put(MESSAGE, message);
		return Response.status(statusHeader).entity(responseJSON.toString()).type(MediaType.APPLICATION_JSON_TYPE).build();
	}

}
