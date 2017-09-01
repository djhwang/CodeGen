/**
 * Entity.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.model;

import com.score.codegen.util.GenUtils;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class Entity{
	private String name;
	private String tbName; // tableName
	private String ucName; // in upperCase name
	private String lcName; // in lowerCase name
	private String ccName; // in CamelCase name
	private String pcName; // in pascalCase name

	public Entity(String name){
		setName(name);
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		setTbName(name);
		String s = name.toUpperCase();

		if (s.startsWith("TB_")){
			name = name.substring("TB_".length());
		}
		this.name = name;
		setUcName(name.toUpperCase());
		setLcName(name.toLowerCase());
		setCcName(GenUtils.underscoreToCamelcase(name));
		setPcName(GenUtils.camelcaseToPascalcase(getCcName()));
	}

	public String getTbName(){
		return tbName;
	}

	public void setTbName(String tbName){
		this.tbName = tbName;
	}

	public String getUcName(){
		return ucName;
	}

	public void setUcName(String ucName){
		this.ucName = ucName;
	}

	public String getLcName(){
		return lcName;
	}

	public void setLcName(String lcName){
		this.lcName = lcName;
	}

	public String getCcName(){
		return ccName;
	}

	public void setCcName(String ccName){
		this.ccName = ccName;
	}

	public String getPcName(){
		return pcName;
	}

	public void setPcName(String pcName){
		this.pcName = pcName;
	}

}
