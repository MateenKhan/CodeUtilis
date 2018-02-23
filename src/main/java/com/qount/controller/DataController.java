package com.qount.controller;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import com.qount.controller.impl.CodeControllerImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Project Metadata Controller")
@Path("/data")
public class DataController {
	/**
	 * this method is used to get the Current version of Project
	 * @throws JSONException 
	 */
	@POST
	@Path("/code")
	@ApiOperation(value = "Returns Project Current version ", notes = "Used to to get Project Current version", responseContainer = "java.lang.String")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCode(String json) throws JSONException {
		return CodeControllerImpl.getCode(json);

	}
	
	/**
	 * this method is used to get the Current version of Project
	 * @throws JSONException 
	 */
	@GET
	@Path("/tables")
	@ApiOperation(value = "Returns Project Current version ", notes = "Used to to get Project Current version", responseContainer = "java.lang.String")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTables() throws JSONException {
		return CodeControllerImpl.getTables();

	}

}