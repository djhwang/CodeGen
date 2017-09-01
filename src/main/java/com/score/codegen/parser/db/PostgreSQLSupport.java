/**
 * PostgreSQLSupport.java 1.0 2013. 8. 4.
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
public class PostgreSQLSupport extends DBSupprot{
	public PostgreSQLSupport(){
		init();
	}

	public void init(){
		super.init("application.db.PostgreSQL");

		metaQuery = "SELECT a.column_name column_name, a.data_type data_type, a.udt_name column_type, CASE WHEN b.column_name IS NULL THEN '' ELSE 'PRI' END column_key"
				+ "  FROM information_schema.columns a"
				+ "       LEFT OUTER JOIN ("
				+ "            SELECT ia.table_name, ia.constraint_name, ib.column_name"
				+ "              FROM information_schema.table_constraints ia,"
				+ "                   information_schema.constraint_column_usage ib"
				+ "             WHERE ia.table_name=?"
				+ "               AND ia.constraint_type='PRIMARY KEY'"
				+ "               AND ib.table_name=ia.table_name"
				+ "               AND ib.constraint_name=ia.constraint_name"
				+ "       ) b  ON ( a.table_name=b.table_name AND a.column_name=b.column_name)"
				+ " WHERE a.table_name=?";
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
				pstmt.setString(2, model);
				rs = pstmt.executeQuery();

				while (rs.next()){
					System.out.println(rs.getString(1) + "," + rs.getString(2)
							+ "," + rs.getString(3) + "," + rs.getString(4));
					AttributeDescriptor at = new AttributeDescriptor(
							rs.getString("COLUMN_NAME"));
					at.setType(rs.getString("DATA_TYPE"));
					at.setJavaType(convertSQL2JavaType(rs
							.getString("DATA_TYPE")));
					at.setAsType(convertSQL2JavaType(rs.getString("DATA_TYPE")));

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
