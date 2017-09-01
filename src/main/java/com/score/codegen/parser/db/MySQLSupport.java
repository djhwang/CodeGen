/**
 * MySQLSupport.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.parser.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.score.codegen.model.AttributeDescriptor;
import com.score.codegen.model.ClassDescriptor;
import com.score.codegen.model.Configuration;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class MySQLSupport extends DBSupprot{
	public MySQLSupport(){
		init();
	}

	public void init(){
		super.init("application.db.MySQL");

		metaQuery = "SELECT column_name, data_type, column_type, column_key FROM information_schema.columns WHERE table_name=?";
	}

	public ArrayList<ClassDescriptor> getClassDesc(Configuration config,
			Connection con){
		ArrayList<ClassDescriptor> classes = new ArrayList<ClassDescriptor>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			pstmt = con.prepareStatement(metaQuery);

			for (String model : config.getModels()){
				ClassDescriptor cl = new ClassDescriptor(model);
				pstmt.setString(1, model);
				rs = pstmt.executeQuery();

				while (rs.next()){
					System.out.println(rs.getString(1) + "," + rs.getString(2)
							+ "," + rs.getString(3) + "," + rs.getString(4));
					AttributeDescriptor at = new AttributeDescriptor(
							rs.getString("COLUMN_NAME"));
					at.setType(rs.getString("DATA_TYPE"));
					at.setJavaType(convertSQL2JavaType(rs
							.getString("DATA_TYPE")));

					if ("PRI".equals(rs.getString("COLUMN_KEY"))){
						at.setPrimaryKey(true);
						cl.addPrimaryKeys(at);
					}

					cl.addAttribute(at);
				}

				// attribute가 있는 경우만 저장
				if (cl.getAttributes().size() > 0){
					classes.add(cl);
				} else{
					System.err.println(model + " does not exist.");
				}
			}

		} catch (Exception e){
			e.printStackTrace();
		} finally{
			if (rs != null){
				try{
					rs.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}

			if (pstmt != null){
				try{
					pstmt.close();
				} catch (Exception e){
					e.printStackTrace();
				}
			}
		}

		return classes;
	}

}
