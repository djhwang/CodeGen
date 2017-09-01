/**
 * ClassDescriptorImporter.java 1.0 2013. 8. 4.
 *   
 * Copyright 2012 S-Core, Inc. All rights reserved.
 * S-Core PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.score.codegen.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.score.codegen.model.AttributeDescriptor;
import com.score.codegen.model.ClassDescriptor;

/**
 * @date 2013. 8. 4.
 * @author david
 *
 */
public class ClassDescriptorImporter extends DefaultHandler{
	private ArrayList<ClassDescriptor> classes = new ArrayList<ClassDescriptor>();

	public ArrayList<ClassDescriptor> getClasses(){
		return classes;
	}

	public void startElement(String uri, String name, String qName,
			Attributes attr) throws SAXException{

		// Imports <Class>  
		if (name.equals("Class")){
			ClassDescriptor cl = new ClassDescriptor(attr.getValue("name"));
			classes.add(cl);
		}

		// Imports <Attribute>
		else if (name.equals("Attribute")){
			ClassDescriptor parent = (ClassDescriptor) classes.get(classes
					.size() - 1);
			AttributeDescriptor at = new AttributeDescriptor(
					attr.getValue("name"));
			at.setType(attr.getValue("type"));

			if (attr.getValue("pk") != null
					&& attr.getValue("pk").equals("true")){
				at.setPrimaryKey(true);
				parent.addPrimaryKeys(at);
			}

			parent.addAttribute(at);

		}

		else if (name.equals("Content")){
		}

		else
			throw new SAXException("Element " + name + " not valid");
	}
}
