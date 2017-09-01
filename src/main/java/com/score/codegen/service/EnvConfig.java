/**
 * EnvConfig.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.service;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class EnvConfig{
	private static AbstractPropertyServiceImpl propService = new AbstractPropertyServiceImpl(
			"properties.env", "");

	public static String getMode(){
		return propService.getProperty("mode", "");
	}

	public static String getDbVendor(){
		return propService.getProperty("db.vendor", "");
	}

	public static String getDbDriver(){
		return propService.getProperty("db.driver", "");
	}

	public static String getDbUrl(){
		return propService.getProperty("db.url", "");
	}

	public static String getDbUserName(){
		return propService.getProperty("db.username", "");
	}

	public static String getDbPassword(){
		return propService.getProperty("db.password", "");
	}

	public static String getDbEncoding(){
		return propService.getProperty("db.driver", "");
	}

	public static String getTargetDbUrl(){
		return propService.getProperty("target.db.url", "");
	}

	public static String getTargetDbUserName(){
		return propService.getProperty("target.db.username", "");
	}

	public static String getTargetDbPassword(){
		return propService.getProperty("target.db.password", "");
	}

	public static String getAuthor(){
		return propService.getProperty("author", "");
	}

	public static String getCreateDate(){
		return propService.getProperty("createDate", "");
	}

	public static String getCommonPackage(){
		return propService.getProperty("commonPackage", "");
	}

	public static String getDaoPackage(){
		return propService.getProperty("daoPackage", "");
	}

	public static String getVoPackage(){
		return propService.getProperty("voPackage", "");
	}

	public static String getServicePackage(){
		return propService.getProperty("servicePackage", "");
	}

	public static String getServiceImplPackage(){
		return propService.getProperty("serviceImplPackage", "");
	}

	public static String getControllerPackage(){
		return propService.getProperty("controllerPackage", "");
	}

	public static String getJspFolder(){
		return propService.getProperty("jspFolder", "");
	}

	public static String getResourceFolder(){
		return propService.getProperty("resouceFolder", "");
	}

	public static String getModels(){
		return propService.getProperty("models", "");
	}

	public static String getTemplates(){
		return propService.getProperty("templates", "");
	}
}
