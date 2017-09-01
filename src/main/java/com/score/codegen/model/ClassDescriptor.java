/**
 * ClassDescriptor.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.model;

import java.util.ArrayList;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class ClassDescriptor extends Entity{
	private ArrayList<AttributeDescriptor> attributes = new ArrayList<AttributeDescriptor>();
	private ArrayList<AttributeDescriptor> primaryKeys = new ArrayList<AttributeDescriptor>();

	public ClassDescriptor(String name){
		super(name);
	}

	public ArrayList<AttributeDescriptor> getAttributes(){
		return attributes;
	}

	public void setAttributes(ArrayList<AttributeDescriptor> attributes){
		this.attributes = attributes;
	}

	public void addAttribute(AttributeDescriptor attribute){
		attributes.add(attribute);
	}

	public ArrayList<AttributeDescriptor> getPrimaryKeys(){
		return primaryKeys;
	}

	public void setPrimaryKeys(ArrayList<AttributeDescriptor> primaryKeys){
		this.primaryKeys = primaryKeys;
	}

	public void addPrimaryKeys(AttributeDescriptor attribute){
		primaryKeys.add(attribute);
	}

}
