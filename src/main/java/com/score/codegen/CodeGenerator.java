/**
 * CodeGenerator.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import com.score.codegen.data.Constant;
import com.score.codegen.model.AttributeDescriptor;
import com.score.codegen.model.ClassDescriptor;
import com.score.codegen.model.Configuration;
import com.score.codegen.model.Template;
import com.score.codegen.parser.ClassDescriptorImporter;
import com.score.codegen.parser.ClassDescriptorImporterDB;
import com.score.codegen.service.EnvConfig;
import com.score.codegen.util.GenUtils;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class CodeGenerator{
	private List<ClassDescriptor> classes = new ArrayList<ClassDescriptor>();
	private HashMap<String, Template> defaultTemplateMap;
	private List<Template> templateList;
	private Configuration config;
	private Map<String, String> dirs = new HashMap<String, String>();

	public CodeGenerator(){
	}

	public void init() throws Exception{
		// environment variable
		try{
			config = new Configuration();
			config.setMode(EnvConfig.getMode());
			config.setAuthor(EnvConfig.getAuthor());
			config.setCreateDate(EnvConfig.getCreateDate());
			config.setDaoPackage(EnvConfig.getDaoPackage());
			config.setVoPackage(EnvConfig.getVoPackage());
			config.setServicePackage(EnvConfig.getServicePackage());
			config.setServiceImplPackage(EnvConfig.getServiceImplPackage());
			config.setControllerPackage(EnvConfig.getControllerPackage());
			config.setCommonPackage(EnvConfig.getCommonPackage());
			config.setJspFolder(EnvConfig.getJspFolder());
			config.setResourceFolder(EnvConfig.getResourceFolder());
			config.setDbVendor(EnvConfig.getDbVendor());
			config.setDbDriver(EnvConfig.getDbDriver());
			config.setDbUrl(EnvConfig.getDbUrl());
			config.setDbUsername(EnvConfig.getDbUserName());
			config.setDbPasswd(EnvConfig.getDbPassword());
			config.setDbEncoding(EnvConfig.getDbEncoding());
			config.setModels(EnvConfig.getModels());
			config.setTemplates(EnvConfig.getTemplates());

		} catch (MissingResourceException _ex){
			_ex.printStackTrace();
			System.err.println("config.properties is missing.");
			System.exit(-1);
		}

		Properties p = new Properties();

		p.put(Velocity.FILE_RESOURCE_LOADER_PATH, "target/classes/templates");

		/*
		 * jar 배포시에는 classpath에서 template file을 찾도록 설정해야 함
		 */
		//        p.put(Velocity.RESOURCE_LOADER, "classpath");
		//        p.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());

		// Init Velocity
		Velocity.init(p);

		// create output directories and set the dirs map
		createDirectories();

		initTemplateInfo();
	}

	public void parse() throws Exception{
		if ("database".equalsIgnoreCase(config.getMode())){
			parseDB();
		} else if ("xml".equalsIgnoreCase(config.getMode())){
			parseXML();
		}
	}

	private void parseDB(){
		// Set the ClassDescriptorImporter as handler
		ClassDescriptorImporterDB cdImporter = new ClassDescriptorImporterDB(
				config);

		// Load the model
		cdImporter.load();
		classes.addAll(cdImporter.getClasses());
	}

	private void parseXML() throws Exception{
		FileInputStream input = null;
		classes = new ArrayList<ClassDescriptor>();

		// SAX parser initialization
		SAXParserFactory spf = SAXParserFactory.newInstance();
		spf.setNamespaceAware(true);
		SAXParser saxParser = spf.newSAXParser();
		XMLReader xmlReader = saxParser.getXMLReader();

		// Set the ClassDescriptorImporter as handler
		ClassDescriptorImporter cdImporter = new ClassDescriptorImporter();
		xmlReader.setContentHandler(cdImporter);

		for (String model : config.getModels()){
			// Load the model
			input = new FileInputStream(model);
			xmlReader.parse(new InputSource(input));
			input.close();

			classes.addAll(cdImporter.getClasses());
		}
	}

	public void generateStub() throws Exception{
		VelocityContext context = new VelocityContext();
		context.put("env", config);

		for (int i = 0; i < classes.size(); i++){
			ClassDescriptor cl = (ClassDescriptor) classes.get(i);
			context.put("class", cl);

			for (Template t : templateList){
				String sourceFilename = null;
				if (t.getExt().equals(".jsp")){
					sourceFilename = new StringBuffer().append(t.getDir())
							.append(File.separator)
							.append(t.getSuffix().toLowerCase())
							.append(cl.getPcName()).append(t.getExt())
							.toString();
				} else{
					sourceFilename = new StringBuffer().append(t.getDir())
							.append(File.separator).append(cl.getPcName())
							.append(t.getSuffix()).append(t.getExt())
							.toString();
				}

				org.apache.velocity.Template template = Velocity.getTemplate(
						t.getTemplateName(), "UTF-8");
				BufferedWriter writer = new BufferedWriter(new FileWriter(
						sourceFilename));

				template.merge(context, writer);
				writer.flush();
				writer.close();

				System.out.println(sourceFilename + " generated!");
			}
		}
	}

	/**
	 * Create output directories
	 * & set the directories map
	 */
	private void createDirectories(){
		// root, section root
		dirs.put("root", Constant.ROOT_OUTPUT);
		dirs.put("rootJava", Constant.ROOT_JAVA);
		dirs.put("rootJsp", Constant.ROOT_JSP);
		dirs.put("rootResources", Constant.ROOT_RESOURCES);
		dirs.put("rootFlex", Constant.ROOT_FLEX);

		//java sub directories
		dirs.put(Constant.JAVA_VO, Constant.ROOT_JAVA + File.separator
				+ config.getVoPackage().replace('.', File.separatorChar));
		dirs.put(Constant.JAVA_DAO, Constant.ROOT_JAVA + File.separator
				+ config.getDaoPackage().replace('.', File.separatorChar));
		dirs.put(Constant.JAVA_SERVICE, Constant.ROOT_JAVA + File.separator
				+ config.getServicePackage().replace('.', File.separatorChar));
		dirs.put(Constant.JAVA_SERVICEIMPL, Constant.ROOT_JAVA + File.separator
				+ config.getServicePackage().replace('.', File.separatorChar));
		dirs.put(Constant.JAVA_CONTROLLER, Constant.ROOT_JAVA
				+ File.separator
				+ config.getControllerPackage()
						.replace('.', File.separatorChar));
		dirs.put(
				Constant.JAVA_RESTCONTROLLER,
				Constant.ROOT_JAVA
						+ File.separator
						+ config.getControllerPackage().replace('.',
								File.separatorChar));

		//jsp
		dirs.put(Constant.JSP_LIST, Constant.ROOT_JSP + File.separator
				+ config.getJspFolder().replace('.', File.separatorChar));
		dirs.put(Constant.JSP_VIEW, Constant.ROOT_JSP + File.separator
				+ config.getJspFolder().replace('.', File.separatorChar));

		//resources
		dirs.put(
				Constant.RESOURCES_SQLMAP,
				Constant.ROOT_RESOURCES
						+ File.separator
						+ config.getResourceFolder().replace('.',
								File.separatorChar));
		dirs.put(
				Constant.RESOURCES_QUERY,
				Constant.ROOT_RESOURCES
						+ File.separator
						+ config.getResourceFolder().replace('.',
								File.separatorChar));

		//flex
		dirs.put(Constant.FLEX_VO, Constant.ROOT_FLEX + File.separator
				+ config.getVoPackage().replace('.', File.separatorChar));
		/*
		 * dirs.put(Constant.FLEX_DA, Constant.ROOT_FLEX + File.separator
		 * + config.getDaPackage().replace('.', File.separatorChar));
		 * dirs.put(Constant.FLEX_LOCALDAIMPL, Constant.ROOT_FLEX +
		 * File.separator
		 * + config.getDaPackage().replace('.', File.separatorChar));
		 * dirs.put(Constant.FLEX_RESTDAIMPL, Constant.ROOT_FLEX +
		 * File.separator
		 * + config.getDaPackage().replace('.', File.separatorChar));
		 * dirs.put(Constant.FLEX_DAO, Constant.ROOT_FLEX + File.separator
		 * + config.getDaoPackage().replace('.', File.separatorChar));
		 * dirs.put(Constant.FLEX_SERVICE, Constant.ROOT_FLEX + File.separator
		 * + config.getServicePackage().replace('.', File.separatorChar));
		 * dirs.put(Constant.FLEX_CONTROLLER, Constant.ROOT_FLEX
		 * + File.separator
		 * + config.getControllerPackage()
		 * .replace('.', File.separatorChar));
		 */

		for (Iterator<String> iter = dirs.keySet().iterator(); iter.hasNext();){
			String key = iter.next();
			//            System.out.println(key + " ===>" + directories.get(key));
			GenUtils.makeDirs(dirs.get(key));
		}
	}

	/**
     * 
     */
	private void initTemplateInfo(){
		//initialize template info
		//name, dir, suffix, ext
		defaultTemplateMap = new HashMap<String, Template>();
		defaultTemplateMap.put(Constant.JAVA_VO, new Template(
				Constant.JAVA_VO, dirs.get(Constant.JAVA_VO), "VO", ".java"));
		defaultTemplateMap
				.put(Constant.JAVA_DAO,
						new Template(Constant.JAVA_DAO,
						dirs.get(Constant.JAVA_DAO), "DAO", ".java"));
		defaultTemplateMap.put(Constant.JAVA_SERVICE, new Template(
				Constant.JAVA_SERVICE, dirs.get(Constant.JAVA_SERVICE),
				"Service", ".java"));
		defaultTemplateMap.put(Constant.JAVA_SERVICEIMPL, new Template(
				Constant.JAVA_SERVICEIMPL, dirs.get(Constant.JAVA_SERVICEIMPL),
				"ServiceImpl", ".java"));
		defaultTemplateMap.put(Constant.JAVA_CONTROLLER, new Template(
				Constant.JAVA_CONTROLLER, dirs.get(Constant.JAVA_CONTROLLER),
				"Controller", ".java"));
		defaultTemplateMap.put(
				Constant.JAVA_RESTCONTROLLER,
				new Template(Constant.JAVA_RESTCONTROLLER, dirs
						.get(Constant.JAVA_RESTCONTROLLER), "RestController",
						".java"));

		// sqlmap, query                                                                
		defaultTemplateMap.put(Constant.RESOURCES_SQLMAP, new Template(
				Constant.RESOURCES_SQLMAP, dirs.get(Constant.RESOURCES_SQLMAP),
				"SQL", ".xml"));
		defaultTemplateMap.put(Constant.RESOURCES_QUERY, new Template(
				Constant.RESOURCES_QUERY, dirs.get(Constant.RESOURCES_QUERY),
				"_query", ".xml"));

		// jsp                                                                          
		defaultTemplateMap
				.put(Constant.JSP_LIST,
						new Template(Constant.JSP_LIST, dirs
								.get(Constant.JSP_LIST), "List", ".jsp"));
		defaultTemplateMap
				.put(Constant.JSP_VIEW,
						new Template(Constant.JSP_VIEW, dirs
								.get(Constant.JSP_VIEW), "View", ".jsp"));

		// Flex
		defaultTemplateMap.put(Constant.FLEX_VO, new Template(
				Constant.FLEX_VO, dirs.get(Constant.FLEX_VO), "VO", ".as"));
		defaultTemplateMap.put(Constant.FLEX_DA, new Template(
				Constant.FLEX_DA, dirs.get(Constant.FLEX_DA), "DA", ".as"));
		defaultTemplateMap.put(Constant.FLEX_LOCALDAIMPL, new Template(
				Constant.FLEX_LOCALDAIMPL, dirs.get(Constant.FLEX_LOCALDAIMPL),
				"LocalDAImpl", ".as"));
		defaultTemplateMap.put(Constant.FLEX_RESTDAIMPL, new Template(
				Constant.FLEX_RESTDAIMPL, dirs.get(Constant.FLEX_LOCALDAIMPL),
				"RestDAImpl", ".as"));
		defaultTemplateMap.put(Constant.FLEX_DAO, new Template(
				Constant.FLEX_DAO, dirs.get(Constant.FLEX_DAO), "DAO", ".as"));
		defaultTemplateMap.put(Constant.FLEX_SERVICE, new Template(
				Constant.FLEX_SERVICE, dirs.get(Constant.FLEX_SERVICE),
				"Service", ".as"));
		defaultTemplateMap.put(Constant.FLEX_CONTROLLER, new Template(
				Constant.FLEX_CONTROLLER, dirs.get(Constant.FLEX_CONTROLLER),
				"Controller", ".as"));

		// initialize selected template list
		templateList = new ArrayList<Template>();
		for (String each : config.getTemplates()){
			if (defaultTemplateMap.containsKey(each)){
				templateList.add(defaultTemplateMap.get(each));
			}
		}
	}

	public void showClasses(){
		for (int i = 0; i < classes.size(); i++){
			ClassDescriptor cl = (ClassDescriptor) classes.get(i);
			System.out.println(cl.getName());
			ArrayList<AttributeDescriptor> attrs = cl.getAttributes();
			for (int j = 0; j < attrs.size(); j++){
				AttributeDescriptor at = (AttributeDescriptor) attrs.get(j);
				System.out.print("\t" + at.getType());
				System.out.println(" " + at.getName());
			}
		}
	}
}
