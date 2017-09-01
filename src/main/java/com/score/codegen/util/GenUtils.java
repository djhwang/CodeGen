/**
 * GenUtils.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.util;

import java.io.File;

import org.apache.commons.lang.StringUtils;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class GenUtils{
	private static final String UNDERSCORE = "_";

	public static String underscoreToCamelcase(String name){
		if (StringUtils.isNotEmpty(name)){
			if (StringUtils.startsWith(name, UNDERSCORE)){
				name = name.substring(1);
			}

			String[] strArry = name.split(UNDERSCORE);
			StringBuffer sb = new StringBuffer(name.length());
			for (String s : strArry){
				sb.append(StringUtils.capitalize(StringUtils.lowerCase(s)));
			}

			return sb.toString();
		}

		return null;
	}

	public static String camelcaseToPascalcase(String name){
		if (StringUtils.isNotEmpty(name)){
			return StringUtils.uncapitalize(name);
		}
		return null;
	}

	public static void makeDirs(String path){
		try{
			boolean success = (new File(path)).mkdirs();
			if (success){
				System.out.println("Directory : '" + path + "' created.");
			}
		} catch (Exception e){
			System.err.println(e.getMessage());
		}
	}
}
