package com.qount.JavaWebAppStructure.controller;

/**
 * 
 * @author mateen
 * @version 1.0
 * JUne 20th 2017
 */
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.qount.JavaWebAppStructure.common.CommonUtils;
import com.qount.JavaWebAppStructure.common.Constants;
import com.qount.JavaWebAppStructure.common.PropertyManager;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Project Metadata Controller")
@Path("/")
public class ProjectVersionController {
	/**
	 * this method is used to get the Current version of Project
	 * @throws JSONException 
	 */
	@GET
	@Path("version")
	@ApiOperation(value = "Returns Project Current version ", notes = "Used to to get Project Current version", responseContainer = "java.lang.String")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProjectCurrentVersion() throws JSONException {
		return CommonUtils.constructResponse(PropertyManager.getProperty(Constants.PROJECT_CURRENT_VERSION, null), Constants.SUCCESS_RESPONSE_CODE);

	}

}