package com.qount.controller.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.code.genreation.Controller;
import com.code.genreation.ControllerImpl;
import com.code.genreation.Dao;
import com.code.genreation.DaoImpl;
import com.code.genreation.Pojo;
import com.code.genreation.SqlQuerys;
import com.code.genreation.UiComponent;
import com.code.genreation.UiForm;
import com.code.genreation.UiHtml;
import com.code.genreation.UiModel;
import com.code.genreation.UiService;
import com.qount.common.DatabaseUtilities;
import com.qount.common.PropertyManager;
import com.qount.common.Utilities;

public class CodeControllerImpl {

	private static final Logger LOGGER = Logger.getLogger(CodeControllerImpl.class);

	public static Response getCode(String json) throws JSONException {
		List<File> files = null;
		ZipOutputStream zos = null;
		BufferedInputStream fif = null;
		try {
			JSONObject requestObj = new JSONObject(json);
			System.out.println(requestObj);
			if(requestObj.optBoolean("java")){
				String tableName = requestObj.optString("table");
				String dbName = requestObj.optString("database");
				String pk = requestObj.optString("pk");
				if (!StringUtils.isEmpty(pk)) {
					requestObj.put("pk", pk);
				}
				JSONArray fields = getTableDetails(pk, tableName, dbName, requestObj);
				if (fields != null && fields.length() != 0) {
					requestObj.put("fields", fields);
					requestObj.put("name", tableName);
				} else {
					throw new WebApplicationException("unable to fetch fields for the table:" + tableName, 417);
				}
			}
			files = new ArrayList<File>();
			// List of files to be downloaded
			if(requestObj.optBoolean("java"))
				files.addAll(Java.getFiles(requestObj));
			if(requestObj.optBoolean("angular4"))
				files.addAll(Angular4.getFiles(requestObj));

			FileOutputStream out = new FileOutputStream(PropertyManager.getProperty("temp.zip.file.location"));
			// ServletOutputStream out = response.getOutputStream();
			zos = new ZipOutputStream(new BufferedOutputStream(out));
			for (File file : files) {
				if (file == null) {
					continue;
				}
				System.out.println("Adding file " + file.getName());
				zos.putNextEntry(new ZipEntry(file.getName()));
				// Get the file
				FileInputStream fis = null;
				try {
					fis = new FileInputStream(file);
				} catch (FileNotFoundException fnfe) {
					// If the file does not exists, write an error entry instead
					// of file contents
					zos.write(("ERROR: Could not find file " + file.getName()).getBytes());
					zos.closeEntry();
					System.out.println("Could not find file " + file.getAbsolutePath());
					continue;
				}
				fif = new BufferedInputStream(fis);
				// Write the contents of the file
				int data = 0;
				while ((data = fif.read()) != -1) {
					zos.write(data);
				}
				fif.close();
				zos.closeEntry();
				System.out.println("Finished adding file " + file.getName());
			}
			File zipFile = new File(PropertyManager.getProperty("temp.zip.file.location"));
			ResponseBuilder builder = Response.ok(zipFile);
			builder.header("Content-Disposition", "attachment; filename=code.zip");
			builder.header("Content-type", "application/zip");
			return builder.build();
		} catch (WebApplicationException e) {
			LOGGER.error("error creating code", e);
			throw e;
		} catch (Exception e) {
			LOGGER.error("error creating code", e);
			throw new WebApplicationException(e.getMessage(), 417);
		} finally {
			Utilities.deleteFilesAsync(files);
			Utilities.closeZipOutputStream(zos);
			Utilities.closeBufferedInputStream(fif);
		}
	}

	private static JSONArray getTableDetails(String pk, String tableName, String dbName, JSONObject requestObj) throws Exception {
		if (StringUtils.isEmpty(tableName)) {
			return null;
		}
		if (requestObj == null) {
			return null;
		}
		PreparedStatement ps = null;
		ResultSet rs = null;
		ResultSet pkResultSet = null;
		Connection conn = null;
		try {
			conn = DatabaseUtilities.getReadConnection();
			ps = conn.prepareStatement("select * from `" + dbName + "`.`" + tableName + "` limit 1;");
			rs = ps.executeQuery();
			if (StringUtils.isEmpty(pk)) {
				DatabaseMetaData dm = conn.getMetaData();
				pkResultSet = dm.getExportedKeys("", "qount", tableName);
				if (pkResultSet.next()) {
					pk = pkResultSet.getString("PKCOLUMN_NAME");
				}
				if (StringUtils.isEmpty(pk)) {
					throw new Exception("single column pk is mandatory in the selected table");
				}
			}
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			JSONArray result = new JSONArray();
			boolean isPkFound = false;
			for (int i = 1; i <= columnCount; i++) {
				JSONObject obj = new JSONObject();
				String columnName = rsmd.getColumnName(i);
				obj.put("name", columnName);
				int columnType = rsmd.getColumnType(i);
				obj.put("type", Utilities.getJavaTypeFromSql(columnType));
				if (!isPkFound && columnName.equals(pk)) {
					requestObj.put("pk", columnName);
					requestObj.put("pkType", Utilities.getJavaTypeFromSql(columnType));
					isPkFound = true;
				}
				result.put(obj);
			}
			return result;
		} catch (Exception e) {
			LOGGER.error(e);
			throw e;
		} finally {
			DatabaseUtilities.closeConnection(conn);
			DatabaseUtilities.closeStatement(ps);
			DatabaseUtilities.closeResultSet(pkResultSet);
			DatabaseUtilities.closeResultSet(rs);
		}
	}

	public static Response getTables() {
		JSONArray tablesList = new JSONArray();
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DatabaseUtilities.getReadConnection();
			rs = conn.createStatement().executeQuery("show tables;");
			while (rs.next()) {
				tablesList.put(rs.getString(1));
			}
			System.out.println(tablesList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtilities.closeResultSet(rs);
			DatabaseUtilities.closeConnection(conn);
		}
		return Response.ok(tablesList.toString()).status(200).build();
	}

	static class Java {

		public static List<File> getFiles(JSONObject requestObj) throws Exception {
			try {
				List<File> files = new ArrayList<>();
				File pojo = null, dao = null, daoImpl = null, sqlQuerys = null, controller = null, controllerImpl = null;
				boolean createPojo = Boolean.parseBoolean(requestObj.optString("pojo"));
				boolean createDao = Boolean.parseBoolean(requestObj.optString("dao"));
				boolean createDaoImpl = Boolean.parseBoolean(requestObj.optString("daoImpl"));
				boolean createQuerys = Boolean.parseBoolean(requestObj.optString("querys"));
				boolean createController = Boolean.parseBoolean(requestObj.optString("controller"));
				boolean createcontrollerImpl = Boolean.parseBoolean(requestObj.optString("controllerImpl"));
				if (createPojo)
					pojo = Pojo.generateCode(requestObj);
				if (createDao)
					dao = Dao.generateCode(requestObj);
				if (createDaoImpl)
					daoImpl = DaoImpl.generateCode(requestObj);
				if (createQuerys)
					sqlQuerys = SqlQuerys.generateCode(requestObj);
				if (createController)
					controller = Controller.generateCode(requestObj);
				if (createcontrollerImpl)
					controllerImpl = ControllerImpl.createControllerImpl(requestObj);
				files.add(pojo);
				files.add(dao);
				files.add(daoImpl);
				files.add(sqlQuerys);
				files.add(controller);
				files.add(controllerImpl);
				return files;
			} catch (Exception e) {
				LOGGER.error("error creating java code", e);
				throw e;
			}
		}
	}

	static class Angular4 {

		public static List<File> getFiles(JSONObject requestObj) throws Exception {
			try {
				List<File> files = new ArrayList<>();
				boolean createUiComponent = Boolean.parseBoolean(requestObj.optString("component"));
				boolean createUiForm = Boolean.parseBoolean(requestObj.optString("form"));
				boolean createUiModel = Boolean.parseBoolean(requestObj.optString("model"));
				boolean createUiService = Boolean.parseBoolean(requestObj.optString("service"));
				boolean createUiHtml = Boolean.parseBoolean(requestObj.optString("html"));
				File uiComponent = null, uiForm = null, uiModel = null, uiService = null, uiHtml = null;
				if (createUiComponent)
					uiComponent = UiComponent.generateCode(requestObj);
				if (createUiForm)
					uiForm = UiForm.generateCode(requestObj);
				if (createUiModel)
					uiModel = UiModel.generateCode(requestObj);
				if (createUiService)
					uiService = UiService.generateCode(requestObj);
				if (createUiHtml)
					uiHtml = UiHtml.generateCode(requestObj);
				files.add(uiComponent);
				files.add(uiForm);
				files.add(uiModel);
				files.add(uiService);
				files.add(uiHtml);
				return files;
			} catch (Exception e) {
				LOGGER.error("error creating angular 4 code", e);
				throw e;
			}
		}
	}
}