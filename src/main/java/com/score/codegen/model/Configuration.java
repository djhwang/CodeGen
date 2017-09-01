/**
 * Configuration.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class Configuration{
	private String mode;
	private String author;
	private String createDate;
	private String daoPackage;
	private String voPackage;
	private String servicePackage;
	private String serviceImplPackage;
	private String controllerPackage;
	private String commonPackage;
	private String jspFolder;
	private String resourceFolder;

	private String daPackage;

	private String dbVendor;
	private String dbDriver;
	private String dbUrl;
	private String dbUsername;
	private String dbPasswd;
	private String dbEncoding;

	private List<String> models;
	private List<String> templates;

	private final String java_templates = "java_vo,java_dao,java_service,java_serviceimpl,java_controller,java_restcontroller";
	private final String jsp_templates = "jsp_list,jsp_view";
	private final String resources_templates = "resources_sqlmap,resources_query";
	private final String flex_templates = "flex_vo,flex_da,flex_dao,flex_localdaimpl,flex_restdaimpl,flex_service,flex_controller";

	public Configuration(){
	}

	public String getMode(){
		return mode;
	}

	public void setMode(String mode){
		this.mode = mode;
	}

	public String getAuthor(){
		return author;
	}

	public void setAuthor(String author){
		this.author = author;
	}

	public String getCreateDate(){
		return createDate;
	}

	public void setCreateDate(String createDate){
		this.createDate = createDate;
	}

	public String getDaoPackage(){
		return daoPackage;
	}

	public void setDaoPackage(String daoPackage){
		this.daoPackage = daoPackage;
	}

	public String getVoPackage(){
		return voPackage;
	}

	public void setVoPackage(String voPackage){
		this.voPackage = voPackage;
	}

	public String getServicePackage(){
		return servicePackage;
	}

	public void setServicePackage(String servicePackage){
		this.servicePackage = servicePackage;
	}

	public String getServiceImplPackage(){
		return serviceImplPackage;
	}

	public void setServiceImplPackage(String serviceImplPackage){
		this.serviceImplPackage = serviceImplPackage;
	}

	public String getControllerPackage(){
		return controllerPackage;
	}

	public void setControllerPackage(String controllerPackage){
		this.controllerPackage = controllerPackage;
	}

	public String getCommonPackage(){
		return commonPackage;
	}

	public void setCommonPackage(String commonPackage){
		this.commonPackage = commonPackage;
	}

	public String getJspFolder(){
		return jspFolder;
	}

	public void setJspFolder(String jspFolder){
		this.jspFolder = jspFolder;
	}

	public String getResourceFolder(){
		return resourceFolder;
	}

	public void setResourceFolder(String resourceFolder){
		this.resourceFolder = resourceFolder;
	}

	public String getDaPackage(){
		return daPackage;
	}

	public void setDaPackage(String daPackage){
		this.daPackage = daPackage;
	}

	public String getDbVendor(){
		return dbVendor;
	}

	public void setDbVendor(String dbVendor){
		this.dbVendor = dbVendor;
	}

	public String getDbDriver(){
		return dbDriver;
	}

	public void setDbDriver(String dbDriver){
		this.dbDriver = dbDriver;
	}

	public String getDbUrl(){
		return dbUrl;
	}

	public void setDbUrl(String dbUrl){
		this.dbUrl = dbUrl;
	}

	public String getDbUsername(){
		return dbUsername;
	}

	public void setDbUsername(String dbUsername){
		this.dbUsername = dbUsername;
	}

	public String getDbPasswd(){
		return dbPasswd;
	}

	public void setDbPasswd(String dbPasswd){
		this.dbPasswd = dbPasswd;
	}

	public String getDbEncoding(){
		return dbEncoding;
	}

	public void setDbEncoding(String dbEncoding){
		this.dbEncoding = dbEncoding;
	}

	public List<String> getModels(){
		return models;
	}

	public void setModels(String models){
		StringTokenizer st = new StringTokenizer(models, ",");
		this.models = new ArrayList<String>(
				((int) (st.countTokens() / 0.75) + 1));

		while (st.hasMoreTokens()){
			this.models.add(st.nextToken());
		}
	}

	public List<String> getTemplates(){
		return templates;
	}

	public void setTemplates(String templates){
		if ("ALL".equalsIgnoreCase(templates)){
			templates = new StringBuffer(java_templates).append(",")
					.append(jsp_templates).append(",")
					.append(resources_templates).toString();
		} else if ("JAAV".equalsIgnoreCase(templates)){
			templates = java_templates;
		} else if ("JSP".equalsIgnoreCase(templates)){
			templates = java_templates;
		} else if ("RESOURCES".equalsIgnoreCase(templates)){
			templates = java_templates;
		}

		StringTokenizer st = new StringTokenizer(templates, ",");
		this.templates = new ArrayList<String>(
				((int) (st.countTokens() / 0.75) + 1));

		while (st.hasMoreTokens()){
			this.templates.add(st.nextToken());
		}
	}

	public String getJava_templates(){
		return java_templates;
	}

	public String getJsp_templates(){
		return jsp_templates;
	}

	public String getResources_templates(){
		return resources_templates;
	}

	public String getFlex_templates(){
		return flex_templates;
	}

}
