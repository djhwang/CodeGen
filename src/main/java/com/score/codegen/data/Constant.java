/**
 * Constant.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.data;

import java.io.File;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public final class Constant{
	private static final String SEPARATOR = File.separator;

	public static String ROOT_OUTPUT = "output";

	public static String ROOT_JAVA = ROOT_OUTPUT + SEPARATOR + "java";
	public static String ROOT_JSP = ROOT_OUTPUT + SEPARATOR + "jsp";
	public static String ROOT_RESOURCES = ROOT_OUTPUT + SEPARATOR + "resources";
	public static String ROOT_FLEX = ROOT_OUTPUT + SEPARATOR + "flex";

	public static String JAVA_VO = "java_vo";
	public static String JAVA_DAO = "java_dao";
	public static String JAVA_SERVICE = "java_service";
	public static String JAVA_SERVICEIMPL = "java_serviceimpl";
	public static String JAVA_CONTROLLER = "java_controller";
	public static String JAVA_RESTCONTROLLER = "java_restcontroller";

	public static String JSP_LIST = "jsp_list";
	public static String JSP_VIEW = "jsp_view";

	public static String RESOURCES_SQLMAP = "resources_sqlmap";
	public static String RESOURCES_QUERY = "resources_query";

	public static String FLEX_VO = "flex_vo";
	public static String FLEX_DA = "flex_da";
	public static String FLEX_LOCALDAIMPL = "flex_localdaimpl";
	public static String FLEX_RESTDAIMPL = "flex_restdaimpl";
	public static String FLEX_DAO = "flex_dao";
	public static String FLEX_SERVICE = "flex_service";
	public static String FLEX_CONTROLLER = "flex_controller";
}
