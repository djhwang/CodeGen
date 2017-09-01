/**
 * DBSupprot.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.parser.db;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.score.codegen.model.ClassDescriptor;
import com.score.codegen.model.Configuration;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public abstract class DBSupprot{
	public HashMap<String, String> Sql2JavaMap;
	public String metaQuery;

	public void init(String propertyFileName){
		Sql2JavaMap = new HashMap<String, String>();

		try{
			ResourceBundle bundle = ResourceBundle.getBundle(propertyFileName);
			for (Object o : bundle.keySet().toArray()){
				Sql2JavaMap.put(o.toString(), bundle.getString(o.toString()));
			}
		} catch (MissingResourceException e){
			e.printStackTrace();
			System.err.println(propertyFileName + " is missing.");
			System.exit(-1);
		}
	}

	public String convertSQL2JavaType(String sqlType){
		/*
		 * 2011.01.29
		 * djhwang
		 * replace white spaces with a underscore('_')
		 */
		sqlType = sqlType.replace(' ', '_');

		if (Sql2JavaMap.containsKey(sqlType.toUpperCase())){
			return Sql2JavaMap.get(sqlType.toUpperCase());
		} else{
			return null;
		}
	}

	public abstract ArrayList<ClassDescriptor> getClassDesc(
			Configuration config, Connection con);
}
