/**
 * ClassDescriptorImporterDB.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

import com.score.codegen.model.ClassDescriptor;
import com.score.codegen.model.Configuration;
import com.score.codegen.parser.db.DBSupprot;
import com.score.codegen.parser.db.MySQLSupport;
import com.score.codegen.parser.db.PostgreSQLSupport;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class ClassDescriptorImporterDB{
	private ArrayList<ClassDescriptor> classes = new ArrayList<ClassDescriptor>();
	private Configuration config;
	private Connection con;

	public ClassDescriptorImporterDB(Configuration config){
		this.config = config;
	}

	public ArrayList<ClassDescriptor> getClasses(){
		return classes;
	}

	public void load(){
		connect();

		select();

		disconnect();
	}

	public void connect(){
		try{
			Class.forName(config.getDbDriver()).newInstance();
			con = DriverManager.getConnection(config.getDbUrl(),
					config.getDbUsername(), config.getDbPasswd());
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	public void disconnect(){
		if (con != null){
			try{
				con.close();
				System.out.println("Connection closed.");
			} catch (Exception e){
				e.printStackTrace();
			}
		}
	}

	public void select(){
		DBSupprot db = null;

		if (config.getDbVendor().equalsIgnoreCase("MySQL")){
			db = new MySQLSupport();
		} else if (config.getDbVendor().equalsIgnoreCase("PostgreSQL")){
			db = new PostgreSQLSupport();
		}

		classes = db.getClassDesc(config, con);
	}

}
