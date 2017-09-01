/**
 * Template.java 1.0 2013. 8. 4.
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
public class Template{
	private String name;
	private String templateName;
	private String suffix;
	private String ext;
	private String dir;

	public Template(String name, String dir, String suffix, String ext){
		this.name = name;
		this.dir = dir;
		this.suffix = suffix;
		this.ext = ext;
		setTemplateName(name);
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getTemplateName(){
		return templateName;
	}

	public void setTemplateName(String templateName){
		this.templateName = templateName + ".vm";
	}

	public String getSuffix(){
		return suffix;
	}

	public void setSuffix(String suffix){
		this.suffix = suffix;
	}

	public String getExt(){
		return ext;
	}

	public void setExt(String ext){
		this.ext = ext;
	}

	public String getDir(){
		return dir;
	}

	public void setDir(String dir){
		this.dir = dir;
	}

}
