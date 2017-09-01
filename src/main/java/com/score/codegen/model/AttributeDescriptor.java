/**
 * AttributeDescriptor.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.model;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class AttributeDescriptor extends Entity{
	private String type;
	private String javaType;
	private String asType;
	private boolean isPrimaryKey;

	public AttributeDescriptor(String name){
		super(name);
		this.isPrimaryKey = false;
	}

	public String getType(){
		return type;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getJavaType(){
		return javaType;
	}

	public void setJavaType(String javaType){
		this.javaType = javaType;
	}

	public String getAsType(){
		return asType;
	}

	public void setAsType(String asType){
		if ("Integer".equalsIgnoreCase(asType)){
			asType = "int";
		}

		this.asType = asType;
	}

	public boolean isPrimaryKey(){
		return isPrimaryKey;
	}

	public void setPrimaryKey(boolean isPrimaryKey){
		this.isPrimaryKey = isPrimaryKey;
	}

}
