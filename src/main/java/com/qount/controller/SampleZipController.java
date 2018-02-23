package com.qount.controller;

import java.io.File;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Project Metadata Controller")
@Path("/zip")
public class SampleZipController {
	
	/**
	 * this method is used to get the Current version of Project
	 * @throws JSONException 
	 */
	@GET
	@ApiOperation(value = "Returns Project Current version ", notes = "Used to to get Project Current version", responseContainer = "java.lang.String")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getZipFile() throws JSONException {
		File f = new File("E:/temp/1.zip");
		return Response.ok(f).build();

	}

}